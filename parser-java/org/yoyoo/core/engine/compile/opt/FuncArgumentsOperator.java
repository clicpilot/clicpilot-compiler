package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class FuncArgumentsOperator extends AbstractOperator {

	public FuncArgumentsOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	private IAtom[] arguments;


	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOperatorString() {
		String str = new String("Arguments");
		return str;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		this.arguments = arguments;
	}

	public IAtom[] getArguments() {
		return arguments;
	}

	public IType operatorTypeCheck() throws CompileException {
		// TODO Auto-generated method stub
		return new PrimitiveType.YoyooVoid(unit, node);
	}
	

}
