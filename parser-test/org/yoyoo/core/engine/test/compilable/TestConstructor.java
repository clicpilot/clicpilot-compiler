package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestConstructor extends AbstractTestCase{
	
	
	@Test
	public void test() 
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 6);
		assertEquals(((TestCase)(TestCase.values.get(0))).getTestString().getVal(), "test a");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "test a");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "test c");
		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "test c");
		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "test d");
		assertEquals(((YoyooString)TestCase.values.get(5)).getVal(), "test e");
	}
	
	
	
	
	
}
