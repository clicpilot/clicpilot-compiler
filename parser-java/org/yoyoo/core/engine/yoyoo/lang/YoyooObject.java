package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class YoyooObject implements IYoyooObject {

//	private YoyooClass ycls;
	
	
	public Object cloneAtom() throws YoyooRTException {
		throw new YoyooRTException.ObjectCannotbeCloned(null, null, null);
	}


	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "null";
	}


	private String instanceName;

//	private RuntimeContext ctx;

//	public YoyooClass getYcls() {
//		return ycls;
//	}

	public YoyooObject() {
		super();
	}

	
	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}


	public YoyooClass getYoyooClass() {
		
		return null;
	}
	
	private YoyooObject fieldOwner;
	
	public YoyooObject getFieldOwner() {
		return fieldOwner;
	}

	public void setFieldOwner(YoyooObject fieldOwner) {
		this.fieldOwner = fieldOwner;
	}


//	@Override
//	public YoyooObject newInstance() {
//		return new YoyooObject();
//	}
	

}
