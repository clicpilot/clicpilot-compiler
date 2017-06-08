package org.yoyoo.core.engine.compile.exp;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAlloExpr;
import org.yoyoo.core.engine.parser.YoyooArguments;
import org.yoyoo.core.engine.parser.YoyooArrayInit;
import org.yoyoo.core.engine.parser.YoyooAssignExpr;
import org.yoyoo.core.engine.parser.YoyooBionExpr;
import org.yoyoo.core.engine.parser.YoyooCastExpression;
import org.yoyoo.core.engine.parser.YoyooCondExpr;
import org.yoyoo.core.engine.parser.YoyooPostfixExpr;
import org.yoyoo.core.engine.parser.YoyooPrimaryExpr;

public class ExprVisitor extends AbstractVisitor {

	private List<Expr> exprList;

	protected Stm stm;


	public ExprVisitor(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node);
		this.stm = stm;
		exprList = new ArrayList<Expr>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooArguments,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooArguments node, Object data) {
		Expr expr = new ArgumentsExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooAssignExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooAssignExpr node, Object data) {
		Expr expr = new AssignExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooCondExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooCondExpr node, Object data) {
		Expr expr = new CondExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooBionExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooBionExpr node, Object data) {

		Expr expr = new BinOpExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooPrimaryExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooPrimaryExpr node, Object data) {
		Expr expr = new PrimaryExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooAlloExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooAlloExpr node, Object data) {
		Expr expr = new AllocationExpr(unit, node, stm);
		node.childrenAccept(expr, expr);
		exprList.add(expr);
		return null;
	}
	
	

	@Override
	public Object visit(YoyooArrayInit node, Object data) {
		Expr expr = new ArrayInitExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}
	
	@Override
	public Object visit(YoyooCastExpression node, Object data) {
		Expr expr = new CastExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}
	
	@Override
	public Object visit(YoyooPostfixExpr node, Object data) {
		Expr expr = new PostfixExpr(unit, node, stm);
		node.childrenAccept(expr, node);
		exprList.add(expr);
		return null;
	}

	/**
	 * @return the exprList
	 */
	public List<Expr> getExprList() {
		return exprList;
	}

}
