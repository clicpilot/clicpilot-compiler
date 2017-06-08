package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.parser.SimpleNode;

public interface Stm {

	public YoyooBaseMethod getMethod();
	
	public CatchStatement getCatchStm();

	public YoyooTypeDefineClass getYcls();
	
	public SymbolTable<YoyooVariable> getVariableTable();

	public Operator getOperator() throws CompileException;

	public SimpleNode getNode();
	
	public boolean isTerminatedByReturnOrThrowStm();
	
	public void typeCheck() throws CompileException;

	

}
