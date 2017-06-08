package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.SpecialExprType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooSpecialExpression;



public class SpecialExprOperator extends AbstractOperator implements IOverrideAssignOpt{
	protected SpecialExprType exprType;
	protected YoyooSpecialExpression yexpr;
	//protected ISpecialExpr myexpr;
	public SpecialExprOperator(YoyooSpecialExpression yexpr, SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		this.yexpr = yexpr;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

	}

	

	@Override
	protected IRuntimeValueAtom eval(RuntimeContext ctx) throws YoyooRTException {
		return new RuntimeValueAtom(yexpr, node, unit);
	}

	public IType operatorTypeCheck() throws CompileException {
		yexpr.compile(node, unit);
		return exprType;
	}

	public SpecialExprType getType() {
		return exprType;
	}

	

	public void convert(IType type) throws CompileException {
		this.exprType = (SpecialExprType)type;
		this.yexpr.setExprType(exprType);
	}

}
