package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.declaration.YoyooClass;


public interface IPrimitiveObject extends IYoyooObject {
	public YoyooClass getYoyooClass();
	public Object getVal();
}
