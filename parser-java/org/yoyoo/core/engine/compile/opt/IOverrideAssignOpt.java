package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.IType;

public interface IOverrideAssignOpt {
	public void convert(IType type) throws CompileException;
}
