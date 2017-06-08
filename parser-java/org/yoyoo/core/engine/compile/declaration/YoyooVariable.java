package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.VariableAssignOperator;
import org.yoyoo.core.engine.compile.stm.CatchStatement;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooArrayDecl;
import org.yoyoo.core.engine.parser.YoyooVariableDeclaratorId;
import org.yoyoo.core.engine.parser.YoyooVariableInitializer;

public class YoyooVariable extends AbstractDeclaration implements Stm {

	protected Expr assignExpr;

	protected IAtom assignAtom;

	protected CompilationUnit compilationUnit;

	private Operator operator;

	protected YoyooBaseMethod method;
	
	private CatchStatement catchStm;
	
	private SymbolTable<YoyooVariable> variableTable;

	public YoyooVariable(YoyooTypeDefineClass unit, CompilationUnit unit2,
			SimpleNode node) {
		super(unit, node);
		this.compilationUnit = unit2;
		this.method = compilationUnit.getMethodInCompiling();
		this.variableTable = compilationUnit.getCurrentLocalVariableTable();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooVariableInitializer,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooVariableInitializer node, Object data) {
		ExprVisitor v = new ExprVisitor(unit.getCompilationUnit(), node, this);
		node.childrenAccept(v, node);
		assignExpr = v.getExprList().get(0);
//		try {
//			
//			assignAtom = v.getExprList().get(0).convert2Atom(node);
//		} catch (CompileException e) {
//			unit.getCompilationUnit().addError(e);
//		}
		return null;
	}


	public YoyooVariable(YoyooTypeDefineClass unit, SimpleNode node) {
		super(unit, node);
		compilationUnit = unit.getCompilationUnit();
		this.method = compilationUnit.getMethodInCompiling();
		this.variableTable = compilationUnit.getCurrentLocalVariableTable();
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooVariableDeclaratorId,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooVariableDeclaratorId node, Object data) {
		this.name = (String) super.visit(node, data);
		return null;
	}
	


	/**
	 * @return the type
	 */
	public IType getType() {
		return type;
	}

	public Operator convert2Operator(SimpleNode node) throws CompileException {
		if(this.assignExpr!=null)
			assignAtom = this.assignExpr.convert2Atom(assignExpr.getNode());
		return new VariableAssignOperator(node, unit.getCompilationUnit(),
				unit, type, assignAtom, name);
	}

	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	public Operator getOperator() throws CompileException {
		if (operator == null) {
			operator = this.convert2Operator(this.getNode());
		}
		return operator;
	}

	public YoyooBaseMethod getMethod() {
		return method;
	}

	public YoyooTypeDefineClass getYcls() {
		return super.getUnit();
	}

	public SymbolTable<YoyooVariable> getVariableTable() {
		return variableTable;
	}

	public boolean isTerminatedByReturnOrThrowStm() {
		return false;
	}
	
	public Expr getAssignExpr() {
		return assignExpr;
	}

	@Override
	public CatchStatement getCatchStm() {
		return catchStm;
	}

	public IType getAssignedType() throws CompileException{
		if(this.assignAtom!=null) {
			return this.assignAtom.getYoyooType();
		}
		return null;
	}

	@Override
	public void typeCheck() throws CompileException {
		
	}
	
	

}
