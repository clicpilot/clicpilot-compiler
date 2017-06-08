package org.yoyoo.core.engine.compile.stm;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooBlockStatementBodyStm;
import org.yoyoo.core.engine.parser.YoyooLocalVariableDeclaration;

public class Block extends AbstractStm implements ILocalVariableStm {



	public Block(YoyooBaseMethod method, CatchStatement catchStm,
			SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private List<Stm> statements;

	/**
	 * @return the statements
	 */
	public List<Stm> getStatements() {
		return statements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLocalVariableDeclaration,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLocalVariableDeclaration node, Object data) {
		YoyooVariable decl = new YoyooVariable(ycls, unit, node);
		node.childrenAccept(decl, node);
		if (statements == null)
			statements = new ArrayList<Stm>();
		statements.add(decl);
		registerNewLocalVariable(decl, node);
		// addLocalVariableDeclaration(decl, node);
		return decl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooBlockStatementBodyStm,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooBlockStatementBodyStm node, Object data) {
		StmVisitor v = new StmVisitor(method, catchStm, node);
		node.childrenAccept(v, node);
		if (statements == null)
			statements = new ArrayList<Stm>();
		statements.add(v.getStatement());
		return v.getStatement();
	}

	
	protected Operator convert2Operator() throws CompileException {
		OperatorList opt = new OperatorList(node, unit, ycls);
		pushOperator(opt);
		boolean stopped = false;
		if(statements!=null) {
			for (Stm stm : statements)
			{
				opt.addOperator(stm.getOperator());
				if(terminatedByReturnOrThrowStm)
				{
					throw new CompileException.UnreachableCode(stm.getNode(), unit);
				}
				else if(stm.isTerminatedByReturnOrThrowStm())
				{
					terminatedByReturnOrThrowStm = true;
				}
				
				if(stopped)
				{
					throw new CompileException.UnreachableCode(stm.getNode(), unit);
				}
				else if(stm instanceof StopStm)
				{
					stopped = true;
				}
				
			}
		}
		return popOperator();

	}

	public void closeLocalVariableStack() {
		this.unit.popLocalVariableStack();

	}

	public void openLocalVariableStack() {
		this.unit.pushNewLocalVariableStack();
		this.variableTable = this.unit.getCurrentLocalVariableTable();
	}

	public void registerNewLocalVariable(YoyooVariable decl, SimpleNode node) {
		try {
			this.unit.registerNewLocalVariable(decl, node);
		} catch (CompileException e) {
			this.unit.addError(e);
		}

	}


}
