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
import org.yoyoo.core.engine.parser.YoyooDoCond;

public class DoStm extends LoopStm{

	public DoStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}
	
	private Expr forCondExpr;
	
	public Expr getForCondExpr() {
		return forCondExpr;
	}

	private IAtom condAtom;

	@Override
	public Object visit(YoyooDoCond node, Object data) {
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
		
		OperatorList doList = new OperatorList(node, ycls.getCompilationUnit(),
				ycls);
		doList.mark();
		doList.loop();
		pushOperator(doList);
		condAtom = this.forCondExpr.convert2Atom(node);
		Operator loopBody = bodyStm.getOperator();
		doList.addOperator(loopBody);
		LoopOperator loopOpt = new LoopOperator(node,
				ycls.getCompilationUnit(), ycls, condAtom, loopBody, null);
		doList.addOperator(loopOpt);
		doList.setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		doList.setAfter(new PopStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls));
		return popOperator();

	}

}
