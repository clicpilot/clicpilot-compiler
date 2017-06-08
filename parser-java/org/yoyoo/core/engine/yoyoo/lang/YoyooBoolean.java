package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooBoolean extends YoyooObject implements IPrimitiveObject {
	private Boolean val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooBoolean)
			if(((YoyooBoolean)(arg0)).getVal()!=null)
				return ((YoyooBoolean)(arg0)).getVal().booleanValue()==val.booleanValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf(val.booleanValue());
	}
	public YoyooBoolean() {
		super();
		this.val = false;
	}
	
	public YoyooBoolean(boolean val) {
		super();
		this.val = val;
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooBoolean(val);
	}

	public Boolean getVal() {
		return val;
	}
	
	public void setVal(Boolean val) {
		this.val = val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooBoolean;
	}

}
