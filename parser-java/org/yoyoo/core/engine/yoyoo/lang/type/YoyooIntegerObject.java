package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;


public class YoyooIntegerObject extends YoyooTypeDefine {	
	
	private Integer val; 
	
	public void setValue(YoyooInteger integerVal) {
		val = integerVal.getVal().intValue();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooIntegerObject)
			return val.equals(((YoyooIntegerObject)arg0).val);
		else
			return false;
	}

	@Override
	public int hashCode() {
		if(val!=null)
			return val.hashCode();
		else
			return super.hashCode();
	}
	
	
	
}
