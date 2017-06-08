package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.ConditionLinkOperator;
import org.yoyoo.core.engine.compile.opt.PopStackFrameOperator;
import org.yoyoo.core.engine.compile.opt.PushStackFrameOperator;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooElseStm;
import org.yoyoo.core.engine.parser.YoyooIfCondExpr;
import org.yoyoo.core.engine.parser.YoyooIfStm;

public class IfStm extends AbstractStm {

	public IfStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private Stm trueStm;

	private Stm falseStm;

	private IAtom condAtom;
	
	private Expr condExpr;

	public Expr getCondExpr() {
		return condExpr;
	}

	@Override
	public Object visit(YoyooElseStm node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		falseStm = v.getStatement();
		return null;
	}

	@Override
	public Object visit(YoyooIfCondExpr node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		condExpr = v.getExprList().get(0);
		return null;
	}

	@Override
	public Object visit(YoyooIfStm node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		trueStm = v.getStatement();
		return null;
	}

	public IAtom getCondAtom() {
		return condAtom;
	}

	public Stm getFalseStm() {
		return falseStm;
	}

	public Stm getTrueStm() {
		return trueStm;
	}

	public Operator convert2Operator() throws CompileException {
		
		condAtom = condExpr.convert2Atom(node);
		ConditionLinkOperator opt = new ConditionLinkOperator(node, unit, ycls,
				null, null, condAtom);

		pushOperator(opt);
		Operator trueLink = trueStm.getOperator();
		trueLink.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		trueLink.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));
		Operator falseLink = falseStm == null ? null : falseStm.getOperator();
		opt.setTrueLink(trueLink);
		opt.setFalseLink(falseLink);
		this.terminatedByReturnOrThrowStm = this.trueStm.isTerminatedByReturnOrThrowStm() && ( falseStm!=null && this.falseStm.isTerminatedByReturnOrThrowStm());
		return popOperator();
	}

	@Override
	public void typeCheck() throws CompileException {
		
		if(!(condAtom.getYoyooType() instanceof PrimitiveType.YoyooBoolean))
			throw new CompileException.InvalidExpr(condAtom.getNode(), condAtom.getCompilationUnit());
	}
	
	

}
