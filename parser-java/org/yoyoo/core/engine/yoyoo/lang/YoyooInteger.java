package org.yoyoo.core.engine.yoyoo.lang;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooInteger extends YoyooObject implements INumber,
		IPrimitiveObject {
	private BigDecimal val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooInteger)
			if(((YoyooInteger)(arg0)).getVal()!=null)
				return ((YoyooInteger)(arg0)).getVal().intValue()==val.intValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf(val.intValue());
	}
	public YoyooInteger() {
		super();
		this.val = new BigDecimal(0);
	}
	public YoyooInteger(BigDecimal val) {
		super();
		this.val = val;
	}

	public YoyooInteger(String val) {
		super();
		this.val = new BigDecimal(val);
	}

	public YoyooInteger(int val) {
		super();
		this.val = new BigDecimal(val);
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooInteger(val.intValue());
	}

	public BigDecimal getVal() {
		return val;
	}
	
	public void setVal(BigDecimal val) {
		this.val = val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooInteger;
	}

}