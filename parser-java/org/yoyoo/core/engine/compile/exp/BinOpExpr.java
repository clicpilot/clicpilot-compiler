package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.opt.InstanceofOperator;
import org.yoyoo.core.engine.compile.opt.OperatorFactory;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooOpt;

public class BinOpExpr extends AbstractExpr {

	public BinOpExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
	}

	protected OperatorMark opt;

	protected IAtom left;

	protected IAtom right;
	
	protected Expr leftExpr;
	
	protected Expr rightExpr;
	
	protected IType instanceofType;
	


	/**
	 * @return the left
	 */
	public IAtom getLeft() {
		return left;
	}

	/**
	 * @return the opt
	 */
	public OperatorMark getOpt() {
		return opt;
	}

	/**
	 * @return the right
	 */
	public IAtom getRight() {
		return right;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooOpt,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooOpt node, Object data) {
		opt = new OperatorMark(node.first_token.image);
		return null;
	}

	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		if(left==null) {
			left = leftExpr.convert2Atom(leftExpr.getNode());
		}
		Operator operator = null;
		if(right==null && rightExpr!=null) {
			right = rightExpr.convert2Atom(rightExpr.getNode());
			operator = OperatorFactory.createOperator(opt.getOpt()
					.getStr(), new IAtom[] { left, right }, node, unit, ycls);
			operator.installArguments(new IAtom[] { left, right });
		} else if (this.instanceofType!=null){
			operator = new InstanceofOperator(node, unit, ycls, instanceofType);
			operator.installArguments(new IAtom[] { left});
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}		
		
		return operator;
	}

	public Object myVisit(SimpleNode node, Object data) {
		
			ExprVisitor v = new ExprVisitor(unit, node, stm);
			//((SimpleNode) data).childrenAccept(v, data);
			node.jjtAccept(v, data);
			if (leftExpr == null) {
				
				leftExpr = v.getExprList().get(0);
				
			} else if (rightExpr == null) {
				
				rightExpr = v.getExprList().get(0);
				
			}
			if(rightExpr==null) {
				TypeVisitor tv = new TypeVisitor(unit, node);
				((SimpleNode) data).childrenAccept(tv, data);
				instanceofType = tv.getType();
			}
			
		
		return null;
	}
	
	public Expr getLeftExpr() {
		return leftExpr;
	}

	public Expr getRightExpr() {
		return rightExpr;
	}

	public IType getInstanceofType() {
		return instanceofType;
	}
}
