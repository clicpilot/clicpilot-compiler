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

public class EmptyOperator extends AbstractOperator {

	public EmptyOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);

	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {


	}

	public IType operatorTypeCheck() throws CompileException {

		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
