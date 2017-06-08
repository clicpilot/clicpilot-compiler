package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAlloExpr;
import org.yoyoo.core.engine.parser.YoyooArguments;
import org.yoyoo.core.engine.parser.YoyooArrayDecl;
import org.yoyoo.core.engine.parser.YoyooArrayDimsExpr;
import org.yoyoo.core.engine.parser.YoyooAssignExpr;
import org.yoyoo.core.engine.parser.YoyooBionExpr;
import org.yoyoo.core.engine.parser.YoyooCondExpr;
import org.yoyoo.core.engine.parser.YoyooPostfixExpr;
import org.yoyoo.core.engine.parser.YoyooPrimaryExpr;

public abstract class AbstractExprVisitor extends AbstractVisitor {


	protected Stm stm;


	protected YoyooTypeDefineClass ycls;

	public AbstractExprVisitor(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node);
		this.stm = stm;
		this.ycls = stm.getYcls();
		
		// TODO Auto-generated constructor stub
	}

	public Object visit(YoyooArguments node, Object data) {
		return this.myVisit(node, data);
	}

	public Object visit(YoyooAssignExpr node, Object data) {
		return this.myVisit(node, data);
	}

	public Object visit(YoyooCondExpr node, Object data) {
		return this.myVisit(node, data);
	}

	public Object visit(YoyooBionExpr node, Object data) {
		return this.myVisit(node, data);
	}

	public Object visit(YoyooPrimaryExpr node, Object data) {
		return this.myVisit(node, data);
	}

	public Object visit(YoyooAlloExpr node, Object data) {
		return this.myVisit(node, data);
	}

	@Override
	public Object visit(YoyooArrayDecl node, Object data) {
		return this.myVisit(node, data);
	}

	@Override
	public Object visit(YoyooArrayDimsExpr node, Object data) {
		return this.myVisit(node, data);
	}
	
	@Override
	public Object visit(YoyooPostfixExpr node, Object data) {
		return this.myVisit(node, data);
	}
	

	public Stm getStm() {
		return stm;
	}



	public abstract Object myVisit(SimpleNode node, Object data);

}
