package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.PopStackFrameOperator;
import org.yoyoo.core.engine.compile.opt.PushStackFrameOperator;
import org.yoyoo.core.engine.compile.opt.SynOperator;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooSynBlock;
import org.yoyoo.core.engine.parser.YoyooSynExpr;

public class SynchronizedStatement extends AbstractStm  {

	private Expr synExpr;

	private Stm synStm;
	
	public SynchronizedStatement(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
	}

	@Override
	protected Operator convert2Operator() throws CompileException {
		SynOperator opt = new SynOperator(node, unit, ycls, synExpr.convert2Atom(synExpr.getNode()));
		this.pushOperator(opt);
		Operator stmOpt = synStm.getOperator();
		opt.installArguments(new IAtom[]{stmOpt});
		opt.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		opt.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));
		return this.popOperator();
	}

	@Override
	public Object visit(YoyooSynBlock node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		synStm = v.getStatement();
		return null;
	}

	@Override
	public Object visit(YoyooSynExpr node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		synExpr = v.getExprList().get(0);		
		return null;
	}
	
	
	public Expr getSynExpr() {
		return synExpr;
	}

	public Stm getSynStm() {
		return synStm;
	}

}
