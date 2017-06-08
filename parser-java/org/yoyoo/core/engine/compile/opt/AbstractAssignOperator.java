package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;

public abstract class AbstractAssignOperator extends AbstractOperator {

	protected AbstractAssignOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IAtom left;

	protected IAtom right;

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 2);
		this.left = arguments[0];
		this.right = arguments[1];

	}

	public IType operatorTypeCheck() throws CompileException {

		IType lefttype = left.getYoyooType();
		if (!(PrimitiveType.isInteger(lefttype)
				|| PrimitiveType.isChar(lefttype)
				|| PrimitiveType.isDouble(lefttype)
				|| PrimitiveType.isFloat(lefttype)
				|| PrimitiveType.isLong(lefttype)
				|| PrimitiveType.isShort(lefttype)
				|| PrimitiveType.isString(lefttype))) {
			throw new CompileException.InvalidExpr(node, unit);
		}
		IType righttype = right.getYoyooType();
		if (!(PrimitiveType.isInteger(righttype)
				|| PrimitiveType.isChar(righttype)
				|| PrimitiveType.isDouble(righttype)
				|| PrimitiveType.isFloat(righttype)
				|| PrimitiveType.isLong(righttype)
				|| PrimitiveType.isShort(righttype)
				|| PrimitiveType.isString(lefttype))) {
			throw new CompileException.InvalidExpr(node, unit);
		}

		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
