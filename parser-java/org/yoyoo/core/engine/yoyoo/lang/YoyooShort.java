package org.yoyoo.core.engine.yoyoo.lang;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooShort extends YoyooObject implements INumber,
		IPrimitiveObject {
	private BigDecimal val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooShort)
			if(((YoyooShort)(arg0)).getVal()!=null)
				return ((YoyooShort)(arg0)).getVal().shortValue()==val.shortValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf(val.shortValue());
	}
	public YoyooShort() {
		super();
		this.val = new BigDecimal((short)0);
	}
	public YoyooShort(BigDecimal val) {
		super();
		this.val = val;
	}

	public YoyooShort(String val) {
		super();
		this.val = new BigDecimal(val);
	}

	public YoyooShort(short val) {
		super();
		this.val = new BigDecimal(val);
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooShort(val.shortValue());
	}

	public BigDecimal getVal() {
		return val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooShort;
	}

}
