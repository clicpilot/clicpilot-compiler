package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.LoopOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.opt.PopStackFrameOperator;
import org.yoyoo.core.engine.compile.opt.PushStackFrameOperator;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooWhileCond;

public class WhileStm extends LoopStm {

	public WhileStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private IAtom condAtom;

	private Expr forCondExpr;
	
	public Expr getForCondExpr() {
		return forCondExpr;
	}

	@Override
	public Object visit(YoyooWhileCond node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		forCondExpr = v.getExprList().get(0);		
		return null;
	}

	public IAtom getCondAtom() {
		return condAtom;
	}



	public Operator convert2Operator() throws CompileException {
		this.terminatedByReturnOrThrowStm = bodyStm.isTerminatedByReturnOrThrowStm();
		condAtom = this.forCondExpr.convert2Atom(node);
		OperatorList whileList = new OperatorList(node, ycls
				.getCompilationUnit(), ycls);
		whileList.mark();
		whileList.loop();
		pushOperator(whileList);
		Operator loopBody = bodyStm.getOperator();
		LoopOperator loopOpt = new LoopOperator(node,
				ycls.getCompilationUnit(), ycls, condAtom, loopBody,
				null);
		
		loopBody.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		loopBody.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));		
		
		whileList.addOperator(loopOpt);
		
		

		whileList.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		whileList.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));

		return popOperator();

	}

}
