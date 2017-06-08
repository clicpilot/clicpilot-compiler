package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public enum YoyooPrimitiveClass implements YoyooClass {
	YoyooBoolean("boolean"), YoyooString("string"), YoyooInteger("int"), YoyooShort(
			"short"), YoyooFloat("float"), YoyooLong("long"), YoyooChar("char"), YoyooDouble(
			"double"), YoyooVoid("void"), YoyooNull("null");

	private String type;

	private YoyooPrimitiveClass(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return true;
	}

	public YoyooObject instance(RuntimeContext ctx) {
		switch (this) {
		case YoyooBoolean:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean();
		case YoyooString:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooString();
		case YoyooInteger:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooInteger();
		case YoyooShort:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooShort();
		case YoyooFloat:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooFloat();
		case YoyooLong:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooLong();
		case YoyooChar:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooChar();
		case YoyooDouble:
			return new org.yoyoo.core.engine.yoyoo.lang.YoyooDouble();
		default:
			return null;
		}
	}

	public String getName() {
		// TODO Auto-generated method stub
		return type;
	}

	

}
