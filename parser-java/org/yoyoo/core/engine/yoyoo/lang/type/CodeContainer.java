package org.yoyoo.core.engine.yoyoo.lang.type;

import java.io.IOException;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.Compiler;
import org.yoyoo.core.engine.parser.ParseException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class CodeContainer extends YoyooTypeDefine {

	public static CompilationUnit compileCode(YoyooString code) {
		return compileCode(code.getVal());
	}
	
	public static CompilationUnit compileCode(String code) {
		try {
			CompilationUnit unit = Compiler.compile(code);
			return unit;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println(code);
			e.printStackTrace();
		}
		return null;
	}

	
}
