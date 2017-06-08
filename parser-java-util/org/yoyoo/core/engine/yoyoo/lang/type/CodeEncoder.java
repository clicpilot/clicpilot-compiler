package org.yoyoo.core.engine.yoyoo.lang.type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorFactory;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorFactory.CodeDecoratorType;
import org.yoyoo.core.engine.compile.encode.ICodeDecorator;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class CodeEncoder extends Class {

	public static boolean encodeClass(String pkg, String cls, Writer out, long checksum, CodeDecoratorType codeType) {
		try {
			YoyooTypeDefineClass ycls =  YoyooEnvironment.getDefault().getDeclaration(pkg, cls);
			ICodeDecorator codeDecorator = CodeDecoratorFactory.getInstance().getCodeDecorator(codeType);
			codeDecorator.encode(ycls);
			boolean output = codeDecorator.output(out, checksum);
			return output;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void encodeClass(String pkg, String cls,  Writer stream) {
		encodeClass(pkg, cls, stream, 0, CodeDecoratorType.DEFAULT);
	}

	
	public static void encodeClass(String pkg, String cls) {
		encodeClass(pkg, cls, new PrintWriter(System.out));		
	}
	
	public static void encodeClass(String pkg, String cls, String f) {
		encodeClass(pkg, cls, f, CodeDecoratorType.DEFAULT);
	}
	

	public static void encodeClass(String pkg, String cls, String f, CodeDecoratorType codeType) {
		OutputStreamWriter out = null;
		FileReader instream = null;
		StringWriter outstream = null;
		try {			
			File file = new File(f);
			File path = file.getParentFile();
			if(!path.exists())
				path.mkdirs();
			long checksum = 0;

			if(file.exists()) {
				instream = new FileReader(file);
				Checksum cs = new CRC32();
				char[] bytes = new char[1024];
			    int len = 0;

			    while ((len = instream.read(bytes)) >= 0) {
			    	for(int i=0;i<len;i++)
			    		cs.update(bytes[i]);

			    }
			    checksum = cs.getValue();
				
			}
//			System.out.println(f+" "+file.exists()+" "+checksum);
			outstream = new StringWriter();
			boolean output = encodeClass(pkg, cls, outstream, checksum, codeType);
			outstream.flush();
			if(output) {

				out = new OutputStreamWriter(new FileOutputStream(file), "UTF8");
//				fileoutstream = new PrintWriter(file);
				StringBuffer buffer = outstream.getBuffer();
				out.append(buffer.toString());
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(outstream!=null)
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			if(instream!=null)
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
				
		}
				
	}
	
	
	public static String encodeClass(String pkg, String cls, CodeDecoratorType codeType) {
		StringWriter outstream = new StringWriter();
		encodeClass(pkg, cls, outstream, -1, codeType);
		outstream.flush();
		StringBuffer buffer = outstream.getBuffer();
		return buffer.toString();
	}
	
	public void encodeClass(YoyooString pkg, YoyooString cls, Writer stream) {
		encodeClass(pkg.getVal(), cls.getVal(), stream);
	}
	
	public void encodeClass(YoyooString pkg, YoyooString cls, YoyooString fname) {
		PrintWriter stream = null;
		try {			
			stream = new PrintWriter(new File(fname.getVal()));
			encodeClass(pkg, cls, stream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(stream!=null)
				stream.close();
		}
	}
	
	public void encodeClass(YoyooString pkg, YoyooString cls) {
		encodeClass(pkg.getVal(), cls.getVal());		
	}
	public void encodeClassFromCode(YoyooString code, YoyooString path) {
		encodeClassFromCode(code, path, new YoyooString(CodeDecoratorType.DEFAULT.name()));
	}
	public void encodeClassFromCode(String code, String path) {
		encodeClassFromCode(code, path, CodeDecoratorType.DEFAULT.name());
	}
	public void encodeClassFromCode(YoyooString code, YoyooString path, YoyooString lang) {
		encodeClassFromCode(code.getVal(), path.getVal(), lang.getVal());
	}
	
	public void encodeClassFromCode(String code, String path, String lang) {
		CompilationUnit unit = CodeContainer.compileCode(code);
		String pkg = unit.getPackageName();
		String pkgPath = pkg.replaceAll("\\.", "/") ;
		CodeDecoratorType codeType = CodeDecoratorType.getCodeDecoratorType(lang);
		
		for(YoyooTypeDefineClass ycls :  unit.getDecl().getSymbols().values()) {
			encodeClass(pkg, ycls.getName(), path+File.separator+pkgPath+File.separator+ycls.getName()+"."+codeType.getExt(), codeType);
		}
	}
	
	public void cleanClassPackage(YoyooString pkg, YoyooString path, YoyooRef<YoyooBoolean> result) {
		cleanClassPackage(pkg.getVal(), path.getVal(), result);
	}
	
	public void cleanClassPackage(String pkg, String path, YoyooRef<YoyooBoolean> result) {
		String pkgPath = pkg.replaceAll("\\.", "/") ;
		File f = new File(path, pkgPath);
		if(f.exists()) {
			File fs[] = f.listFiles();
			for(File file : fs) {
				if(!file.delete()){
					result.setValue(new YoyooBoolean(false));
					return;
				}
				
			}
		}
		if(!f.delete()){
			result.setValue(new YoyooBoolean(false));
		} else {
			result.setValue(new YoyooBoolean(true));
		}
	}
	
	public static void cleanPackage(String pkg, String path) {
		String pkgPath = pkg.replaceAll("\\.", "/") ;
		File f = new File(path, pkgPath);
		if(f.exists()) {
			File fs[] = f.listFiles();
			for(File file : fs) {
				if(!file.delete()){
					System.err.println("Cannot delete "+file.getPath());
					return;
				}
				
			}
		}
	}
	public static void encodeFromCode(String code, String path) {
		encodeFromCode(code, path, CodeDecoratorType.DEFAULT.name());
	}
	public static void encodeFromCode(String code, String path, String lang) {

		CompilationUnit unit = CodeContainer.compileCode(code);
		
		String pkg = unit.getPackageName();
		String pkgPath = pkg.replaceAll("\\.", "/") ;
		CodeDecoratorType codeType = CodeDecoratorType.getCodeDecoratorType(lang);
		
		for(YoyooTypeDefineClass ycls :  unit.getDecl().getSymbols().values()) {
			encodeClass(pkg, ycls.getName(), path+File.separator+pkgPath+File.separator+ycls.getName()+"."+codeType.getExt(), codeType);
		}
		YoyooEnvironment.getDefault().clear();

	}
	
	public static String encodeFromCode(String code, CodeDecoratorType codeType) {

		CompilationUnit unit = CodeContainer.compileCode(code);		
		String pkg = unit.getPackageName();
		StringBuffer sb = new StringBuffer();
		for(YoyooTypeDefineClass ycls :  unit.getDecl().getSymbols().values()) {
			sb.append(encodeClass(pkg, ycls.getName(), codeType));
		}
		YoyooEnvironment.getDefault().clear();
		return sb.toString();

	}

	
	
	
	
}