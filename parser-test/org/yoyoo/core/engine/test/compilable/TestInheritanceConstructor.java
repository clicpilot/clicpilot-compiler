package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestInheritanceConstructor  extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 5);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "TestInheritanceConstructorA 1");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "TestInheritanceConstructorB 2 3");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "TestInheritanceConstructorA 3");
		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "TestInheritanceConstructorA 4");
		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "TestInheritanceConstructorA 5");
	}
	
	
	
	
	
}
