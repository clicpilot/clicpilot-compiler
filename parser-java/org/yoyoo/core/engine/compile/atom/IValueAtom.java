package org.yoyoo.core.engine.compile.atom;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.IType;


public interface IValueAtom extends IAtom {
	
	public IType getAssignedType() throws CompileException;
	
}
