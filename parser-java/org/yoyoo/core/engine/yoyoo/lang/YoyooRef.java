package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.type.RefType;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class YoyooRef<V extends YoyooObject> extends YoyooObject {
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooRef)
			if(((YoyooRef<?>)(arg0)).getRefType()!=null
					&& ((YoyooRef<?>)(arg0)).getRefType().getName().equals(refType.getName())
					&& ((YoyooRef<?>)(arg0)).getValue()!=null
					&& ((YoyooRef<?>)(arg0)).getValue().equals(value)
					)
				return true;
			else 
				return false;
		else if(arg0!=null && value!=null)
			return value.equals(arg0);
		else 
			return false;
	}

	@Override
	public String toString() {
		return refType.getName()+"("+value.toString()+")";
	}

	private RefType refType;

	private V value;

	public RefType getRefType() {
		return refType;
	}

	public void setRefType(RefType refType) {
		this.refType = refType;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public YoyooRef(RefType refType, V value) {
		super();
		this.refType = refType;
		this.value = value;
	}

	@Override
	public Object cloneAtom() throws YoyooRTException {
		return this;
	}
	
	
	
}
