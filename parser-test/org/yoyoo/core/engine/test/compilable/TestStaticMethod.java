package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestStaticMethod  extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 2);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "TestStaticMethodA");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "test");
	}
	
	
	
	
	
}
