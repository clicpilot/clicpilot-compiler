package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.runtime.YoyooRTException;



public final class YoyooNull extends YoyooObject {

	private YoyooObject obj;
	
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof YoyooNull) {
			return true;
		} else {
			return false;
		}

	}

	public Object getVal() {
		return null;
	}
	
	public YoyooNull(YoyooObject obj){
		this.obj = obj;
	}

	public YoyooObject getClassInstance() {
		return obj;
	}

	@Override
	public Object cloneAtom() throws YoyooRTException {
		return this;
	}
	


	
	
	
	//public static final YoyooNull Null = new YoyooNull();

}