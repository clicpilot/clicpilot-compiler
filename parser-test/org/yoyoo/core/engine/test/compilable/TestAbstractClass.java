package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestAbstractClass extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 6);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "TestAbstractClassA TestAbstractClassAImplA");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "TestAbstractClassB TestAbstractClassAImplB");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "TestAbstractClassA TestAbstractClassAImplA");
		assertEquals(((YoyooLong)TestCase.values.get(3)).getVal().longValue(), 1l);
		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "TestAbstractClassA TestAbstractClassAImplA");
		assertEquals(((YoyooLong)TestCase.values.get(5)).getVal().longValue(), 1l);
//		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "TestAbstractClassA TestAbstractClassAImplAB");
//		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "TestAbstractClassB TestAbstractClassAImplAB");
//		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "TestAbstractClassA TestAbstractClassAImplAB");
	}
	
	
	
	
	
}
