package org.yoyoo.core.engine.compile.stm;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.opt.CatchOperator;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.opt.ThrowOperator;
import org.yoyoo.core.engine.compile.opt.TryOperator;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooCatchBlock;
import org.yoyoo.core.engine.parser.YoyooFinallyBlock;
import org.yoyoo.core.engine.parser.YoyooFormalParameter;
import org.yoyoo.core.engine.parser.YoyooFormalParameterName;
import org.yoyoo.core.engine.parser.YoyooFormalParameterType;
import org.yoyoo.core.engine.parser.YoyooTryBlock;

public class TryStatement extends AbstractStm {
	
	private List<CatchStatement> catchBlock;

	private Stm tryBlock;

	private Stm finallyBlock;

	private FormalParameter currentException;
	
	
	private transient List<FuncCallOperator> funcOpts;
	
	private transient List<ThrowOperator> throwOpts;
	
	public void addFuncCallOperator(FuncCallOperator funcOpt) {
		
			if(funcOpts==null) {
				funcOpts = new ArrayList<FuncCallOperator>();
			}
			this.funcOpts.add(funcOpt);
		
	}
	
	public void addThrowOperator(ThrowOperator throwOpt) {
		
		if(throwOpts==null) {
			throwOpts = new ArrayList<ThrowOperator>();
		}
		this.throwOpts.add(throwOpt);
	
}
	
//	public void addExceptionType(ReferenceNameType type) {
//		if(type!=null) {
//			if(exceptionTypes==null) {
//				exceptionTypes = new ArrayList<ReferenceNameType>();
//			}
//			this.exceptionTypes.add(type);
//		}
//	}
//	
//	public void addExceptionType(List<ReferenceNameType> type) {
//		if(type!=null) {
//			if(exceptionTypes==null) {
//				exceptionTypes = new ArrayList<ReferenceNameType>();
//			}
//			this.exceptionTypes.addAll(type);
//		}
//	}
//	
//	public void addThrowAtom(IAtom atom) {
//		if(atom!=null) {
//			if(throwAtoms==null) {
//				throwAtoms = new ArrayList<IAtom>();
//			}
//			this.throwAtoms.add(atom);
//		}
//	}
//	
//	public void addThrowAtom(List<IAtom> atom) {
//		if(atom!=null) {
//			if(throwAtoms==null) {
//				throwAtoms = new ArrayList<IAtom>();
//			}
//			this.throwAtoms.addAll(atom);
//		}
//	}

	public TryStatement(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		catchBlock = new ArrayList<CatchStatement>(1);
	}

	@Override
	protected Operator convert2Operator() throws CompileException {
		Operator finalOpt = null;
		if(finallyBlock!=null)
			finalOpt = finallyBlock.getOperator();
		int catchBlockSize = catchBlock.size();
		List<CatchOperator> catchOperatorList = new ArrayList<CatchOperator>();
		for(int i=0;i<catchBlockSize;i++) {			
			catchOperatorList.add((CatchOperator)catchBlock.get(i).convert2Operator());
		}
		
		

		TryOperator tryOpt = new TryOperator(node, unit, ycls, (OperatorList)tryBlock.getOperator(), catchOperatorList, (OperatorList)finalOpt, throwOpts, funcOpts);
		
		return tryOpt;
	}

	@Override
	public Object visit(YoyooCatchBlock node, Object data) {
		CatchStatement catchStatement = new CatchStatement(method, catchStm, node);
		StmVisitor v = new StmVisitor(method, catchStatement, node);
		node.childrenAccept(v, node);
		catchStatement.setException(currentException);
		catchStatement.setBody(v.getStatement());
		catchBlock.add(catchStatement);
		return null;
	}

	@Override
	public Object visit(YoyooTryBlock node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		tryBlock = v.getStatement();
		return null;
	}

	@Override
	public Object visit(YoyooFinallyBlock node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		finallyBlock = v.getStatement();
		return null;
	}

	@Override
	public Object visit(YoyooFormalParameter node, Object data) {		
		return node.childrenAccept(this, node);
	}

	@Override
	public Object visit(YoyooFormalParameterName node, Object data) {
		currentException.setName(node.first_token.image);
		
		return null;
	}

	@Override
	public Object visit(YoyooFormalParameterType node, Object data) {
		TypeVisitor typeVisitor = new TypeVisitor(unit, node);
		node.childrenAccept(typeVisitor, data);
		currentException = new FormalParameter();
		currentException.setType(typeVisitor.getType());

		return null;
	}
	
	public List<CatchStatement> getCatchBlock() {
		return catchBlock;
	}

	public Stm getTryBlock() {
		return tryBlock;
	}

	public Stm getFinallyBlock() {
		return finallyBlock;
	}




}
