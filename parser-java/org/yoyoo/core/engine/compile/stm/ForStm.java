package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.LoopOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.opt.PopStackFrameOperator;
import org.yoyoo.core.engine.compile.opt.PushStackFrameOperator;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooForCond;
import org.yoyoo.core.engine.parser.YoyooForInit;
import org.yoyoo.core.engine.parser.YoyooForUpdate;
import org.yoyoo.core.engine.parser.YoyooLocalVariableDeclaration;
import org.yoyoo.core.engine.parser.YoyooStatementExpression;

public class ForStm extends LoopStm implements ILocalVariableStm {

	public ForStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private StmList initStms;

	private StmList updateStms;
	
	private Expr forCondExpr;
	
	private IAtom condAtom;
	
	private ForInit init;
	
	private ForUpdate update;

	@Override
	public Object visit(YoyooForCond node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		forCondExpr = v.getExprList().get(0);
		
		return super.visit(node, data);
	}

	@Override
	public Object visit(YoyooForInit node, Object data) {
		init = new ForInit(method, catchStm, this, node);
		node.childrenAccept(init, node);
		return null;
	}

	@Override
	public Object visit(YoyooForUpdate node, Object data) {
		update = new ForUpdate(method, catchStm, this, node);
		node.childrenAccept(update, node);
		return null;
	}

	public static class ForInit extends AbstractStm {
		private ForStm forStm;

		public ForInit(YoyooBaseMethod method, CatchStatement catchStm, ForStm forStm, SimpleNode node) {
			super(method, catchStm, node);
			this.forStm = forStm;
		}

		@Override
		public Object visit(YoyooLocalVariableDeclaration node, Object data) {
			YoyooVariable decl = new YoyooVariable(ycls, unit, node);
			// forStm.addLocalVariableDeclaration(decl, node);

			node.childrenAccept(decl, node);
			if (forStm.initStms == null) {
				forStm.initStms = new StmList(method, catchStm, node);
			}
			forStm.registerNewLocalVariable(decl, node);
			forStm.initStms.add(decl);
			return null;
		}

		@Override
		public Object visit(YoyooStatementExpression node, Object data) {
			StmExpr stm = new StmExpr(method, catchStm, node);
			node.childrenAccept(stm, node);
			forStm.initStms = new StmList(method, catchStm, node);
			forStm.initStms.add(stm);
			return null;
		}
		@Override
		public Operator convert2Operator() throws CompileException {
			return forStm.initStms.getOperator();
		}

	}

	public static class ForUpdate extends AbstractStm {

		@Override
		public Object visit(YoyooStatementExpression node, Object data) {
			StmExpr stm = new StmExpr(method, catchStm, node);
			node.childrenAccept(stm, node);
			forStm.updateStms = new StmList(method, catchStm, node);

			forStm.updateStms.add(stm);
			return null;
		}

		private ForStm forStm;

		public ForUpdate(YoyooBaseMethod method, CatchStatement catchStm, ForStm forStm, SimpleNode node) {
			super(method, catchStm, node);
			this.forStm = forStm;
		}
		@Override
		public Operator convert2Operator() throws CompileException {
			return forStm.updateStms.getOperator();
		}
	}

	public IAtom getCondAtom() {
		return condAtom;
	}

	public StmList getInitStms() {
		return initStms;
	}


	public StmList getUpdateStms() {
		return updateStms;
	}
	
	public Expr getForCondExpr() {
		return forCondExpr;
	}


	@Override
	public void typeCheck() throws CompileException {
		this.terminatedByReturnOrThrowStm = this.bodyStm.isTerminatedByReturnOrThrowStm();
	}

	public Operator convert2Operator() throws CompileException {
		//this.terminatedByReturnStm = this.bodyStm.isTerminatedByReturnStm();

		OperatorList forList = new OperatorList(node,
				ycls.getCompilationUnit(), ycls);
		forList.mark();
		forList.loop();
		pushOperator(forList);
		Operator initOpt = init.getOperator();
		
		condAtom = this.forCondExpr.convert2Atom(node);
		
		
		forList.addOperator(initOpt);

		LoopOperator loopOpt = new LoopOperator(node,
				ycls.getCompilationUnit(), ycls, condAtom, null, null);
		
		pushOperator(loopOpt);
		Operator loopBody = bodyStm.getOperator();
		loopOpt.setLoopBody(loopBody);		
		loopBody.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		loopBody.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));
		if(update!=null) {
			Operator updateOpt = update.getOperator();
			loopOpt.setLoopUpdate(updateOpt);
		}
		forList.addOperator(popOperator());

		forList.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		forList.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));

		return popOperator();

	}

	public void closeLocalVariableStack() {
		this.unit.popLocalVariableStack();

	}

	public void openLocalVariableStack() {

		this.unit.pushNewLocalVariableStack();
		this.variableTable = this.unit.getCurrentLocalVariableTable();
	}

	public void registerNewLocalVariable(YoyooVariable decl, SimpleNode node) {
		try {
			this.unit.registerNewLocalVariable(decl, node);
		} catch (CompileException e) {
			this.unit.addError(e);
		}

	}

}
