package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;


public class RefOperator extends AbstractOperator implements IOverrideAssignOpt{
	protected IType valueType;

	protected RefOperator(IType valueType, SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		this.valueType = valueType;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

	}

	

	public IType operatorTypeCheck() throws CompileException {
		return null;
	}

	public IType getValueType() {
		return valueType;
	}

	

	public void convert(IType valueType) throws CompileException{
		
		this.valueType = valueType;
	}

}
