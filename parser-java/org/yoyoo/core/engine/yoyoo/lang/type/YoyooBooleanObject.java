package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;

public class YoyooBooleanObject extends YoyooTypeDefine {	
	
	private Boolean val; 
	
	public void setValue(YoyooBoolean booleanVal) {
		val = booleanVal.getVal();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooBooleanObject)
			return val.equals(((YoyooBooleanObject)arg0).val);
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
