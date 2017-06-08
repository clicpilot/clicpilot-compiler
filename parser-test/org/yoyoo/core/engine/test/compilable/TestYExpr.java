package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestYExpr extends AbstractTestCase{
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 1);		
		assertEquals((((YoyooBoolean)TestCase.values.get(0))).getVal(), Boolean.TRUE);
//		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "test c");
	}
	
	
	
	
	
}
