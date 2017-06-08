package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.opt.OperatorFactory;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooPostfixOpt;
import org.yoyoo.core.engine.parser.YoyooPrimaryExpr;

public class PostfixExpr  extends AbstractExpr {

	public PostfixExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
	}

	protected OperatorMark optMark;
	
	protected Expr leftExpr;
	
	public Expr getLeftExpr() {
		return leftExpr;
	}

	public OperatorMark getOptMark() {
		return optMark;
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		return null;
	}

	@Override
	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		IAtom atom = leftExpr.convert2Atom(node);
		Operator operator = OperatorFactory.createOperator(optMark
				.getOpt().getStr(), new IAtom[] {atom}, node,
				unit, ycls);
		return operator;
	}

	@Override
	public Object visit(YoyooPostfixOpt node, Object data) {
		optMark = new OperatorMark(node.first_token.image);
		return null;
	}
	
	@Override
	public Object visit(YoyooPrimaryExpr node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, stm);
		node.jjtAccept(v, data);
		leftExpr = v.getExprList().get(0);
		return null;
	}

}
