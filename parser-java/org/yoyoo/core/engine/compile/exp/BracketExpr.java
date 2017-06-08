package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.opt.BracketOperator;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooBracketExpr;

public class BracketExpr extends AbstractExpr {

	private Expr expr;
	
	public Expr getExpr() {
		return expr;
	}

	private IAtom atom;
	
	public BracketExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		return null;
	}

	@Override
	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		atom = new BracketOperator(node, unit, ycls, expr.convert2Atom(node));
		return atom;
	}
	
	@Override
	public Object visit(YoyooBracketExpr node, Object data)  {
		ExprVisitor v = new ExprVisitor(unit, node, stm);
		node.childrenAccept(v, node);
		expr = v.getExprList().get(0);
		return null;
	}

}
