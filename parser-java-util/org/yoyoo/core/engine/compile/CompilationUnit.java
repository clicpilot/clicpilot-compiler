package org.yoyoo.core.engine.compile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorObservableDelegate;
import org.yoyoo.core.engine.compile.encode.ICodeDecorator;
import org.yoyoo.core.engine.compile.opt.IStop;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.ParseException;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.Token;
import org.yoyoo.core.engine.parser.YoyooCompilationUnit;
import org.yoyoo.core.engine.parser.YoyooDeclarationName;
import org.yoyoo.core.engine.parser.YoyooImportDecl;
import org.yoyoo.core.engine.parser.YoyooPackageDecl;
import org.yoyoo.core.engine.parser.YoyooTypeDecl;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;


public class CompilationUnit extends YoyooParserVisitorAdapter {
	
	private SimpleNode compilationUnitNode;


	private SymbolTable<String> imports;

	private SymbolTable<YoyooTypeDefineClass> decl;

	private String packageName;

	private CompilationUnitSource source;


	private Stack<Operator> operatorsInCompiling;

	private YoyooTypeDefineClass classInCompiling;

	private YoyooBaseMethod methodInCompiling;


	

	private Stack<SymbolTable<YoyooVariable>> localVariableStack;

	private SymbolTable<YoyooVariable> currentLocalVariableTable;

	private Map<String, Set<ReferenceNameType>> referencesMap;
	
	private Map<String, Set<ReferenceNameType>> staticReferencesMap;

//	private Map<ReferenceNameType, Set<SimpleNode>> referencesInfoMap;

	private Map<String, Set<ReferenceNameType>> unknownReferencesMap;
	
	private Map<String, Set<ReferenceNameType>> unknownStaticReferencesMap;

//	private Map<String, Set<SimpleNode>> unknownReferencesInfoMap;

	// private Map<ReferenceNameType, Set<IUnknownAction>>
	// unknownReferenceTypeActions;

	public Map<String, Set<ReferenceNameType>> getUnknownStaticReferencesMap() {
		return unknownStaticReferencesMap;
	}

	private YoyooEnvironment env;
	
	private CodeDecoratorObservableDelegate encodeObservable;
	
	private boolean compiled;
	
	private List<Token> preProcessorTokenList;
  

	public SimpleNode getCompilationUnitNode() {
		return compilationUnitNode;
	}

	public void setCompilationUnitNode(SimpleNode compilationUnitNode) {
		this.compilationUnitNode = compilationUnitNode;
	}
	
	public boolean containsPreProcessorStm() {
		return preProcessorTokenList!=null;
	}
  
	public List<Token> getPreProcessorTokens() {
		return preProcessorTokenList;
	}
	
	public void setPreProcessorTokenList(List<Token> preProcessorTokenList) {
		if(preProcessorTokenList!=null && !preProcessorTokenList.isEmpty()) {
			this.preProcessorTokenList = preProcessorTokenList;
		}
	}

	
	public void addCodeDecorator(ICodeDecorator observer) {
		this.encodeObservable.addObserver(observer);
	}
	
	private CompilationUnit(YoyooEnvironment env) {
		super();
		imports = new SymbolTable<String>();
		decl = new SymbolTable<YoyooTypeDefineClass>();
		localVariableStack = new Stack<SymbolTable<YoyooVariable>>();
		this.env = YoyooEnvironment.getDefault();
		this.referencesMap = new HashMap<String, Set<ReferenceNameType>>();
		this.staticReferencesMap = new HashMap<String, Set<ReferenceNameType>>();
//		this.referencesInfoMap = new HashMap<ReferenceNameType, Set<SimpleNode>>();
		this.unknownReferencesMap = new HashMap<String, Set<ReferenceNameType>>();
		this.unknownStaticReferencesMap = new HashMap<String, Set<ReferenceNameType>>();
		
		
		encodeObservable = new CodeDecoratorObservableDelegate();
		
		List<ICodeDecorator> codeDecorators = env.getCodeDecorators();
		for(ICodeDecorator codeDecorator : codeDecorators)
			this.addCodeDecorator(codeDecorator);
	}

	public CompilationUnit(YoyooEnvironment env, File f) {
		this(env);
		this.source = new FileCompilationUnitSource(f);
	}

	public CompilationUnit(YoyooEnvironment env, String str, File f) {
		this(env);
		this.source = new StringCompilationUnitSource(str, f);
	}
	
	public CompilationUnit(YoyooEnvironment env, String str) {
		this(env);
		this.source = new StringCompilationUnitSource(str, null);
	}

	
	private void addItems(String name, SimpleNode node,
			YoyooTypeDefineClass declUnit) {
		try {
			if (node instanceof YoyooImportDecl) {
				imports.putSymbol(name, name, node, this);
				env.includePackage(name);
			} else if (node instanceof YoyooTypeDecl) {
				decl.putSymbol(name, declUnit, node, this);
				env.addDeclaration(packageName == null ? "" : packageName + "."
						+ name, node, declUnit, this);
				if (packageName != null)
					env.includePackage(packageName);
			}

		} catch (CompileException e) {
			addError(e);
		} catch (ParseException e) {
			//addError(e);			
			e.printStackTrace();
		}
	}



	public void backup() {

	}



	/**
	 * @param packageDecl
	 *            the packageDecl to set
	 */
	public void setPackageDecl(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooCompilationUnit,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooCompilationUnit node, Object data) {

		return super.visit(node, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooDeclarationName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooDeclarationName node, Object data) {
		
		SimpleNode declNode = (SimpleNode) data;
		YoyooTypeDefineClass declUnit = new YoyooTypeDefineClass(this, node);
		
		this.setClassInCompiling(declUnit);
		declNode.childrenAccept(declUnit, data);
		addItems(node.first_token.image, declNode, declUnit);		
		
		return super.visit(node, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooImportDecl,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooImportDecl node, Object data) {
		addItems((String) data, node, null);
		return super.visit(node, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooPackageDecl,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooPackageDecl node, Object data) {
		setPackageDecl((String) data);
		return super.visit(node, data);
	}

	/**
	 * @return the file
	 */
	public CompilationUnitSource getSource() {
		return source;
	}

	public void pushOperator(Operator opt) {
		if (this.operatorsInCompiling == null)
			this.operatorsInCompiling = new Stack<Operator>();
		this.operatorsInCompiling.push(opt);
	}

	public Operator popOperator() {
		return this.operatorsInCompiling.pop();
	}

	public Operator findMark(String label, boolean loop) {
		if (operatorsInCompiling != null && !operatorsInCompiling.isEmpty()) {
			for (Iterator<Operator> iterator = operatorsInCompiling.iterator(); iterator
					.hasNext();) {
					Operator opt = iterator.next();
				if (opt instanceof IStop && ((IStop) opt).isMark()) {
					if (!loop || (loop && ((IStop) opt).isLoop())) {
						if ((label != null && opt.getLabel() != null && opt.getLabel().equals(label)) || label == null) {
							
							return opt;
						}
					}

				}

			}
		}
		return null;
	}

	public void pushNewLocalVariableStack() {
		SymbolTable<YoyooVariable> bk = null;
		if (currentLocalVariableTable != null) {
			localVariableStack.push(currentLocalVariableTable);
			bk = currentLocalVariableTable;
		}
		currentLocalVariableTable = new SymbolTable<YoyooVariable>(bk);

	}

	public void registerNewLocalVariable(YoyooVariable decl, SimpleNode node)
			throws CompileException {
		currentLocalVariableTable.putSymbol(decl.getName(), decl, node, this);
	}
	
	
	
	public IType getVariableType(YoyooTypeDefineClass typecls,
			YoyooBaseMethod method, SymbolTable<YoyooVariable> variableTable,
			ReferenceAtom atom, String name, SimpleNode node, CompilationUnit unit)
			throws CompileException {

		YoyooVariable decl;
		FormalParameter param;
		YoyooField field;
		IType type = null;
		int firstDotIndex = name.indexOf(".");
		int secondDotIndex = name.indexOf(".", firstDotIndex);
		String subname[] = name.split("\\.");
		String firstname = (firstDotIndex == -1)?name:name.substring(0, firstDotIndex);
		if (firstname.equals(RuntimeContext.THIS)) {
			if(method!=null && method.isStaticMethod())
			{
				throw new CompileException.FieldInStatic(firstname, node, unit);
			}
			type = ReferenceNameType.createThisRefType(typecls, node);
		} else if (firstname.equals(RuntimeContext.SUPER)) {
			if(method!=null && method.isStaticMethod())
			{
				throw new CompileException.FieldInStatic(firstname, node, unit);
			}
			type = ReferenceNameType.createThisRefType(typecls, node);
		} else if (variableTable!=null && (decl = variableTable.getSymbol(firstname)) != null) {
			type = decl.getType();
			IType assignedType = decl.getAssignedType();
			if(assignedType!=null) {
				atom.setAssignedType(assignedType);
			}
		} else if (method != null
				&& (param = method.getParameter(firstname)) != null) {
			type = param.getType();
		} else if ((field = typecls.getField(firstname)) != null) {
			
			if(method!=null && method.isStaticMethod() && !field.getUnit().isInterface())	{
				throw new CompileException.FieldInStatic(firstname, node, unit);
			} else if(field.accessibilityCheck(typecls)) {
				type = field.getType();
			}
			IType assignedType = field.getAssignedType();
			if(assignedType!=null) {
				atom.setAssignedType(assignedType);
			}
		} else {
			if(typecls.isAmbiguousField(firstname)) {
				throw new CompileException.FieldAmbiguous(firstname, node, unit);
			} else {
				ReferenceNameType staticReferenceName = new ReferenceNameType(unit, node, typecls);
				staticReferenceName.setName(firstname);
				if(unit.getEnv().solveUnknownStaticReferenceType(unit, staticReferenceName)) {
					type = staticReferenceName;					
				} else {
					throw new CompileException.Undefined(firstname, node, unit);
				}
			}
		}
		if (firstDotIndex == -1) {			
			return type;
		} else {
			atom.addType(type);			
			if (type.isPrimitiveType() && type.isArray()) {
				if(subname.length>2) {
					String postname = name.substring(secondDotIndex+1);	
					type = YoyooArray.fieldCheck(subname[1], node, unit);
					YoyooTypeDefineClass ycls = (YoyooTypeDefineClass) ((ReferenceNameType) type)
						.map2YoyooClass(env);
					return getVariableType(ycls, null, null, atom, postname, node, unit);
				} else {
					return YoyooArray.fieldCheck(subname[1], node, unit);
				}
			} else if (!type.isPrimitiveType() && type.isArray()) {
				if(subname.length>2) {
					String postname = name.substring(secondDotIndex+1);	
					type = YoyooArray.fieldCheck(subname[1], node, unit);
					YoyooTypeDefineClass ycls = (YoyooTypeDefineClass) ((ReferenceNameType) type)
						.map2YoyooClass(env);
					return getVariableType(ycls, null, null, atom, postname, node, unit);
				} else {
					return YoyooArray.fieldCheck(subname[1], node, unit);
				}

			} else {
				YoyooTypeDefineClass ycls = (YoyooTypeDefineClass) ((ReferenceNameType) type)
						.map2YoyooClass(env);
				String postname = name.substring(firstDotIndex+1);	
				return getVariableType(ycls, null, null, atom, postname, node, unit);

			}

		}

	}

	public void popLocalVariableStack() {
		if (!localVariableStack.isEmpty())
			currentLocalVariableTable = localVariableStack.pop();
		else
			currentLocalVariableTable = null;
	}

	public YoyooVariable getLocalVariable(String name) {
		return currentLocalVariableTable.getSymbol(name);
	}

	public void addError(CompileException error) {
		env.addError(error, this);
	}

	public void addRefernece(ReferenceNameType type) {
		YoyooTypeDefineClass ycls = null;
		if (!type.conatinPackage()) {
			ycls = env.getDeclaration(this.getPackageName(), type.getName());
			if (ycls != null) {
				type.setPkgname(this.getPackageName());
				this.addReferenceType(type);
			} else {
				ycls = env.getDeclaration(type.getName());
				if (ycls != null) {
					this.addReferenceType(type);
				}
			}
		} else {
			ycls = env.getDeclaration(type.getName());
			if (ycls != null) {
				this.addReferenceType(type);
			}
		}
		this.addUnknownReferenceType(type);

	}

	public void removeUnknownReferenceName(Set<ReferenceNameType> types, String pkgName, String clsName) {

//		if (unknownReferencesMap.containsKey(name)) {
//			Set<ReferenceNameType> types = unknownReferencesMap.remove(name);
			for (ReferenceNameType reftype : types) {
				reftype.setPkgname(pkgName);
				reftype.setName(clsName);
				addReferenceType(reftype);
			}

//		}
	}
	
	public void removeUnknownStaticReferenceName(ReferenceNameType type, String pkgName, String clsName) {

//		if (unknownReferencesMap.containsKey(name)) {
//			Set<ReferenceNameType> types = unknownReferencesMap.remove(name);

			type.setPkgname(pkgName);
			type.setName(clsName);
			type.setStaticReference(true);
			addStaticReferenceType(type);


//		}
	}

	private void addUnknownReferenceType(
			ReferenceNameType type) {
		String name = type.getFullName();
		if (!unknownReferencesMap.containsKey(name)) {
			Set<ReferenceNameType> set = new HashSet<ReferenceNameType>();
			set.add(type);
			unknownReferencesMap.put(name, set);
		} else {
			unknownReferencesMap.get(name).add(type);
		}

	}

	private void addReferenceType(ReferenceNameType type) {

		String name = type.getFullName();
		if (!referencesMap.containsKey(name)) {
			Set<ReferenceNameType> set = new HashSet<ReferenceNameType>();
			set.add(type);
			referencesMap.put(name, set);
		} else {
			referencesMap.get(name).add(type);
		}

	}
	
	private void addStaticReferenceType(ReferenceNameType type) {

		String name = type.getName();
		if (!staticReferencesMap.containsKey(name)) {
			Set<ReferenceNameType> set = new HashSet<ReferenceNameType>();
			set.add(type);
			staticReferencesMap.put(name, set);
		} else {
			staticReferencesMap.get(name).add(type);
		}
	}
	
	private void addUnknownStaticReferenceType(ReferenceNameType type) {

		String name = type.getName();
		if (!unknownStaticReferencesMap.containsKey(name)) {
			Set<ReferenceNameType> set = new HashSet<ReferenceNameType>();
			set.add(type);
			unknownStaticReferencesMap.put(name, set);
		} else {
			unknownStaticReferencesMap.get(name).add(type);
		}
	}
	
	public boolean isStaticReference(String name) {
		return staticReferencesMap.containsKey(name);
	}
	
	public YoyooClass getStaticReferenceYoyooObject(String name) {
		Iterator<ReferenceNameType> iterator = staticReferencesMap.get(name).iterator();
		if(iterator.hasNext()) {
			ReferenceNameType type = iterator.next();
			return type.map2YoyooClass(YoyooEnvironment.getDefault());
		} else {
			return null;
		}
			
	}
	

	
	public boolean isUnknownStaticReferenceType(ReferenceNameType type) {
		String name = type.getFullName();
		boolean bool = unknownStaticReferencesMap.containsKey(name);
		if(bool) {
			addUnknownStaticReferenceType(type);
		}
		return bool;
	}
	
	

	public boolean containsUnknownReferenceType() {
		return !unknownReferencesMap.isEmpty();
	}

	public String[] getImports() {
		return imports.getSymbols().keySet().toArray(new String[0]);
	}

	public static interface IUnknownAction {
		public void doAction();
	}

	public YoyooTypeDefineClass getClassInCompiling() {
		return classInCompiling;
	}

	public void setClassInCompiling(YoyooTypeDefineClass classInCompiling) {
		this.classInCompiling = classInCompiling;
	}

	public YoyooBaseMethod getMethodInCompiling() {
		return methodInCompiling;
	}

	public void setMethodInCompiling(YoyooBaseMethod methodInCompiling) {
		this.methodInCompiling = methodInCompiling;
	}


	public void compileEnd() {
		compiled = true;
		this.encodeObservable.codeChanged();
		this.encodeObservable.notifyObservers(this);
	}
	
	public void compileStart() {
		compiled = false;
		this.encodeObservable.codeChanged();
		this.encodeObservable.notifyObservers(this);
	}
	

	


	public Map<String, Set<ReferenceNameType>> getUnknownReferenceMap() {
		return unknownReferencesMap;
	}

//	public Map<String, Set<SimpleNode>> getUnknownReferencesInfoMap() {
//		return unknownReferencesInfoMap;
//	}

	public List<CompileException> getErrors() {

		return this.env.getErrors(this);
	}

	public int getErrorCount() {
		return this.env.getErrors(this) != null ? this.env.getErrors(this)
				.size() : 0;
	}

	public SymbolTable<YoyooVariable> getCurrentLocalVariableTable() {
		return currentLocalVariableTable;
	}

	public YoyooEnvironment getEnv() {
		return env;
	}
	
	public boolean isCompiled() {
		return compiled;
	}



	public SymbolTable<YoyooTypeDefineClass> getDecl() {
		return decl;
	}
	
	
	public static abstract class CompilationUnitSource{
		public abstract File getFile();
	}
	
	public static class FileCompilationUnitSource extends CompilationUnitSource{
		private File file;

		public File getFile() {
			return file;
		}

		public FileCompilationUnitSource(File file) {
			super();
			this.file = file;
		}
	}

	public static class StringCompilationUnitSource extends CompilationUnitSource{
		private String source;
		private File rootFile;
		public String getSource() {
			return source;
		}
		public File getFile() {
			return rootFile;
		}
		public StringCompilationUnitSource(String source, File rootFile) {
			super();
			this.source = source;
			if(rootFile!=null) {
				this.rootFile = rootFile;
			} else {
				synchronized(System.class) {
					
					
					try {
						File temp = File.createTempFile("temp",".yoyoo");

						FileWriter fileoutput = new FileWriter(temp);
						BufferedWriter buffout = new BufferedWriter(fileoutput);
						buffout.write(source);
						buffout.close();
						
						this.rootFile = temp;
						temp.deleteOnExit();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}
	}


}
