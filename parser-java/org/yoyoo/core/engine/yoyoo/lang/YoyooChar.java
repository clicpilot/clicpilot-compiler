package org.yoyoo.core.engine.yoyoo.lang;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public final class YoyooChar extends YoyooObject implements INumber,
		IPrimitiveObject {
	private BigDecimal val;
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooChar)
			if(((YoyooChar)(arg0)).getVal()!=null)
				return ((YoyooChar)(arg0)).getVal().intValue()==val.intValue();
			else 
				return false;
		else
			return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return val==null?super.toString():String.valueOf((char)val.intValue());
	}
	public YoyooChar() {
		super();
		this.val = new BigDecimal((char)0);
	}
	
	public YoyooChar(BigDecimal val) {
		super();
		this.val = val;
	}

	public YoyooChar(char val) {
		super();
		this.val = new BigDecimal(val);
	}

	@Override
	public YoyooObject cloneAtom() throws YoyooRTException {
		// TODO Auto-generated method stub
		return new YoyooChar((char)val.intValue());
	}

	public BigDecimal getVal() {
		return val;
	}

	public YoyooPrimitiveClass getYoyooClass() {
		// TODO Auto-generated method stub
		return YoyooPrimitiveClass.YoyooChar;
	}

}
