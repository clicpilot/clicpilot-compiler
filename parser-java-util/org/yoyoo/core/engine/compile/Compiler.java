package org.yoyoo.core.engine.compile;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorFactory;
import org.yoyoo.core.engine.compile.encode.PreProcessorCodeDecorator;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorFactory.CodeDecoratorType;
import org.yoyoo.core.engine.parser.ParseException;
import org.yoyoo.core.engine.parser.YoyooParser;
import org.yoyoo.core.engine.runtime.RuntimeContext;


public class Compiler {

	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}

	public static void init() throws Exception {

	}

	public static CompilationUnit compile(File f) throws ParseException,
			IOException  {
		YoyooEnvironment.getDefault().includePackage("yoyoo.lang");
		try{
			CompilationUnit unit = YoyooParser.parse(YoyooEnvironment.getDefault(), f);
			unit = processMacro(unit);
			return unit;
		} catch (ParseException e) {
			System.err.println(f.getPath());
			throw e;
		} catch (Exception e) {
			System.err.println(f.getPath());
			e.printStackTrace();
			return null;
		} catch (Error e) {
			System.err.println(f.getPath());
			e.printStackTrace();
			return null;
		}
	}

	
	public static CompilationUnit compile(String src) throws ParseException, IOException  {
		YoyooEnvironment.getDefault().includePackage("yoyoo.lang");
		CompilationUnit unit = YoyooParser.parse(YoyooEnvironment.getDefault(), src);
		unit = processMacro(unit);
		return unit;
	}
	
	public static CompilationUnit compile(String src, File f) throws ParseException, IOException  {
		try{
			YoyooEnvironment.getDefault().includePackage("yoyoo.lang");
			CompilationUnit unit = YoyooParser.parse(YoyooEnvironment.getDefault(), src, f);
			unit = processMacro(unit);
			return unit;
		} catch (ParseException e) {
			System.err.println(src);
			throw e;
		} catch (Error e) {
			System.err.println(src);
			throw e;
		}
		
	}
	
	public static CompilationUnit processMacro(CompilationUnit unit) throws ParseException, IOException  {
		if(unit.containsPreProcessorStm()) {
			PreProcessorCodeDecorator codeDecorator = (PreProcessorCodeDecorator)CodeDecoratorFactory.getInstance().getCodeDecorator(CodeDecoratorType.PREPROCESSOR);
			codeDecorator.start(unit);			
//			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			StringWriter writter = new StringWriter();
			codeDecorator.output(writter);
			String src = writter.getBuffer().toString();
			return compile(src, unit.getSource().getFile());			
		} else {
			return unit;
		}
	}
	
	public static void main(String[] args) {
		YoyooEnvironment env = YoyooEnvironment.getDefault();
		env.clearError();
		env.addYoyooPath("./");
		RuntimeContext cctx = null;
		cctx = RuntimeContext.getCurrentContext();
		cctx.run("yoyoo", "app", "main");
	}

	public static boolean containsPackageName(String name) {
		return name.indexOf(".") != -1;
	}

	public static String getPackageName(String name) {
		int i = -1;
		if ((i = name.lastIndexOf(".")) != -1) {
			return name.substring(0, i);
		} else {
			return null;
		}
	}

	public static String getClassName(String name) {
		int i = -1;
		if ((i = name.lastIndexOf(".")) != -1) {
			return name.substring(i + 1);
		} else {
			return null;
		}
	}

	public static String package2Path(String pkgName) {
		return pkgName.replaceAll("\\.", "/");
	}

	
	public static YoyooTypeDefineClass compileClass(YoyooEnvironment env, String typeunitPkg, String typeunitName) throws CompileException{
		try {
			env.includePackage(typeunitPkg);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return null;
		} 
		YoyooTypeDefineClass unit = null;
		
		unit = env.getDeclaration(typeunitPkg, typeunitName);
		if (unit != null) {
			env.solveUnknownReferenceType(unit.getCompilationUnit());
			Collection<YoyooTypeDefineClass> decls = env.getDeclarations();
			for (YoyooTypeDefineClass cls : decls) {
				if(!cls.getCompilationUnit().containsUnknownReferenceType())
					cls.classCheck();
			}
			for (YoyooTypeDefineClass cls : decls) {
				if(!cls.getCompilationUnit().containsUnknownReferenceType())
					cls.typeCheck();
				
			}

			List<CompileException> errors = env.getErrors();

			boolean hasError = false;
			if (errors != null && !errors.isEmpty()) {
				for (CompileException e : errors) {
					System.err.println("Compile Error: " + e.getMessage());
					//e.printStackTrace();
					hasError = true;
				}					
			} 
			if (!hasError && unit != null) {
				
				return unit;
			} else {
				return null;
			}
		} else {
			System.err.println("Error: " + typeunitPkg + "." + typeunitName
					+ " does not exist!");
			return null;
		}
		
	}

}
