package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestStaticConstructor extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 1);
		//assertEquals(((TestCase)((YoyooNull)TestCase.values.get(0)).getClassInstance()).getTestString().getVal(), "test a");
	}
	
	
	
	
	
}
