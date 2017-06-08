package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.exp.PrimaryExpr;
import org.yoyoo.core.engine.compile.opt.AtomOperator;
import org.yoyoo.core.engine.compile.opt.OperatorFactory;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAssignmentOperator;
import org.yoyoo.core.engine.parser.YoyooPrefixOpt;
import org.yoyoo.core.engine.parser.YoyooPrimaryExpr;
import org.yoyoo.core.engine.parser.YoyooStmExprAssignExpr;
import org.yoyoo.core.engine.parser.YoyooStmExprPostOpt;

public class StmExpr extends AbstractStm {

	public StmExpr(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private OperatorMark preOptMark;

	private OperatorMark postOptMark;

	

	private OperatorMark assignOptMark;

	private IAtom atom;
	
	private Expr expr;

	public Expr getExpr() {
		return expr;
	}

	public Expr getAssignExpr() {
		return assignExpr;
	}

	private IAtom assignAtom;
	
	private Expr assignExpr;

//	private ArgumentsExpr arguments;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooAssignmentOperator,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooAssignmentOperator node, Object data) {
		assignOptMark = new OperatorMark(node.first_token.image);
		return null;
	}

	@Override
	public Object visit(YoyooPrefixOpt node, Object data) {
		preOptMark = new OperatorMark(node.first_token.image);
		return super.visit(node, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooPrimaryExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooPrimaryExpr node, Object data) {
		expr = new PrimaryExpr(unit, node, this);
		node.childrenAccept(expr, node);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooStmExprAssignExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooStmExprAssignExpr node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		assignExpr = v.getExprList().get(0);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooStmExprPostOpt,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooStmExprPostOpt node, Object data) {
		postOptMark = new OperatorMark(node.first_token.image);
		return null;
	}

	/**
	 * @return the assignAtom
	 */
	public IAtom getAssignAtom() {
		return assignAtom;
	}

	/**
	 * @return the atom
	 */
	public IAtom getAtom() {
		return atom;
	}

	/**
	 * @return the postOptMark
	 */
	public OperatorMark getPostOptMark() {
		return postOptMark;
	}
	
	public OperatorMark getPreOptMark() {
		return preOptMark;
	}

	public OperatorMark getAssignOptMark() {
		return assignOptMark;
	}

	public Operator convert2Operator() throws CompileException {
		atom = expr.convert2Atom(expr.getNode());
		if(assignExpr!=null)
			assignAtom = assignExpr.convert2Atom(assignExpr.getNode());
		Operator opt = null;
		if (preOptMark != null)
			opt = OperatorFactory.createOperator(preOptMark.getOpt().getStr(),
					new IAtom[] { atom }, node, unit, ycls);
		else if (postOptMark != null)
			opt = OperatorFactory.createOperator(postOptMark.getOpt().getStr(),
					new IAtom[] { atom }, node, unit, ycls);
		else if (assignOptMark != null)
			opt = OperatorFactory.createOperator(assignOptMark.getOpt()
					.getStr(), new IAtom[] { atom, assignAtom }, node, unit,
					ycls);
		else
			opt = new AtomOperator(node, unit, ycls, atom);
		return opt;
	}

}
