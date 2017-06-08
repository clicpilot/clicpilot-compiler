package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class YoyooSpecialExprClass implements YoyooClass {
	

	
	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	public YoyooObject instance(RuntimeContext ctx) {
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "expr";
	}

	

}
