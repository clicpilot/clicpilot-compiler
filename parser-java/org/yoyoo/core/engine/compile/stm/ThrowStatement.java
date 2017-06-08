package org.yoyoo.core.engine.compile.stm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.ThrowOperator;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooThrowExpr;

public class ThrowStatement extends AbstractStm  {
	
	private Expr throwExpr;
	
	private IAtom throwAtom;
	
	private transient ThrowOperator opt;
	
	private transient List<TryStatement> wrappedTryStatements;

	public ThrowStatement(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		this.terminatedByReturnOrThrowStm = true;
	}
	
	
	
	

	@Override
	protected Operator convert2Operator() throws CompileException {
		
		throwAtom = throwExpr.convert2Atom(throwExpr.getNode());
		List<IType> catchExceptionTypes = new ArrayList<IType>();
		List<IType> methodThrownExceptionTypes = new ArrayList<IType>();
		opt = new ThrowOperator(node, unit, ycls, throwAtom, catchExceptionTypes, methodThrownExceptionTypes);
		

		if(wrappedTryStatements!=null) {
			Iterator<TryStatement> iterator = wrappedTryStatements.iterator();			
			while(iterator.hasNext()) {
				TryStatement tryStatement = iterator.next();
				Iterator<CatchStatement> catchStatementIterator = tryStatement.getCatchBlock().iterator();
				while(catchStatementIterator.hasNext()) {
					IType caughtType = catchStatementIterator.next().getException().getType();
					catchExceptionTypes.add(caughtType);
				}
				tryStatement.addThrowOperator(opt);
			}
		}
		

		if(this.method.getThrowsList()!=null) {
			Iterator<ReferenceNameType> referenceNameTypeIterator = this.method.getThrowsList().iterator();
			while(referenceNameTypeIterator.hasNext()) {
				ReferenceNameType throwType = referenceNameTypeIterator.next();
				methodThrownExceptionTypes.add(throwType);
			}
		}

				
		return opt;

	}
	
	@Override
	public Object visit(YoyooThrowExpr node, Object data) {
		Iterator<Stm> iterator = this.method.getStmCompileStack().iterator();
		while(iterator.hasNext()) {
			Stm stm = iterator.next();
			if(stm instanceof TryStatement) {
				if(wrappedTryStatements == null)
					wrappedTryStatements = new ArrayList<TryStatement>();
				wrappedTryStatements.add((TryStatement)stm);
			}
		}
		
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		throwExpr = v.getExprList().get(0);	

		return null;
	}
	
	
	public Expr getThrowExpr() {
		return throwExpr;
	}


}
