package org.yoyoo.core.engine.compile.exp;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.opt.ArrayInitOperator;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooVariableInitializer;

public class ArrayInitExpr extends AbstractExpr {

	private List<Expr> initExprs;

	private IType type;
	
	public ArrayInitExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
	}
	

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		return null;
	}

	@Override
	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		int size = initExprs==null?0:initExprs.size();
		IAtom[] atoms = new IAtom[size];
		for(int i=0;i<size;i++) {
			Expr initExprItem  = initExprs.get(i);
			atoms[i] = initExprItem.convert2Atom(initExprItem.getNode());
		}
		return new ArrayInitOperator(node, unit, ycls, type, atoms);
	}
	

	@Override
	public Object visit(YoyooVariableInitializer node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, stm);
		node.childrenAccept(v, node);
		Expr expr = v.getExprList().get(0);
		if(initExprs == null) {
			initExprs = new ArrayList<Expr>();
		}
		initExprs.add(expr);
		return null;
	}

	public List<Expr> getInitExprs() {
		return initExprs;
	}


}
