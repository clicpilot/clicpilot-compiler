package org.yoyoo.core.engine.test.generic.map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestMap extends AbstractTestCase{
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		assertEquals(TestCase.values.size(), 16);		
		assertEquals((((YoyooInteger)TestCase.values.get(0))).getVal().intValue(), 6);
		assertEquals((((YoyooInteger)TestCase.values.get(1))).getVal().intValue(), 6);
		
		assertEquals((((YoyooInteger)TestCase.values.get(2))).getVal().intValue(), 6);
		assertEquals((((YoyooInteger)TestCase.values.get(3))).getVal().intValue(), 6);
		
		assertEquals((((YoyooString)TestCase.values.get(4))).getVal(), "item1");
		assertEquals((((YoyooString)TestCase.values.get(5))).getVal(), "item2");
		assertEquals((((YoyooString)TestCase.values.get(6))).getVal(), "item3");
		assertEquals((((YoyooString)TestCase.values.get(7))).getVal(), "item4");
		assertEquals((((YoyooString)TestCase.values.get(8))).getVal(), "item5");
		assertEquals((((YoyooString)TestCase.values.get(9))).getVal(), "item6");
		

		assertEquals((((YoyooString)TestCase.values.get(10))).getVal(), "item1");
		assertEquals((((YoyooString)TestCase.values.get(11))).getVal(), "item2");
		assertEquals((((YoyooString)TestCase.values.get(12))).getVal(), "item3");
		assertEquals((((YoyooString)TestCase.values.get(13))).getVal(), "item4");
		assertEquals((((YoyooString)TestCase.values.get(14))).getVal(), "item5");
		assertEquals((((YoyooString)TestCase.values.get(15))).getVal(), "item6");
		
		

	}
	
	
	
	
	
}
