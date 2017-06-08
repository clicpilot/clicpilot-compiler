package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;

public class YoyooShortObject extends YoyooTypeDefine {	
	
	private Short val; 
	
	public void setValue(YoyooShort shortVal) {
		val = shortVal.getVal().shortValue();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooShortObject)
			return val.equals(((YoyooShortObject)arg0).val);
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
