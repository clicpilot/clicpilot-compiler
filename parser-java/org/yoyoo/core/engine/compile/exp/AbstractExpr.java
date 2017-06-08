package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;

public abstract class AbstractExpr extends AbstractExprVisitor implements Expr {

	public AbstractExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);

	}
	
	public SimpleNode getNode() {
		return node;
	}
	

	

}
