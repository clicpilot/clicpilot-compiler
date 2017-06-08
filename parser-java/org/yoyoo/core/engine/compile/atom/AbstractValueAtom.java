package org.yoyoo.core.engine.compile.atom;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;

public abstract class AbstractValueAtom implements IValueAtom {

	protected SimpleNode node;

	protected CompilationUnit unit;

	protected IType type;


	
	public AbstractValueAtom(IType type, SimpleNode node, CompilationUnit unit) {
		super();
		this.type = type;
		this.node = node;
		this.unit = unit;
		
	}

	public IType getYoyooType() throws CompileException {
		return type;
	}

	public SimpleNode getNode() {
		return node;
	}

	public CompilationUnit getCompilationUnit() {
		return unit;
	}
	
	@Override
	public IType getAssignedType() throws CompileException {
		return type;
	}


}
