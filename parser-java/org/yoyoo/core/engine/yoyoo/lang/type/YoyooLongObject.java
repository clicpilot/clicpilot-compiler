package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;

public class YoyooLongObject extends YoyooTypeDefine {	
	
	private Long val; 
	
	public void setValue(YoyooLong longVal) {
		val = longVal.getVal().longValue();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooLongObject)
			return val.equals(((YoyooLongObject)arg0).val);
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
