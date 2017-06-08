package org.yoyoo.core.engine.yoyoo.lang;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooDouble extends YoyooObject implements INumber,
		IPrimitiveObject {
	private BigDecimal val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooDouble)
			if(((YoyooDouble)(arg0)).getVal()!=null)
				return ((YoyooDouble)(arg0)).getVal().doubleValue()==val.doubleValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf(val.doubleValue());
	}
	public YoyooDouble() {
		super();
		this.val = new BigDecimal(0.0d);
	}
	public YoyooDouble(BigDecimal val) {
		super();
		this.val = val;
	}

	public YoyooDouble(double val) {
		super();
		this.val = new BigDecimal(val);
	}

	public YoyooDouble(String val) {
		super();
		this.val = new BigDecimal(val);
	}
	
	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooDouble(val.doubleValue());
	}

	public BigDecimal getVal() {
		return val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooDouble;
	}

}
