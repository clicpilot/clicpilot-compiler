package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooBlock;
import org.yoyoo.core.engine.parser.YoyooBlockStatementBodyStm;
import org.yoyoo.core.engine.parser.YoyooBreakStatement;
import org.yoyoo.core.engine.parser.YoyooContinueStatement;
import org.yoyoo.core.engine.parser.YoyooDoStatement;
import org.yoyoo.core.engine.parser.YoyooEmptyStatement;
import org.yoyoo.core.engine.parser.YoyooForStatement;
import org.yoyoo.core.engine.parser.YoyooIfStatement;
import org.yoyoo.core.engine.parser.YoyooLabeledStatement;
import org.yoyoo.core.engine.parser.YoyooReturnStatement;
import org.yoyoo.core.engine.parser.YoyooStatementExpression;
import org.yoyoo.core.engine.parser.YoyooSwitchStatement;
import org.yoyoo.core.engine.parser.YoyooSynchronizedStatement;
import org.yoyoo.core.engine.parser.YoyooThrowStatement;
import org.yoyoo.core.engine.parser.YoyooTryStatement;
import org.yoyoo.core.engine.parser.YoyooWhileStatement;

public class StmVisitor extends AbstractVisitor {

	private Stm statement;

	private YoyooBaseMethod method;
	
	private CatchStatement catchStm;

//	public StmVisitor(YoyooBaseMethod method, SimpleNode node) {
//		super(method.getUnit().getCompilationUnit(), node);
//		this.method = method;
//	}
//	
	public StmVisitor(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method.getUnit().getCompilationUnit(), node);
		this.method = method;
		this.catchStm = catchStm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooBlock,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooBlock node, Object data) {
		Block stm = new Block(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		stm.openLocalVariableStack();
		node.childrenAccept(stm, node);
		statement = stm;
		stm.closeLocalVariableStack();
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooStatementExpression,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooStatementExpression node, Object data) {
		StmExpr stm = new StmExpr(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooBreakStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooBreakStatement node, Object data) {
		BreakStm stm = new BreakStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooContinueStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooContinueStatement node, Object data) {
		ContinueStm stm = new ContinueStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooDoStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooDoStatement node, Object data) {
		DoStm stm = new DoStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooEmptyStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooEmptyStatement node, Object data) {
		EmptyStm stm = new EmptyStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooForStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooForStatement node, Object data) {
		ForStm stm = new ForStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		stm.openLocalVariableStack();
		node.childrenAccept(stm, node);
		statement = stm;
		stm.closeLocalVariableStack();
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooIfStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooIfStatement node, Object data) {
		IfStm stm = new IfStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLabeledStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLabeledStatement node, Object data) {
		LabeledStm stm = new LabeledStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooReturnStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooReturnStatement node, Object data) {
		ReturnStm stm = new ReturnStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooSwitchStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooSwitchStatement node, Object data) {
		SwitchStm stm = new SwitchStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooWhileStatement,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooWhileStatement node, Object data) {
		WhileStm stm = new WhileStm(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, node);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	@Override
	public Object visit(YoyooBlockStatementBodyStm node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		
		node.childrenAccept(v, node);
		statement = v.statement;
		return statement;
	}

	@Override
	public Object visit(YoyooSynchronizedStatement node, Object data) {
		SynchronizedStatement stm = new SynchronizedStatement(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, data);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	@Override
	public Object visit(YoyooThrowStatement node, Object data) {
		ThrowStatement stm = new ThrowStatement(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, data);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	@Override
	public Object visit(YoyooTryStatement node, Object data) {
		TryStatement stm = new TryStatement(method, catchStm, node);
		method.pushStmCompilingStack(stm);
		node.childrenAccept(stm, data);
		statement = stm;
		method.popStmCompilingStack();
		return stm;
	}

	/**
	 * @return the stmList
	 */
	public Stm getStatement() {
		return statement;
	}

}
