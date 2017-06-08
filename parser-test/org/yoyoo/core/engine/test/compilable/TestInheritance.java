package org.yoyoo.core.engine.test.compilable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestInheritance  extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 5);
		assertEquals(((YoyooString)TestCase.values.get(0)).getVal(), "TestInheritanceA test");
		assertEquals(((YoyooString)TestCase.values.get(1)).getVal(), "TestInheritanceB test");
		assertEquals(((YoyooString)TestCase.values.get(2)).getVal(), "TestInheritanceC test");
		assertEquals(((YoyooString)TestCase.values.get(3)).getVal(), "TestInheritanceA TestInheritanceD test");
		assertEquals(((YoyooString)TestCase.values.get(4)).getVal(), "TestInheritanceB test");
	}
	
	
	
	
	
}
