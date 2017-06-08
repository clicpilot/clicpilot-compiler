package org.yoyoo.core.engine.compile;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorFactory.CodeDecoratorType;
import org.yoyoo.core.engine.compile.encode.Java2YoyooCodeDecorator;
import org.yoyoo.core.engine.compile.encode.PreProcessorCodeDecorator;
import org.yoyoo.core.engine.parser.ParseException;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeEncoder;


public class StringTemplate2YoyooConvertor {
	
	protected File templateSrcPath;
	
	protected File yoyooSrcPath;
	
	protected List<String> includePackages;
	
	protected List<String> exculdePackages;
	
	
	
	protected YoyooEnvironment env = YoyooEnvironment.getDefault();
	
	public void convert(String[] fileExt) throws IOException, ParseException, CompileException {
		this.convert(true, fileExt, "","");
	}
	
	public void convert(boolean needCompile, String[] fileExt) throws IOException, ParseException, CompileException {
		this.convert(needCompile, fileExt, "","");
	}
	
	public void convert(boolean needCompile, String[] fileExt, String postfix, String pkgprefix) throws IOException, ParseException, CompileException {
		
		Map<String, File> allJavaFiles = new HashMap<String, File>();
		
		if(templateSrcPath==null) {
			throw new IOException("Source path is null;");
		}
		
		if(yoyooSrcPath!=null) {
			if(!yoyooSrcPath.exists())
				yoyooSrcPath.mkdirs();
		} else {
			throw new IOException("Yoyoo source path is null;");
		}
		List<String> includePaths = null;
		if(includePackages!=null && !includePackages.isEmpty()) {
			includePaths = packages2paths(templateSrcPath, includePackages);
		}
		List<String> excludePaths = null;
		if(exculdePackages!=null && !exculdePackages.isEmpty()) {
			excludePaths = packages2paths(templateSrcPath, exculdePackages);
		}
		addFile(allJavaFiles, templateSrcPath, templateSrcPath, fileExt, includePaths, excludePaths);
		
		for(String classname : allJavaFiles.keySet()) {
			System.out.println(classname);
			int index = classname.lastIndexOf(".");	
			File file = allJavaFiles.get(classname);
			FileReader fr = new FileReader(file);
			char[] ch = new char[128];
			int i=0;
			StringBuffer sb = new StringBuffer();
			sb.append("package "+pkgprefix+classname.substring(0, index)+";\n");
			sb.append("public class "+classname.substring(index+1)+postfix+"{\n");
			sb.append("public static string generateCode(Map<String, Object> param) {\n");
			sb.append("string code = \"\n");
			
			StringBuffer filesb = new StringBuffer(); 
			while((i=fr.read(ch))>0) {
				String str = new String(ch, 0, i);
				
				
				
//				str = str.replace("\r", "");
//				str = str.replace("\n", "\\n\";\ncode+=\"");
				filesb.append(str);
			}
			String code = filesb.toString();
			code = code.replace("\\", "\\\\");
			code = code.replace("\"", "\\\"");
			
			code = code.replace("<!--%", "\"+param.get(\"");
			code = code.replace("%-->", "\")+\"");
			
			
			code = code.replace("/*%", "\"+param.get(\"");
			code = code.replace("%*/", "\")+\"");
			
			code = code.replace("/*@", "");
			code = code.replace("@*/", "");
			
			code = code.replace("/*#", "\";\n");
			code = code.replace("#*/", "\ncode+=\"");
			code = code.replace("/*[", "\"");
			code = code.replace("]*/", "\"");
			
			code = code.replace("/*+", "\n");
		
			
			sb.append(code);
			sb.append("\";\n");
			sb.append("return code;\n");
			sb.append("}\n");
			sb.append("}\n");
			Compiler.compile(sb.toString());
		}
		
		Set<String> convertedClasses = new HashSet<String>(); 
		Set<String> convertedPackages = new HashSet<String>();
		Set<String> convertedTemplateClasses = new HashSet<String>(); 
		for(String classname : allJavaFiles.keySet()) {
			classname += postfix;
			classname = pkgprefix+classname;
			int index = classname.lastIndexOf(".");			
			
			convertedPackages.add(classname.substring(0, index));
			YoyooTypeDefineClass ycls =  env.getDeclaration(classname.substring(0, index), classname.substring(index+1)+PreProcessorCodeDecorator.TEMPLATE_CLASS);
			if(ycls==null){	
				convertedClasses.add(classname);
				String f = yoyooSrcPath.getPath() + File.separator + package2Path(classname) + "."+Constants.FILE_EXT_YOYOO;
				CodeEncoder.encodeClass(classname.substring(0, index), classname.substring(index+1), f, CodeDecoratorType.JAVA2YOYOO);
				String jf = yoyooSrcPath.getPath() + File.separator + package2Path(classname) + "."+Constants.FILE_EXT_JAVA;
				CodeEncoder.encodeClass(classname.substring(0, index), classname.substring(index+1), jf, CodeDecoratorType.JAVA);
			} else {
				convertedTemplateClasses.add(classname+PreProcessorCodeDecorator.TEMPLATE_CLASS);
				String f = yoyooSrcPath.getPath() + File.separator + package2Path(classname+PreProcessorCodeDecorator.TEMPLATE_CLASS) + "."+Constants.FILE_EXT_YOYOO;
				CodeEncoder.encodeClass(classname.substring(0, index), classname.substring(index+1)+PreProcessorCodeDecorator.TEMPLATE_CLASS, f, CodeDecoratorType.JAVA2YOYOO);
				String jf = yoyooSrcPath.getPath() + File.separator + package2Path(classname+PreProcessorCodeDecorator.TEMPLATE_CLASS) + "."+Constants.FILE_EXT_JAVA;
				CodeEncoder.encodeClass(classname.substring(0, index), classname.substring(index+1)+PreProcessorCodeDecorator.TEMPLATE_CLASS, jf, CodeDecoratorType.JAVATEMPLATE);
				
			}
		}
//		Set<String> convertedTemplateClasses = new HashSet<String>();
//		for(String convertedClass : convertedClasses) {
//			int index = convertedClass.lastIndexOf(".");		
//			YoyooTypeDefineClass ycls =  env.getDeclaration(convertedClass.substring(0, index), convertedClass.substring(index+1)+PreProcessorCodeDecorator.TEMPLATE_CLASS);
//			if(ycls!=null){				
//				convertedTemplateClasses.add(convertedClass+PreProcessorCodeDecorator.TEMPLATE_CLASS);
//				String f = yoyooSrcPath.getPath() + File.separator + package2Path(convertedClass+PreProcessorCodeDecorator.TEMPLATE_CLASS) + "."+Constants.FILE_EXT_YOYOO;
//				CodeEncoder.encodeClass(convertedClass.substring(0, index), convertedClass.substring(index+1)+PreProcessorCodeDecorator.TEMPLATE_CLASS, f, CodeDecoratorType.JAVA2YOYOO);
//			}
//		}
		

		env.clear();
		if(needCompile) {
			String getenv = System.getenv("YOYOO_HOME");
			if(getenv!=null)
				env.addYoyooPath(getenv);
			env.addYoyooPath(yoyooSrcPath.getPath());
			
			for(String requiredYoyooPackage : Java2YoyooCodeDecorator.requiredYoyooPackages) {
				env.includePackage(requiredYoyooPackage);
			}
			for(YoyooTypeDefineClass definedClass : env.getDeclarations()) {
				
				//System.out.println("Compile definedClass :"+definedClass.getFullName());
				Compiler.compileClass(env, definedClass.getCompilationUnit().getPackageName(), definedClass.getName());
				
			}
			for(String convertedClass : convertedClasses) {
				//System.out.println("Compile convertedClass:"+convertedClass);
				int index = convertedClass.lastIndexOf(".");			
				Compiler.compileClass(env, convertedClass.substring(0, index), convertedClass.substring(index+1));
			}
			for(String convertedClass : convertedTemplateClasses) {
				//System.out.println("Compile convertedTemplateClass:"+convertedClass);
				int index = convertedClass.lastIndexOf(".");			
				Compiler.compileClass(env, convertedClass.substring(0, index), convertedClass.substring(index+1));
			}
			if(env.getErrors()!=null) {
				for(CompileException e : env.getErrors()) {
					e.printStackTrace();
				}
			}
		}
	}
	

	
	private void addFile(Map<String, File> allJavaFiles, File root, File path, final String[] fileExt, List<String> includePaths, List<String> excludePaths) {
		File[] files = path.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if(f.isDirectory())
					return true;
				else{
					for(int i=0;i<fileExt.length;i++) {
						if(f.getName().endsWith(fileExt[i])) {
							return true;
						}
					}
					return false;
				}
			}			
		});
		if(files!=null) {
			for(File file: files) {
				if(file.isDirectory()) {
					addFile(allJavaFiles, root, file, fileExt, includePaths, excludePaths);
				} else {
					String pathname = file.getParent();
					String packagePath = file.getPath().substring(root.getPath().length());
					int idx = packagePath.lastIndexOf(".");
					packagePath = packagePath.substring(0, idx);
					String classname = path2Package(packagePath.substring(1));
					if(includePaths!=null && includePaths.contains(pathname)){
						allJavaFiles.put(classname, file);
					} 
					if(excludePaths!=null && !excludePaths.contains(pathname)) {
						allJavaFiles.put(classname, file);
					}
					if(includePaths==null && excludePaths==null) {
						allJavaFiles.put(classname, file);
					}				
				}
			}
		}
		
	}
	
	private List<String> packages2paths(File parentPath, List<String> packages) {
		List<String> includePaths = new ArrayList<String>();
		for(String pkg : packages) {
			String subPath = package2Path(pkg);
			File f = new File(parentPath, subPath);
			if(f.exists() && f.isDirectory()) {
				includePaths.add(f.getPath());
			}
		}
		return includePaths;
	}
	
	private static String package2Path(String pkgName) {
		return pkgName.replaceAll("\\.", "/");
	}
	
	private static String path2Package(String path) {
		return path.replaceAll("/", "\\.").replaceAll("\\\\", "\\.");		
	}
	
	public static void main(String[] args) {
		StringTemplate2YoyooConvertor c = new StringTemplate2YoyooConvertor();
		
		if(args.length==1) {
			System.out.println("Start Java2YoyooConvertor: " + args[0]);
			String[] paths = args[0].split(";");
			String[] yoyooPaths = paths[0].split(",");
			for(int i=0;i<yoyooPaths.length;i++) {
				File file = new File(yoyooPaths[i]);
				c.env.addYoyooPath(file.getPath());
				System.out.println("Yoyoo Path: "+file.getPath());
			}
			c.templateSrcPath = new File(paths[1]);
			System.out.println("Src Path: "+c.templateSrcPath.getPath());
			c.yoyooSrcPath = new File(paths[2]);
			System.out.println("Yoyoo Target Path: "+c.yoyooSrcPath.getPath());
			if(paths.length==4) {
				String[] yoyoopkgs = paths[3].split(",");
				c.exculdePackages = new ArrayList<String>();
				for(int i=0;i<yoyoopkgs.length;i++) {
					c.exculdePackages.add(yoyoopkgs[i]);
				}
			}
			try {
				c.convert(false, new String[]{"HTML"});
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (CompileException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	

	
}
