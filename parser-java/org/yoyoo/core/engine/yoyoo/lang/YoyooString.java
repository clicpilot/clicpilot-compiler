package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooString extends YoyooObject implements IPrimitiveObject {
	private String val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooString)
			if(((YoyooString)(arg0)).getVal()!=null)
				return ((YoyooString)(arg0)).getVal().equals(val);
			else 
				return false;
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return val.hashCode();
	}
	
	@Override
	public String toString() {
		return val==null?super.toString():val;
	}
	public YoyooString() {
		super();
		this.val = new String();
	}
	public YoyooString(String val) {
		super();
		this.val = val;
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		return new YoyooString(val);
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		return YoyooPrimitiveClass.YoyooString;
	}

}
