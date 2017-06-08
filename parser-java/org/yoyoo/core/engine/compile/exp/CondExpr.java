package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.opt.OperatorFactory;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooCondExpr;
import org.yoyoo.core.engine.parser.YoyooCondFalseExpr;
import org.yoyoo.core.engine.parser.YoyooCondTrueExpr;

public class CondExpr extends AbstractExpr {

	public CondExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
		// TODO Auto-generated constructor stub
	}

	private IAtom condition;

	private IAtom trueAtom;

	private IAtom falseAtom;
	
	private Expr trueExpr;

	private Expr falseExpr;
	
	private Expr condExpr;

	
	/**
	 * @return the condition
	 */
	public IAtom getCondition() {
		return condition;
	}

	/**
	 * @return the falseAtom
	 */
	public IAtom getFalseAtom() {
		return falseAtom;
	}

	/**
	 * @return the trueAtom
	 */
	public IAtom getTrueAtom() {
		return trueAtom;
	}

	@Override
	public Object visit(YoyooCondExpr node, Object data) {
		
		ExprVisitor v = new ExprVisitor(unit, node, stm);
		node.childrenAccept(v, data);
		condExpr = v.getExprList().get(0);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooCondFalseExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooCondFalseExpr node, Object data) {
		if (data instanceof YoyooCondExpr) {
			ExprVisitor v = new ExprVisitor(unit, node, stm);
			node.childrenAccept(v, data);
			
			this.falseExpr = v.getExprList().get(0);
			
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooCondTrueExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooCondTrueExpr node, Object data) {
		if (data instanceof YoyooCondExpr) {
			ExprVisitor v = new ExprVisitor(unit, node, stm);
			node.childrenAccept(v, data);
			
			this.trueExpr = v.getExprList().get(0);
			
		}
		return null;
	}

	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		if(condition==null) {
			condition = condExpr.convert2Atom(condExpr.getNode());
		}
		if(trueAtom==null) {
			trueAtom = trueExpr.convert2Atom(trueExpr.getNode());
		}
		
		if(falseAtom==null) {
			falseAtom = falseExpr.convert2Atom(falseExpr.getNode());
		}
		
		Operator operator = OperatorFactory.createOperator("?", new IAtom[] {
				condition, trueAtom, falseAtom }, node, unit, ycls);
		return operator;
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		if (data instanceof YoyooCondExpr) {
			ExprVisitor v = new ExprVisitor(unit, node, stm);
			((YoyooCondExpr)data).childrenAccept(v, data);
			condExpr = v.getExprList().get(0);
		}
		return null;
	}
	

	public Expr getTrueExpr() {
		return trueExpr;
	}

	public Expr getFalseExpr() {
		return falseExpr;
	}

	public Expr getCondExpr() {
		return condExpr;
	}



}
