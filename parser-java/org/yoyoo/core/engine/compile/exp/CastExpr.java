package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.opt.CastOperator;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooCastExpr;
import org.yoyoo.core.engine.parser.YoyooCastType;

public class CastExpr extends AbstractExpr {

	private IType castType;
	private Expr  castExpr;
	
	public CastExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		
		return null;
	}

	@Override
	public IAtom convert2Atom(SimpleNode node) throws CompileException {		
		return new CastOperator(node, unit, ycls, castType, (IAtom)castExpr.convert2Atom(node));
	}

	@Override
	public Object visit(YoyooCastExpr node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, stm);
		node.childrenAccept(v, data);
		castExpr = v.getExprList().get(0);
		return null;
	}

	@Override
	public Object visit(YoyooCastType node, Object data) {
		TypeVisitor v = new TypeVisitor(unit, node);
		node.childrenAccept(v, data);
		castType = v.getType();
		return null;
	}
	
	public IType getCastType() {
		return castType;
	}

	public Expr getCastExpr() {
		return castExpr;
	}

}
