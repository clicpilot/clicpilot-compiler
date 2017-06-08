package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.opt.EmptyOperator;
import org.yoyoo.core.engine.parser.SimpleNode;

public class EmptyStm extends AbstractStm {

	public EmptyStm(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Operator convert2Operator() throws CompileException {
		return (new EmptyOperator(node, ycls.getCompilationUnit(), ycls));
	}

}
