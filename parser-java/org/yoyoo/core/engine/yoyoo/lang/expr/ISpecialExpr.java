package org.yoyoo.core.engine.yoyoo.lang.expr;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;


public interface ISpecialExpr {
	public void syntaxCheck() throws CompileException;
	public void execute(YoyooObject params, YoyooRef<YoyooObject> returnRef, RuntimeContext ctx) throws YoyooRTException;
	public void execute() throws YoyooRTException;
}
