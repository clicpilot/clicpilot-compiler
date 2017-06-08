package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;

public class YoyooDoubleObject extends YoyooTypeDefine {	
	
	private Double val; 
	
	public void setValue(YoyooDouble doubleVal) {
		val = doubleVal.getVal().doubleValue();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooDoubleObject)
			return val.equals(((YoyooDoubleObject)arg0).val);
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
