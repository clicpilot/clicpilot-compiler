package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class PushStackFrameOperator extends AbstractOperator {

	private boolean copyVariables;

	public PushStackFrameOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, boolean copyVariables) {
		super(node, unit, ycls);
		this.copyVariables = copyVariables;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		ctx.pushStackFrame(copyVariables);
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		// TODO Auto-generated method stub

	}

	public IType operatorTypeCheck() throws CompileException {
		// TODO Auto-generated method stub
		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
