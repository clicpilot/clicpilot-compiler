package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;

public class YoyooFloatObject extends YoyooTypeDefine {	
	
	private Float val; 
	
	public void setValue(YoyooFloat floatVal) {
		val = floatVal.getVal().floatValue();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooFloatObject)
			return val.equals(((YoyooFloatObject)arg0).val);
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
