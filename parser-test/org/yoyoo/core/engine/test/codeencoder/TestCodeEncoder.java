package org.yoyoo.core.engine.test.codeencoder;

import org.junit.Test;
import org.yoyoo.core.engine.compile.encode.CodeDecoratorFactory.CodeDecoratorType;
import org.yoyoo.core.engine.test.template.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeEncoder;


public class TestCodeEncoder extends AbstractTestCase {
	@Test
	public void test()
	{
		String code = "package test;"+
		"public class Test {"+
		"public static void main(string[] args) {"+
		"}"+
		"}";
		String newCode = CodeEncoder.encodeFromCode(code, CodeDecoratorType.DEFAULT);
		System.out.println(newCode);

		newCode = CodeEncoder.encodeFromCode(code, CodeDecoratorType.JAVA);
		System.out.println(newCode);
	}


}


