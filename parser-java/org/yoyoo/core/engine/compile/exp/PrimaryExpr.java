package org.yoyoo.core.engine.compile.exp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.NullAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.atom.ValueAtom;
import org.yoyoo.core.engine.compile.opt.AllocationOperator;
import org.yoyoo.core.engine.compile.opt.ArrayIndexOperator;
import org.yoyoo.core.engine.compile.opt.DotOperator;
import org.yoyoo.core.engine.compile.opt.FuncArgumentsOperator;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.opt.OperatorFactory;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.opt.SpecialExprOperator;
import org.yoyoo.core.engine.compile.stm.CatchStatement;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.stm.TryStatement;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.Token;
import org.yoyoo.core.engine.parser.YoyooAlloExpr;
import org.yoyoo.core.engine.parser.YoyooArguments;
import org.yoyoo.core.engine.parser.YoyooArrayIndexExpr;
import org.yoyoo.core.engine.parser.YoyooBracketExpr;
import org.yoyoo.core.engine.parser.YoyooLiteralBoolean;
import org.yoyoo.core.engine.parser.YoyooLiteralChar;
import org.yoyoo.core.engine.parser.YoyooLiteralFloat;
import org.yoyoo.core.engine.parser.YoyooLiteralInteger;
import org.yoyoo.core.engine.parser.YoyooLiteralNull;
import org.yoyoo.core.engine.parser.YoyooLiteralString;
import org.yoyoo.core.engine.parser.YoyooNameExpr;
import org.yoyoo.core.engine.parser.YoyooPostfixOpt;
import org.yoyoo.core.engine.parser.YoyooPrefixOpt;
import org.yoyoo.core.engine.parser.YoyooRefId;
import org.yoyoo.core.engine.parser.YoyooSpecialExpr;
import org.yoyoo.core.engine.parser.YoyooSuperRefId;
import org.yoyoo.core.engine.parser.YoyooThis;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class PrimaryExpr extends AbstractExpr {

	public PrimaryExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
	}

	private Stack<OperatorMark> prefixOptStack;

	private Stack<OperatorMark> postfixOptStack;

	private IAtom prefixAtom;
	
	private Expr prefixExpr;

	private List<IAtom> postfixAtoms;
	
	private List<Expr> postfixExprs;	

	private transient List<TryStatement> wrappedTryStatements;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooPostfixOpt,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooPostfixOpt node, Object data) {
		if (postfixOptStack == null) {
			postfixOptStack = new Stack<OperatorMark>();
		}
		postfixOptStack.add(new OperatorMark(node.first_token.image));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooPrefixOpt,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooPrefixOpt node, Object data) {
		if (prefixOptStack == null) {
			prefixOptStack = new Stack<OperatorMark>();
		}
		prefixOptStack.add(new OperatorMark(node.first_token.image));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooAlloExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooAlloExpr node, Object data) {
		this.prefixExpr = new AllocationExpr(unit, node, stm);
		node.childrenAccept(this.prefixExpr, node);
//		try {
//			prefixAtom = expr.convert2Atom(node);
//		} catch (CompileException e) {
//			unit.addError(e);
//		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLiteralBoolean,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLiteralBoolean node, Object data) {
		YoyooBoolean primary = new YoyooBoolean(Boolean
				.valueOf(node.first_token.image));
		prefixAtom = new ValueAtom(new PrimitiveType.YoyooBoolean(unit, node), primary, node,
				unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLiteralChar,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLiteralChar node, Object data) {
		YoyooChar primary = new YoyooChar(Character
				.valueOf(node.first_token.image.charAt(1)));
		prefixAtom = new ValueAtom(new PrimitiveType.YoyooChar(unit, node), primary, node, unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLiteralFloat,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLiteralFloat node, Object data) {

		String val = node.first_token.image;
		YoyooObject primary = null;
		if(val.toLowerCase().endsWith("f")) {
			primary = new YoyooFloat(val.substring(0, val.length()-1));
			prefixAtom = new ValueAtom(new PrimitiveType.YoyooFloat(unit, node), primary, node,
					unit);
		} else if(val.toLowerCase().endsWith("d")) {
			primary = new YoyooDouble(val.substring(0, val.length()-1));
			prefixAtom = new ValueAtom(new PrimitiveType.YoyooDouble(unit, node), primary, node,
					unit);
		} else {
			primary = new YoyooDouble(val);
			prefixAtom = new ValueAtom(new PrimitiveType.YoyooDouble(unit, node), primary, node,
					unit);
		}  
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLiteralInteger,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLiteralInteger node, Object data) {
		String val = node.first_token.image;
		YoyooObject primary = null;
		if(val.toLowerCase().endsWith("s")) {
			primary = new YoyooShort(val.substring(0, val.length()-1));
			prefixAtom = new ValueAtom(new PrimitiveType.YoyooShort(unit, node), primary, node,
					unit);
		} else if (val.toLowerCase().endsWith("l")) {
			primary = new YoyooLong(val.substring(0, val.length()-1));
			prefixAtom = new ValueAtom(new PrimitiveType.YoyooLong(unit, node), primary, node,
					unit);
		} else {
			primary = new YoyooInteger(val);
			prefixAtom = new ValueAtom(new PrimitiveType.YoyooInteger(unit, node), primary, node,
					unit);
		}  
		
		return null;
	}

	@Override
	public Object visit(YoyooSpecialExpr node, Object data) {
		SpecialExprVisitor v = new SpecialExprVisitor(unit, node, stm);
		node.childrenAccept(v, node);		
		prefixAtom = new SpecialExprOperator(v.getYexpr(), node, unit, ycls);
		return null;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLiteralNull,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLiteralNull node, Object data) {
		prefixAtom = new NullAtom(new PrimitiveType.YoyooNull(unit, node), null, node, unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLiteralString,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLiteralString node, Object data) {
		String str = node.first_token.image;
		str = str.substring(1, str.length() - 1);
		str = str.replace("\\\\", "\\");
		str = str.replace("\\\"", "\"");
		str = str.replace("\\\r", "\r");
		str = str.replace("\\\n", "\n");
		YoyooString primary = new YoyooString(str);
		
		prefixAtom = new ValueAtom(new PrimitiveType.YoyooString(unit, node), primary, node,
				unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooNameExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooNameExpr node, Object data) {
		Token token1 = node.first_token;
		Token token2 = node.last_token;
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while (token1 != token2) {

			sb.append(token1.image);
			token1 = token1.next;
			i++;
		}

		sb.append(token2.image);
		String name = sb.toString();
		prefixAtom = new ReferenceAtom(stm, name, node, unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooSuperRefId,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooSuperRefId node, Object data) {
		prefixAtom = new ReferenceAtom(stm, RuntimeContext.SUPER
				+ "." + node.first_token.image, node, unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooThis,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooThis node, Object data) {
		prefixAtom = new ReferenceAtom(stm, RuntimeContext.THIS,
				node, unit);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooBracketExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooBracketExpr node, Object data) {
		this.prefixExpr = new BracketExpr(unit, node, stm);
		node.jjtAccept(this.prefixExpr, data);
//		node.childrenAccept(this.prefixExpr, node);
//		ExprVisitor v = new ExprVisitor(unit, node, stm);
//		node.childrenAccept(v, node);
//		this.prefixExpr = v.getExprList().get(0);
//		try {
//			prefixAtom = new BracketOperator(node, unit, ycls, v.getExprList()
//					.get(0).convert2Atom(node));
//		} catch (CompileException e) {
//			unit.addError(e);
//		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooArguments,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooArguments node, Object data) {
		ArgumentsExpr expr = new ArgumentsExpr(unit, node, stm);
		node.childrenAccept(expr, node);
//		try {
			if (postfixExprs == null) {
				postfixExprs = new ArrayList<Expr>();
			}
			postfixExprs.add(expr);
			
			
		Iterator<Stm> iterator = this.stm.getMethod().getStmCompileStack().iterator();
		while(iterator.hasNext()) {
			Stm trystm = iterator.next();
			if(trystm instanceof TryStatement) {
				if(wrappedTryStatements == null)
					wrappedTryStatements = new ArrayList<TryStatement>();
				wrappedTryStatements.add((TryStatement)trystm);
			}
		}
			
//		} catch (CompileException e) {
//			unit.addError(e);
//		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooArrayIndexExpr,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooArrayIndexExpr node, Object data) {
		ArrayIndexExpr expr = new ArrayIndexExpr(unit, node, stm);
		node.jjtAccept(expr, data);
		//node.childrenAccept(expr, node);
//		try {
			if (postfixExprs == null) {
				postfixExprs = new ArrayList<Expr>();
			}
			postfixExprs.add(expr);
//			IAtom atom = v.getExprList().get(0).convert2Atom(node);
//
//			postfixAtoms.add(new ArrayIndexOperator(node, unit, ycls, atom));
//		} catch (CompileException e) {
//			unit.addError(e);
//		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooRefId,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooRefId node, Object data) {
		if (postfixExprs == null) {
			postfixExprs = new ArrayList<Expr>();
		}
		postfixExprs.add(new ReferenceNameExpr(unit, node, stm, node.first_token.image));
		return null;
	}

	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		
		if(this.prefixAtom==null && this.prefixExpr!=null) {
			this.prefixAtom = this.prefixExpr.convert2Atom(node);
		}
		IAtom lastAtom = this.prefixAtom;
		
		
		if(postfixExprs!=null && postfixExprs.size()>0) {
			if (postfixAtoms == null) {
				postfixAtoms = new ArrayList<IAtom>();
			}
			for(Expr expr : postfixExprs) {
				postfixAtoms.add(expr.convert2Atom(expr.getNode()));
			}
		}
		
		OperatorList list = new OperatorList(node, unit, ycls);
		if (postfixAtoms != null && !postfixAtoms.isEmpty()) {
			for (IAtom atom : postfixAtoms) {
				if (atom instanceof FuncArgumentsOperator) {
					
					
					
					FuncCallOperator opt = null;
					if (lastAtom instanceof AllocationOperator) {
						opt = ((AllocationOperator) lastAtom);
						list.addOperator(opt);
						if(opt.getYoyooType().isArray()){
							IType type1 = opt.getYoyooType().typeClone(opt.getYoyooType().getNode());
							type1.setArray(false);
							type1.setArrayDim(0);
							unit.addError(new CompileException.TypeMismatch(type1, opt.getYoyooType(), node, unit));
						}

					} else {
						
						opt = new FuncCallOperator(node, unit,
								ycls, lastAtom);
						
						opt.installArguments(new IAtom[] { atom });

						list.addOperator(opt);
						lastAtom = opt;
					}
					
					if(wrappedTryStatements!=null) {
						List<IType> catchExceptionTypes = new ArrayList<IType>();
						Iterator<TryStatement> iterator = wrappedTryStatements.iterator();			
						while(iterator.hasNext()) {
							TryStatement tryStatement = iterator.next();
							Iterator<CatchStatement> catchStatementIterator = tryStatement.getCatchBlock().iterator();
							while(catchStatementIterator.hasNext()) {
								catchExceptionTypes.add(catchStatementIterator.next().getException().getType());
							}
							tryStatement.addFuncCallOperator(opt);
						}
						opt.setCatchExceptionTypes(catchExceptionTypes);
						
					}
					

					if(this.stm.getMethod()!=null && this.stm.getMethod().getThrowsList()!=null) {
						List<IType> methodThrownExceptionTypes = new ArrayList<IType>();
						Iterator<ReferenceNameType> referenceNameTypeIterator = this.stm.getMethod().getThrowsList().iterator();
						while(referenceNameTypeIterator.hasNext()) {
							methodThrownExceptionTypes.add(referenceNameTypeIterator.next());
						}
						opt.setMethodThrownExceptionTypes(methodThrownExceptionTypes);
					}
				}
				else if (atom instanceof ArrayIndexOperator)
				{
					((ArrayIndexOperator)atom).setArray(lastAtom);
					lastAtom = atom;
				}
				else {
					if(lastAtom instanceof DotOperator) {
						list.addOperator((DotOperator)lastAtom);
					}
					DotOperator opt = new DotOperator(node, unit, ycls);
					opt.installArguments(new IAtom[] { lastAtom, atom });
					lastAtom = opt;
					
				}
			}
		}

		if(lastAtom instanceof DotOperator) {
			list.addOperator((DotOperator)lastAtom);
		}
		
		if (prefixOptStack != null && !prefixOptStack.empty()) {
			Iterator<OperatorMark> markIterator = prefixOptStack.iterator();
			Operator operator = null;
			while (markIterator.hasNext()) {
				OperatorMark mark = markIterator.next();
				operator = OperatorFactory.createOperator(mark
						.getOpt().getStr(), new IAtom[] { lastAtom }, node,
						unit, ycls);
				
				lastAtom = operator;
			}
			if(list.size()>0)
				list.setOperator(list.size()-1, operator);
			else
				list.addOperator(operator);
		}
		if (list.isEmpty()) {
			return lastAtom;
		} else if (list.size() == 1) {
			return list.firstOperator();
		} else {
			return list;
		}
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Stack<OperatorMark> getPrefixOptStack() {
		return prefixOptStack;
	}

	public Stack<OperatorMark> getPostfixOptStack() {
		return postfixOptStack;
	}

	public IAtom getPrefixAtom() {
		return prefixAtom;
	}

	public List<IAtom> getPostfixAtoms() {
		return postfixAtoms;
	}
	

	public Expr getPrefixExpr() {
		return prefixExpr;
	}


	public List<Expr> getPostfixExprs() {
		return postfixExprs;
	}

}
