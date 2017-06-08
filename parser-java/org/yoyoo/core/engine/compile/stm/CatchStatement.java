package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.opt.CatchOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.parser.SimpleNode;

public class CatchStatement extends AbstractStm {

	private Stm body;

	private FormalParameter exception;

	public CatchStatement(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
	}

	public void setBody(Stm body) {
		this.body = body;
	}

	public void setException(FormalParameter exception) {
		this.exception = exception;
	}

	@Override
	protected Operator convert2Operator() throws CompileException {
		CatchOperator catchOperator = new CatchOperator(node, unit, ycls, exception, (OperatorList)body.getOperator());
		return catchOperator;
	}

	public Stm getBody() {
		return body;
	}

	public FormalParameter getException() {
		return exception;
	}


}
