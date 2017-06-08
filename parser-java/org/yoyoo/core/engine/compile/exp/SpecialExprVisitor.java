package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooSpecialExprBody;
import org.yoyoo.core.engine.yoyoo.lang.YoyooSpecialExpression;


public class SpecialExprVisitor extends AbstractVisitor {

	protected Stm stm;
	
	protected YoyooSpecialExpression yexpr;
	
	public SpecialExprVisitor(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node);
		this.stm = stm;
		this.yexpr = new YoyooSpecialExpression();
	}

	@Override
	public Object visit(YoyooSpecialExprBody node, Object data) {
		String body = node.first_token.image;
		body = body.substring(1, body.length()-1);
		yexpr.setBody(body);
		return null;
	}



	public YoyooSpecialExpression getYexpr() {
		return yexpr;
	}

}
