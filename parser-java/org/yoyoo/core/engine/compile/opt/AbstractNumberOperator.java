package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;

public abstract class AbstractNumberOperator extends AbstractOperator {

	protected AbstractNumberOperator(SimpleNode node, CompilationUnit unit,
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

	protected IType getReturnClassName4Short(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isChar(right)) {
			return new PrimitiveType.YoyooInteger(unit, node);
		} else if (PrimitiveType.isLong(right)) {
			return new PrimitiveType.YoyooLong(unit, node);
		} else if (PrimitiveType.isFloat(right)) {
			return new PrimitiveType.YoyooFloat(unit, node);
		} else if (PrimitiveType.isDouble(right)) {
			return new PrimitiveType.YoyooDouble(unit, node);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}
	}

	protected IType getReturnClassName4Integer(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isChar(right)) {
			return new PrimitiveType.YoyooInteger(unit, node);
		} else if (PrimitiveType.isLong(right)) {
			return new PrimitiveType.YoyooLong(unit, node);
		} else if (PrimitiveType.isFloat(right)) {
			return new PrimitiveType.YoyooFloat(unit, node);
		} else if (PrimitiveType.isDouble(right)) {
			return new PrimitiveType.YoyooDouble(unit, node);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}
	}

	protected IType getReturnClassName4Long(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isLong(right)
				|| PrimitiveType.isChar(right)) {
			return new PrimitiveType.YoyooLong(unit, node);
		} else if (PrimitiveType.isFloat(right)
				|| PrimitiveType.isDouble(right)) {
			return new PrimitiveType.YoyooDouble(unit, node);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}
	}

	protected IType getReturnClassName4Float(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isFloat(right)
				|| PrimitiveType.isChar(right)) {
			return new PrimitiveType.YoyooFloat(unit, node);
		} else if (PrimitiveType.isLong(right)
				|| PrimitiveType.isDouble(right)) {
			return new PrimitiveType.YoyooDouble(unit, node);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}
	}

	protected IType getReturnClassName4Double(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isLong(right)
				|| PrimitiveType.isFloat(right)
				|| PrimitiveType.isDouble(right)
				|| PrimitiveType.isChar(right)) {
			return new PrimitiveType.YoyooDouble(unit, node);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}
	}

	protected IType getReturnClassName4Char(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isChar(right)) {
			return new PrimitiveType.YoyooInteger(unit, node);
		} else if (PrimitiveType.isLong(right)) {
			return new PrimitiveType.YoyooLong(unit, node);
		} else if (PrimitiveType.isFloat(right)) {
			return new PrimitiveType.YoyooFloat(unit, node);
		} else if (PrimitiveType.isDouble(right)) {
			return new PrimitiveType.YoyooDouble(unit, node);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}
	}

	public IType operatorTypeCheck() throws CompileException {

		IType leftType = left.getYoyooType();
		IType rightType = right.getYoyooType();
		if (PrimitiveType.isInteger(leftType)) {
			return getReturnClassName4Integer(rightType);
		} else if (PrimitiveType.isShort(leftType)) {
			return getReturnClassName4Short(rightType);
		} else if (PrimitiveType.isLong(leftType)) {
			return getReturnClassName4Long(rightType);
		} else if (PrimitiveType.isFloat(leftType)) {
			return getReturnClassName4Float(rightType);
		} else if (PrimitiveType.isDouble(leftType)) {
			return getReturnClassName4Double(rightType);
		} else if (PrimitiveType.isChar(leftType)) {
			return getReturnClassName4Char(rightType);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}

	}

}
