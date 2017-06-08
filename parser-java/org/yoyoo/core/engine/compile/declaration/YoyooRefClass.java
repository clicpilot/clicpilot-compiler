package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.compile.type.RefType;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;


public class YoyooRefClass implements YoyooClass {
	

	private RefType valueType;
	
	public YoyooRefClass(RefType valueType) {
		super();
		this.valueType = valueType;
	}

	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	public YoyooObject instance(RuntimeContext ctx) {
		return new YoyooRef<YoyooObject>(valueType, null);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "ref<"+valueType.getName()+">";
	}

	

}
