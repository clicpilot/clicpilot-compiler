package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public interface YoyooClass {
	public boolean isPrimitive();
	public YoyooObject instance(RuntimeContext ctx);
	public String getName();
}
