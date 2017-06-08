package org.yoyoo.core.engine.compile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.encode.ICodeDecorator;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.ParseException;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooTypeDecl;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.expr.SpecialExprFactory;


public class YoyooEnvironment {
	
	private SymbolTable<YoyooTypeDefineClass> decl;
	
	private SymbolTable<YoyooTypeDefineClass> preprocessorDecl;

	private YoyooTypeDefineLoader typeLoader;

	private Set<String> yoyooPath;

	private Map<String, List<CompileException>> errors;

	private Set<String> includePackages;

	private static YoyooEnvironment env;
	
	private List<ICodeDecorator> codeDecorators;
	
	

	public synchronized static YoyooEnvironment getDefault() {
		if (env == null) {
			YoyooTypeDefineLoader typeLoader = new YoyooTypeDefineLoader();
			env = new YoyooEnvironment(typeLoader);
			SpecialExprFactory.getInstance();

		}
		return env;
	}

	private YoyooEnvironment(YoyooTypeDefineLoader typeLoader) {
		super();
		decl = new SymbolTable<YoyooTypeDefineClass>();
		preprocessorDecl = new SymbolTable<YoyooTypeDefineClass>();
		errors = new HashMap<String, List<CompileException>>();
		includePackages = new HashSet<String>();
		// globalErrors = new ArrayList<CompileException>();
		this.typeLoader = typeLoader;
		codeDecorators = new ArrayList<ICodeDecorator>();
	}

	public void clear() {

		decl = new SymbolTable<YoyooTypeDefineClass>();
		preprocessorDecl = new SymbolTable<YoyooTypeDefineClass>();
		errors = new HashMap<String, List<CompileException>>();
		includePackages = new HashSet<String>();
		
		
	}
	
	
	public void addDeclaration(String name, SimpleNode node,
			YoyooTypeDefineClass unit, CompilationUnit cunit) {
		try {

			if (node instanceof YoyooTypeDecl) {
				if(cunit.containsPreProcessorStm()) {
					preprocessorDecl.putSymbol(name, unit, node, cunit);
				} else {
					decl.putSymbol(name, unit, node, cunit);
				}
			}

		} catch (CompileException e) {
			addError(e, cunit);
		}
	}

	public YoyooClass getDeclaration(IType type) {
		if(!type.isPrimitiveType())
		{
			ReferenceNameType refType = (ReferenceNameType)type; 
			return refType.map2YoyooClass(this);
		}
		else
		{
			PrimitiveType pType = (PrimitiveType)type;
			return pType.map2YoyooClass(this);
		}
	}

	public YoyooTypeDefineClass getDeclaration(String pkgname, String name) {
		return getDeclaration(pkgname == null ? name : pkgname + "." + name);
	}

	public YoyooTypeDefineClass getDeclaration(String name) {
		return decl.getSymbol(name);
	}

	public Collection<YoyooTypeDefineClass> getDeclarations() {
		return decl.getSymbols().values();
	}

	public YoyooTypeDefineLoader getTypeLoader() {
		return typeLoader;
	}

	public JavaClassAndYoyooClassWrapper getClassFromName(String name) {
		YoyooTypeDefineClass ycls = getDeclaration(name);
		if (ycls == null) {
			Class<? extends IYoyooObject> cls;
			//try {
				cls = getTypeLoader().loadClass(name);
			//} catch (ClassNotFoundException e) {
			//	return null;
			//}
			return new JavaClassAndYoyooClassWrapper(cls);
		} else {
			return new JavaClassAndYoyooClassWrapper(ycls);
		}

	}

	public void addYoyooPath(String str) {
		if (yoyooPath == null)
			yoyooPath = new TreeSet<String>();
		File f = new File(str);
		String fname = f.getPath();
		if (!yoyooPath.contains(fname)) {
			yoyooPath.add(fname);
		}
	}

	public void removeYoyooPath(String str) {
		if (yoyooPath != null && yoyooPath.contains(str)) {
			yoyooPath.remove(str);
		}
	}

	public void includePackage(String pkg) throws ParseException {
		if (!includePackages.contains(pkg)) {
			includePackages.add(pkg);
			String pkgPath = Compiler.package2Path(pkg);
			if(yoyooPath!=null) {
			for (String root : yoyooPath) {				
				File f = new File(root + File.separator + pkgPath);
				if (f.exists()) {
					File[] yoyooFile = f.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.toLowerCase().endsWith(
									"." + Constants.FILE_EXT_YOYOO);
						}
					});
					if (yoyooFile != null && yoyooFile.length > 0) {
						for (File yf : yoyooFile) {
							try {
								Compiler.compile(yf);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
								//System.exit(-1);
								
								this.addError(
										new CompileException.FileCannotFound(yf
												.getPath(), null, null), yf
												.getPath());
								//throw e;
								throw new ParseException(e.getMessage());
								
								
							} catch (ParseException e) {
								e.printStackTrace();
								
								//System.exit(-1);
								this.addError(
										new CompileException.FileCannotParsed(
												yf.getPath(), null, null), yf
												.getPath());
								//e.printStackTrace();
								//throw e;
								throw new ParseException(e.getMessage());
								
								
							} catch (IOException e) {
								e.printStackTrace();
								//System.exit(-1);
								this.addError(
										new CompileException.FileCannotParsed(
												yf.getPath(), null, null), yf
												.getPath());
								//e.printStackTrace();
								//throw new C(e.getMessage(), null, null, ctx);
								throw new ParseException(e.getMessage());
								
							}
						}
					}
				}
			}
			}

		}
	}

	public void solveUnknownReferenceType(CompilationUnit unit) {
		if (unit.containsUnknownReferenceType()) {
			Set<CompilationUnit> nextUnits = new HashSet<CompilationUnit>();
			synchronized (unit) {
				Set<String> keys = unit.getUnknownReferenceMap().keySet();
				int i = 0;
				for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
					String key = iterator.next();
					String pkgName = null;
					String clsName = null;
					if (Compiler.containsPackageName(key)) {
						YoyooTypeDefineClass tunit = getDeclaration(key);
						if (tunit != null) {
							i++;
							pkgName = Compiler.getPackageName(key);
							clsName = Compiler.getClassName(key);
							if (tunit.getCompilationUnit()
									.containsUnknownReferenceType())
								nextUnits.add(tunit
										.getCompilationUnit());
						}
					} else {
						pkgName = unit.getPackageName();
						clsName = key;
						YoyooTypeDefineClass tunit = getDeclaration(pkgName,
								key);
						if (tunit != null) {
							i++;
							if (tunit.getCompilationUnit()
									.containsUnknownReferenceType()
									&& tunit.getCompilationUnit() != unit)
								nextUnits.add(tunit
										.getCompilationUnit());
						}

						String[] imports = unit.getImports();
						for (String importPackage : imports) {

							YoyooTypeDefineClass tunit1 = getDeclaration(
									importPackage, key);
							if (tunit1 != null) {
								pkgName = importPackage;
								i++;
								if (tunit1.getCompilationUnit()
										.containsUnknownReferenceType())
									nextUnits.add(tunit1
											.getCompilationUnit());
							}

						}
						

					}

					if (i == 0) {
						Set<ReferenceNameType> unknownTypeSet = unit.getUnknownReferenceMap().get(key); 
						for (Iterator<ReferenceNameType> unknownTypeIterator = unknownTypeSet.iterator();unknownTypeIterator.hasNext(); ) {
							ReferenceNameType unknownType = unknownTypeIterator.next();
							int index = -1;
							if((index=unknownType.getOwnerClass().isTypeParameterName(key))!=-1) {
								unknownType.setTypeParameter(true, index);
								unknownTypeIterator.remove();
							} else {
								unit.addError(new CompileException.Undefined(key, unknownType.node, unknownType.unit));
							}
						}
						if(unknownTypeSet.isEmpty()) {
							iterator.remove();
						}
					} else if (i == 1) {
						Set<ReferenceNameType> unknownTypeSet = unit.getUnknownReferenceMap().get(key);
						iterator.remove();
						unit.removeUnknownReferenceName(unknownTypeSet, pkgName, clsName);
					} else {
						for (ReferenceNameType type : unit
								.getUnknownReferenceMap().get(key)) {
							unit.addError(new CompileException.TypeAmbiguous(
									key, type.node, type.unit));
						}
					}
					
					i=0;

				}
			}
			for(CompilationUnit next : nextUnits)
			{
				solveUnknownReferenceType(next);
			}
		}
	}
	
	public boolean solveUnknownStaticReferenceType(CompilationUnit unit, ReferenceNameType reftype) {
		String key = reftype.getFullName();
		if(unit.isUnknownStaticReferenceType(reftype)) {
			unit.addError(new CompileException.Undefined(key,
					reftype.node, reftype.unit));
			return false;
		} else {
			//Set<CompilationUnit> nextUnits = new HashSet<CompilationUnit>();		
			
			String pkgName = null;
			String clsName = null;
			int i=0;
			if (Compiler.containsPackageName(key)) {
	
				YoyooTypeDefineClass tunit = getDeclaration(key);
				if (tunit != null) {
					i++;
					pkgName = Compiler.getPackageName(key);
					clsName = Compiler.getClassName(key);
//					if (tunit.getCompilationUnit()
//							.containsUnknownReferenceType())
//						nextUnits.add(tunit
//								.getCompilationUnit());
				}
			} else {
				pkgName = unit.getPackageName();
				clsName = key;
				YoyooTypeDefineClass tunit = getDeclaration(pkgName,
						key);
				if (tunit != null) {
					i++;
//					if (tunit.getCompilationUnit()
//							.containsUnknownReferenceType()
//							&& tunit.getCompilationUnit() != unit)
//						nextUnits.add(tunit
//								.getCompilationUnit());
				}
	
				String[] imports = unit.getImports();
				for (String importPackage : imports) {
	
					YoyooTypeDefineClass tunit1 = getDeclaration(
							importPackage, key);
					if (tunit1 != null) {
						pkgName = importPackage;
						i++;
//						if (tunit1.getCompilationUnit()
//								.containsUnknownReferenceType())
//							nextUnits.add(tunit1
//									.getCompilationUnit());
					}
	
				}
	
			}
	
			if (i == 0) {
				unit.addError(new CompileException.Undefined(key,
						reftype.node, reftype.unit));
				return false;
				
			} else if (i == 1) {
								
				unit.removeUnknownStaticReferenceName(reftype, pkgName, clsName);
				try {
					Compiler.compileClass(this, pkgName, clsName);
				} catch (CompileException e) {
					unit.addError(e);
					return false;
				}
				return true;
			} else {
				unit.addError(new CompileException.TypeAmbiguous(
						key, reftype.node, reftype.unit));
				return false;
			}
		}
	}

	protected void addError(CompileException error, CompilationUnit unit) {
		this.addError(error, unit.getSource().getFile().getPath());
	}

	protected void addError(CompileException error, String filename) {
		if (!this.errors.containsKey(filename)) {
			this.errors.put(filename, new ArrayList<CompileException>());
		}
		this.errors.get(filename).add(error);
	}

	public List<CompileException> getErrors(CompilationUnit unit) {
		if (errors != null && errors.containsKey(unit.getSource().getFile().getPath()))
			return errors.get(unit.getSource().getFile().getPath());
		else
			return null;
	}

	public List<CompileException> getErrors() {
		List<CompileException> allerrors = new ArrayList<CompileException>();
		if (errors != null) {
			for (List<CompileException> elist : errors.values()) {
				allerrors.addAll(elist);
			}
			return allerrors;
		} else
			return null;
	}

	public void clearError(String filename) {
		if (this.errors.containsKey(filename)) {
			this.errors.put(filename, new ArrayList<CompileException>());
		}
	}

	public void clearError(CompilationUnit unit) {
		this.clearError(unit.getPackageName());
	}

	public void clearError() {
		this.errors.clear();
	}

	public boolean compareYoyooClass(String clsName1, String clsName2)
			throws CompileException {
		return clsName1.equals(clsName2);
	}

	public boolean compareYoyooClass(String clsName, Class<? extends IYoyooObject>  cls)
			throws CompileException {
		return clsName.equals(cls.getName());
	}

	public boolean compareYoyooClass(Class<? extends IYoyooObject>  cls1, Class<? extends IYoyooObject>  cls2)
			throws CompileException {
		return compareYoyooClass(cls1.getName(), cls2);
	}
	
	public void addCodeDecorator(ICodeDecorator codeDecorator) {
		codeDecorators.add(codeDecorator);
	}

	public List<ICodeDecorator> getCodeDecorators() {
		return codeDecorators;
	}

}
