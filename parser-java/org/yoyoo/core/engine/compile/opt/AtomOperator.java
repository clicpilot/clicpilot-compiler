package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class AtomOperator extends AbstractOperator {

	protected IAtom atom;

	public AtomOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom atom) {
		super(node, unit, ycls);
		this.atom = atom;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		return atom.getVal(ctx);
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
	}

	public IType operatorTypeCheck() throws CompileException {

		return atom.getYoyooType();
	}

}
