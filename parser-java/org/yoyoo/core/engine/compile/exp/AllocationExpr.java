package org.yoyoo.core.engine.compile.exp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.opt.AllocationOperator;
import org.yoyoo.core.engine.compile.opt.ArrayInitOperator;
import org.yoyoo.core.engine.compile.stm.CatchStatement;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.stm.TryStatement;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.PrimitiveType.TYPE;
import org.yoyoo.core.engine.compile.type.PrimitiveTypeVisitor;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAlloPrimitiveExpr;
import org.yoyoo.core.engine.parser.YoyooAlloReferenceExpr;
import org.yoyoo.core.engine.parser.YoyooAllocationName;
import org.yoyoo.core.engine.parser.YoyooArgument;
import org.yoyoo.core.engine.parser.YoyooArrayDims;
import org.yoyoo.core.engine.parser.YoyooArrayDimsAndInits;
import org.yoyoo.core.engine.parser.YoyooArrayDimsExpr;
import org.yoyoo.core.engine.parser.YoyooArrayInit;
import org.yoyoo.core.engine.parser.YoyooPrimitiveTypeName;


public class AllocationExpr extends AbstractExpr {



	public AllocationExpr(CompilationUnit unit, SimpleNode node, Stm stm) {
		super(unit, node, stm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object visit(YoyooAlloPrimitiveExpr node, Object data) {
		PrimitiveTypeAlloExprVisitor v = new PrimitiveTypeAlloExprVisitor(unit, node, this);
		node.childrenAccept(v, data);
		return null;
	}

	@Override
	public Object visit(YoyooAlloReferenceExpr node, Object data) {
		ReferenceTypeAlloExprVisitor v = new ReferenceTypeAlloExprVisitor(unit, node, this);
		node.childrenAccept(v, data);
		if(this.stm.getMethod()!=null) {
		Iterator<Stm> iterator = this.stm.getMethod().getStmCompileStack().iterator();
		while(iterator.hasNext()) {
			Stm trystm = iterator.next();
			if(trystm instanceof TryStatement) {
				if(wrappedTryStatements == null)
					wrappedTryStatements = new ArrayList<TryStatement>();
				wrappedTryStatements.add((TryStatement)trystm);
			}
		}
		}
		return null;
	}

	private IType type;
	
	private List<IAtom> arguments;
	
	private List<IAtom> arrayDims; 
	
	private List<Expr> argumentExprs;
	
	private List<Expr> arrayDimExprs;
	
	private boolean array;
	
	private int arrayDim;
	
	private ArrayInitExpr arrayInitExpr;
	
	private ArrayInitOperator arrayInitOpt;
	
	private transient List<TryStatement> wrappedTryStatements;

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.yoyoo.core.engine.compile.AbstractVisitor#visit(org.yoyoo.core.engine.parser.YoyooPrimitiveType,
//	 *      java.lang.Object)
//	 */
//	@Override
//	public Object visit(YoyooPrimitiveType node, Object data) {
//		PrimitiveType ptype = new PrimitiveType(unit, node);
//		node.jjtAccept(ptype, data);
//		type = ptype;
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.yoyoo.core.engine.compile.AbstractVisitor#visit(org.yoyoo.core.engine.parser.YoyooReferenceNameType,
//	 *      java.lang.Object)
//	 */
//	@Override
//	public Object visit(YoyooReferenceNameType node, Object data) {
//		ReferenceNameType rtype = new ReferenceNameType(unit, node);
//		node.jjtAccept(rtype, data);
//		type = rtype;
//		return null;
//	}


	/**
	 * @return the array
	 */
	public boolean isArray() {
		return array;
	}

	/**
	 * @return the type
	 */
	public IType getType() {
		return type;
	}
	

	public ArrayInitExpr getArrayInitExpr() {
		return arrayInitExpr;
	}

	public List<Expr> getArgumentExprs() {
		return argumentExprs;
	}

	public List<Expr> getArrayDimExprs() {
		return arrayDimExprs;
	}
	
	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		
		if (argumentExprs!=null && argumentExprs.size()>0) {
			if(arguments == null)
				arguments = new ArrayList<IAtom>();
			for(Expr expr : argumentExprs) {
				arguments.add(expr.convert2Atom(expr.getNode()));
			}
		}
		
		if (arrayDimExprs!=null && arrayDimExprs.size()>0) {
			if(arrayDims == null)
				arrayDims = new ArrayList<IAtom>();
			for(Expr expr : arrayDimExprs) {
				arrayDims.add(expr.convert2Atom(expr.getNode()));
			}
		}
		
		
		IAtom[] args = null;
		if(arguments!=null)
			args = arguments.toArray(new IAtom[0]);

		IAtom[] arrayDimOpt = null;
		if(arrayDims!=null)
			arrayDimOpt = arrayDims.toArray(new IAtom[0]);
		if(arrayInitExpr!=null)
			arrayInitOpt = (ArrayInitOperator)arrayInitExpr.convert2Atom(arrayInitExpr.getNode());
		
		AllocationOperator operator = null;
		if (!type.isPrimitiveType()) {
			final String name = ((ReferenceNameType) type).getName();

			YoyooClass cls = YoyooEnvironment.getDefault().getDeclaration(
					unit.getPackageName(), name);
			
			
			if (cls == null) {
				operator = new AllocationOperator(type, node, unit,
						ycls, args, arrayDimOpt, arrayInitOpt);
				
			} else {
				operator = new AllocationOperator(type, node, unit,
						ycls, args, arrayDimOpt, arrayInitOpt);
				
			}
			
		} else {
			operator = new AllocationOperator(type, node, unit, ycls, args,
					arrayDimOpt, arrayInitOpt);

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
				tryStatement.addFuncCallOperator(operator);
			}
			operator.setCatchExceptionTypes(catchExceptionTypes);
			
		}
		if(this.stm.getMethod()!=null && this.stm.getMethod().getThrowsList()!=null) {
			List<IType> methodThrownExceptionTypes = new ArrayList<IType>();
			Iterator<ReferenceNameType> referenceNameTypeIterator = this.stm.getMethod().getThrowsList().iterator();
			while(referenceNameTypeIterator.hasNext()) {
				methodThrownExceptionTypes.add(referenceNameTypeIterator.next());
			}
			operator.setMethodThrownExceptionTypes(methodThrownExceptionTypes);
		}
		return operator;
	}

	

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static class PrimitiveTypeAlloExprVisitor extends AbstractVisitor
	{
		private AllocationExpr expr;
		public PrimitiveTypeAlloExprVisitor(CompilationUnit unit, SimpleNode node, AllocationExpr expr) {
			super(unit, node);
			this.expr = expr;
		}

		@Override
		public Object visit(YoyooPrimitiveTypeName node, Object data) {
			PrimitiveTypeVisitor v = new PrimitiveTypeVisitor(unit, node);
			node.jjtAccept(v, data);
			TYPE typeVal = v.getType();
			PrimitiveType ptype = new PrimitiveType(unit, node);
			ptype.setType(typeVal);
			expr.type = ptype;
			return null;
		}

		@Override
		public Object visit(YoyooArrayDimsAndInits node, Object data) {
			ArrayDimsAndInitsVisitor v = new ArrayDimsAndInitsVisitor(unit, node, expr);
			node.childrenAccept(v, data);
			return null;
		}


			
	}
	
	public static class ReferenceTypeAlloExprVisitor extends AbstractVisitor
	{
		private AllocationExpr expr;
		
		public ReferenceTypeAlloExprVisitor(CompilationUnit unit, SimpleNode node, AllocationExpr expr) {
			super(unit, node);
			this.expr = expr;
		}
		public Object visit(YoyooAllocationName node, Object data) {
			TypeVisitor v = new TypeVisitor(unit, node);
			v.visit(node, data);
			expr.type = (ReferenceNameType)v.getType();
			return null;
		}
		/*
		public Object visit(YoyooReferenceTypeName node, Object data) {
			ReferenceNameType rtype = new ReferenceNameType(unit, node);
			Token token1 = node.first_token;
			Token token2 = node.last_token;
			if (token1 != token2) {
				StringBuffer sb = new StringBuffer();
				while (token1 != token2) {
					sb.append(".");
					sb.append(token1.image);
					token1 = token1.next;
				}
				rtype.setPkgname(sb.toString());
				rtype.setName(token2.image);
				
			} else {
				rtype.setName(token2.image);
				
			}
			expr.type = rtype;
			unit.addRefernece(rtype);
			return null;
		}
		*/
		@Override
		public Object visit(YoyooArrayDimsAndInits node, Object data) {
			ArrayDimsAndInitsVisitor v = new ArrayDimsAndInitsVisitor(unit, node, expr);
			node.childrenAccept(v, data);
			return null;
		}
		
		@Override
		public Object visit(YoyooArgument node, Object data) {

			ExprVisitor v = new ExprVisitor(unit, node, expr.stm);

			node.childrenAccept(v, node);
			
			if (expr.argumentExprs == null) {
				expr.argumentExprs = new ArrayList<Expr>();
			}
			expr.argumentExprs.add(v.getExprList().get(0));
			
			return null;

		}

			
	}
	
	public static class ArrayDimsAndInitsVisitor extends AbstractVisitor
	{
		private AllocationExpr expr;
		
		public ArrayDimsAndInitsVisitor(CompilationUnit unit, SimpleNode node, AllocationExpr expr) {
			super(unit, node);
			this.expr = expr;
		}
		
		@Override
		public Object visit(YoyooArrayDims node, Object data) {
			expr.array = true;
			expr.arrayDim++;
			expr.type.setArray(expr.array);
			expr.type.setArrayDim(expr.arrayDim);
			return super.visit(node, data);
		}

		@Override
		public Object visit(YoyooArrayDimsExpr node, Object data) {
			expr.array = true;
			expr.arrayDim++;
			expr.type.setArray(expr.array);
			expr.type.setArrayDim(expr.arrayDim);
			ExprVisitor v = new ExprVisitor(unit, node, expr.stm);

			node.childrenAccept(v, node);
			
			if (expr.arrayDimExprs == null) {
				expr.arrayDimExprs = new ArrayList<Expr>();
			}
			expr.arrayDimExprs.add(v.getExprList().get(0));
			
			return null;
		}

		@Override
		public Object visit(YoyooArrayInit node, Object data) {
			if (expr.arrayInitExpr == null) {
				expr.arrayInitExpr = new ArrayInitExpr(unit, node, expr.stm);				
			}
			node.childrenAccept(expr.arrayInitExpr , node);
			return null;
		}

		
		
	}
	
	


}
