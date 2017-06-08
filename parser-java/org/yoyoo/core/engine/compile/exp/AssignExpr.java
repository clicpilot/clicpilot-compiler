package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAssignExpr;
import org.yoyoo.core.engine.parser.YoyooAssignVal;

public class AssignExpr extends BinOpExpr {

	public AssignExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
		this.opt = new OperatorMark(OperatorMark.MARK.ASSIGN);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooAssignExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooAssignExpr node, Object data) {
		if (data instanceof YoyooAssignExpr) {
			this.opt = new OperatorMark(node.first_token.image);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooAssignVal,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooAssignVal node, Object data) {
		if (data instanceof YoyooAssignExpr) {
			ExprVisitor v = new ExprVisitor(unit, node, stm);
			node.childrenAccept(v, data);
			try {
				this.right = v.getExprList().get(0).convert2Atom(node);
			} catch (CompileException e) {
				unit.addError(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.SimpleNode,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(SimpleNode node, Object data) {
		if (data instanceof YoyooAssignExpr) {
			ExprVisitor v = new ExprVisitor(unit, node, stm);
			node.childrenAccept(v, data);
			try {
				this.left = v.getExprList().get(0).convert2Atom(node);
			} catch (CompileException e) {
				unit.addError(e);
			}
		}
		return super.visit(node, data);
	}

}
