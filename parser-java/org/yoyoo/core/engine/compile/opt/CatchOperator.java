package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class CatchOperator extends OperatorList {

	private FormalParameter exception;
	
	//private Operator bodyOpt;

	public CatchOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, FormalParameter exception, OperatorList bodyOpt) {
		super(node, unit, ycls);
		this.exception = exception;
		this.operators = bodyOpt.operators;
	}


	@Override
	public IType operatorTypeCheck() throws CompileException {
		super.operatorTypeCheck();		
		return new PrimitiveType.YoyooVoid(unit, node);
	}


	@Override
	public void installArguments(IAtom[] arguments) throws CompileException {

		
	}


	@Override
	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		//System.err.println("exception catch:" + exception.getName());
		ctx.registerVariable(exception.getName(), ctx.getCurrentException(), node, unit);
		ctx.cleanException();
		if(this.operators!=null) {
		for(Operator opt : this.operators)
			ctx.evaluateOperator(opt);
		}
		return null;
	}
	
	public FormalParameter getException() {
		return exception;
	}





}
