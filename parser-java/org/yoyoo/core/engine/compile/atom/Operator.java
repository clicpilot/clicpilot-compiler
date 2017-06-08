package org.yoyoo.core.engine.compile.atom;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public interface Operator extends IAtom {
	public IRuntimeValueAtom evaluate(RuntimeContext ctx)
			throws YoyooRTException;

	public void installArguments(IAtom[] arguments) throws CompileException;

	public void setBefore(Operator before);

	public void setAfter(Operator after);

	public String getLabel();

	public void setLabel(String label);

	public IType typeCheck() throws CompileException;

}
