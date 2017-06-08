package org.yoyoo.core.engine.test.generic.list;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestList extends AbstractTestCase{
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 15);		
		assertEquals((((YoyooInteger)TestCase.values.get(0))).getVal().intValue(), 5);
		assertEquals((((YoyooString)TestCase.values.get(1))).getVal(), "item1");
		assertEquals((((YoyooString)TestCase.values.get(2))).getVal(), "item2");
		assertEquals((((YoyooString)TestCase.values.get(3))).getVal(), "item3");
		assertEquals((((YoyooString)TestCase.values.get(4))).getVal(), "item4");
		assertEquals((((YoyooString)TestCase.values.get(5))).getVal(), "itemEnd");
		assertEquals((((YoyooInteger)TestCase.values.get(6))).getVal().intValue(), 4);
		assertEquals((((YoyooString)TestCase.values.get(7))).getVal(), "item2");
		assertEquals((((YoyooString)TestCase.values.get(8))).getVal(), "item3");
		assertEquals((((YoyooString)TestCase.values.get(9))).getVal(), "item4");
		assertEquals((((YoyooString)TestCase.values.get(10))).getVal(), "itemEnd");
		assertEquals((((YoyooString)TestCase.values.get(11))).getVal(), "new item2");
		assertEquals((((YoyooString)TestCase.values.get(12))).getVal(), "new item3");
		assertEquals((((YoyooString)TestCase.values.get(13))).getVal(), "new item4");
		assertEquals((((YoyooString)TestCase.values.get(14))).getVal(), "new itemEnd");
		

	}
	
	
	
	
	
}
