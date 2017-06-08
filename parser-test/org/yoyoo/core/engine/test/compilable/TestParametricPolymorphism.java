package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestParametricPolymorphism   extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 4);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "TestParametricPolymorphismA test");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "TestParametricPolymorphismA 1");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "TestParametricPolymorphismB test");
		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "TestParametricPolymorphismA 1");
	}
}	
	
	