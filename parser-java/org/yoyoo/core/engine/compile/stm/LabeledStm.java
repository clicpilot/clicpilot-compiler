package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.opt.LabelOperator;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooLabeledStmBody;
import org.yoyoo.core.engine.parser.YoyooLabeledStmLabel;

public class LabeledStm extends AbstractStm {

	public LabeledStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private String label;

	private Stm stm;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLabeledStmBody,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLabeledStmBody node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		stm = v.getStatement();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLabeledStmLabel,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLabeledStmLabel node, Object data) {
		label = node.first_token.image;
		return null;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the stm
	 */
	public Stm getStm() {
		return stm;
	}

	public Operator convert2Operator() throws CompileException {
		this.terminatedByReturnOrThrowStm = stm.isTerminatedByReturnOrThrowStm();
		LabelOperator opt = new LabelOperator(node, unit, ycls, label, null);
		opt.mark();
		pushOperator(opt);
		opt.setOpt(stm.getOperator());
		return popOperator();
	}

}
