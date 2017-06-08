package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.FuncReturnOperator;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooReturnExpr;

public class ReturnStm extends AbstractStm {

	public ReturnStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		this.terminatedByReturnOrThrowStm = true;
	}

	private IAtom atom;
	
	private Expr returnExpr;

	@Override
	public Object visit(YoyooReturnExpr node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		
		if (v.getExprList().size() > 0)
			returnExpr = v.getExprList().get(0);
		
		return null;
	}

	public IAtom getAtom() {
		return atom;
	}

	public Operator convert2Operator() throws CompileException {
		if(returnExpr!=null) 
			atom = returnExpr.convert2Atom(node);
		return (new FuncReturnOperator(node, ycls.getCompilationUnit(), ycls,
				atom, (YoyooMethod) method));

	}

	public Expr getReturnExpr() {
		return returnExpr;
	}

}
