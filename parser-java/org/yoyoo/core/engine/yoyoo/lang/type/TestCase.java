package org.yoyoo.core.engine.yoyoo.lang.type;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class TestCase extends YoyooTypeDefine {

	public static List<Object> values = new ArrayList<Object>();
	
	public static List<Boolean> resultOfEquals = new  ArrayList<Boolean>();
	
	public final static boolean DEBUG = true; 
	
	public static void clearValues() {
		values.clear();
	}
	public TestCase() {
		super();
	}
	
	
	public synchronized void check(YoyooObject obj)
	{
		values.add(obj);
	}
	public boolean assertEquals(YoyooBoolean b) {
		if(!b.getVal() && DEBUG) {
			System.err.print(b);
		}
		return b.getVal();
	}
	public boolean assertEquals(YoyooObject obj1, YoyooObject obj2)
	{
		boolean b = obj1.equals(obj2);
		resultOfEquals.add(new Boolean(b));
		if(!b && DEBUG) {
			System.err.print(obj1 + " not equal to " + obj2);
		}
		return b;
	}
	
	private YoyooString testString;
	
	public YoyooString getTestString() {
		return testString;
	}
	
	public void getTestString(YoyooRef<YoyooString> ref) {
		ref.setValue(testString);
	}

	public void setTestString(YoyooString testString) {
		this.testString = testString;
	}
	
	public void yoyooConstructor(YoyooString testString) {
		this.testString = testString;
	}
	
	protected TestCase testObject; 
	
	public void setTestObject(TestCase testObject) {
		this.testObject = testObject;
	}

	public void println(YoyooObject obj) {
		if (obj instanceof YoyooString)
			System.out.println(((YoyooString) obj).getVal());
		else if (obj instanceof YoyooBoolean)
			System.out.println(((YoyooBoolean) obj).getVal());
		else if (obj instanceof YoyooInteger)
			System.out.println(((YoyooInteger) obj).getVal().intValue());
		else if (obj instanceof YoyooLong)
			System.out.println(((YoyooLong) obj).getVal());
		else if (obj instanceof YoyooShort)
			System.out.println(((YoyooShort) obj).getVal());
		else if (obj instanceof YoyooChar)
			System.out.println(((YoyooChar) obj).getVal());
		else if (obj instanceof YoyooDouble)
			System.out.println(((YoyooDouble) obj).getVal());
		else if (obj instanceof YoyooFloat)
			System.out.println(((YoyooFloat) obj).getVal().floatValue());
		else if (obj instanceof YoyooNull)
			System.out.println(((YoyooNull) obj).getVal());
		else 
			System.out.println(obj);
	}
	@Override
	public Object cloneAtom() throws YoyooRTException {
		TestCase newObj = (TestCase)super.cloneAtom();
		newObj.testString = testString;
		return newObj;
	}
	
	

}
