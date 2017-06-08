package org.yoyoo.core.engine.compile.exp;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.opt.FuncArgumentsOperator;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooArgument;

public class ArgumentsExpr extends AbstractExpr {

	public ArgumentsExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
		
		
	}

	private List<IAtom> arguments;
	
	private List<Expr> argumentExprs;


	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooArgument,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooArgument node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, stm);
		node.childrenAccept(v, node);
		
		if (argumentExprs == null) {
			argumentExprs = new ArrayList<Expr>();
		}
		argumentExprs.add(v.getExprList().get(0));

		
		return null;

	}

	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		// PushStackFrameOperator push = new PushStackFrameOperator(node, unit);
		FuncArgumentsOperator opt = new FuncArgumentsOperator(node, unit, ycls);
		if (argumentExprs!=null && argumentExprs.size()>0) {
			if(arguments == null)
				arguments = new ArrayList<IAtom>();
			for(Expr expr : argumentExprs) {
				arguments.add(expr.convert2Atom(expr.getNode()));
			}
		}
		if (arguments != null)
			opt.installArguments(arguments.toArray(new IAtom[0]));
		
		
		
		// OperatorList list = new OperatorList(node, unit);
		// list.addOperator(push);
		// list.addOperator(opt);
		return opt;
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<IAtom> getArguments() {
		return arguments;
	}
	

	public List<Expr> getArgumentExprs() {
		return argumentExprs;
	}


}
