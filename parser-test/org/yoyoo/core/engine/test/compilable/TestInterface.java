package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestInterface extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 5);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "TestInterfaceA TestInterfaceAImplA");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "TestInterfaceA TestInterfaceAImplB");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "TestInterfaceA TestInterfaceAImplA");
		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "TestInterfaceB TestInterfaceAImplAB");
		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "TestInterfaceA TestInterfaceAImplA");
	}
	
	
	
	
	
}
