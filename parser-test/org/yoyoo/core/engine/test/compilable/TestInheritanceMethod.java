package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestInheritanceMethod  extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 11);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "testA B");
		assertEquals(((YoyooString)TestCase.values.get(5)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(6)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(7)).getVal(), "testA");
		assertEquals(((YoyooString)TestCase.values.get(8)).getVal(), "testA C");
		assertEquals(((YoyooString)TestCase.values.get(9)).getVal(), "testA C");
		assertEquals(((YoyooString)TestCase.values.get(10)).getVal(), "testA C");

	}
	
	
	
	
	
}
