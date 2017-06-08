package org.yoyoo.core.engine.yoyoo.lang;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooLong extends YoyooObject implements INumber,
		IPrimitiveObject {
	private BigDecimal val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooLong)
			if(((YoyooLong)(arg0)).getVal()!=null)
				return ((YoyooLong)(arg0)).getVal().longValue()==val.longValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf(val.longValue());
	}
	public YoyooLong() {
		super();
		this.val = new BigDecimal(0L);
	}
	public YoyooLong(BigDecimal val) {
		super();
		this.val = val;
	}

	public YoyooLong(String val) {
		super();
		this.val = new BigDecimal(val);
	}

	public YoyooLong(long val) {
		super();
		this.val = new BigDecimal(val);
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooLong(val.longValue());
	}

	public BigDecimal getVal() {
		return val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooLong;
	}

}
