package org.yoyoo.core.engine.yoyoo.lang;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooFloat extends YoyooObject implements INumber,
		IPrimitiveObject {
	private BigDecimal val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooFloat)
			if(((YoyooFloat)(arg0)).getVal()!=null)
				return ((YoyooFloat)(arg0)).getVal().floatValue()==val.floatValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf(val.floatValue());
	}
	public YoyooFloat() {
		super();
		this.val = new BigDecimal(0.0f);
	}
	public YoyooFloat(BigDecimal val) {
		super();
		this.val = val;
	}

	public YoyooFloat(String val) {
		super();
		this.val = new BigDecimal(val);
	}

	public YoyooFloat(float val) {
		super();
		this.val = new BigDecimal(val);
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooFloat(val.floatValue());
	}

	public BigDecimal getVal() {
		return val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooFloat;
	}

}
