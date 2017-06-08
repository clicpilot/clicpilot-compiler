package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooLoopStm;

public abstract class LoopStm extends AbstractStm {

	protected Stm bodyStm;

	public Stm getBodyStm() {
		return bodyStm;
	}

	public LoopStm(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
	}

	@Override
	public Object visit(YoyooLoopStm node, Object data) {

		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		bodyStm  = v.getStatement();

		return null;
	}

}
