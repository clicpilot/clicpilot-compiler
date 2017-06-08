package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class AddOperator extends AbstractNumberOperator {

	protected AddOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);

		if (leftObj instanceof YoyooInteger) {
			return new RuntimeValueAtom(add((YoyooInteger) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooShort) {
			return new RuntimeValueAtom(add((YoyooShort) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooLong) {
			return new RuntimeValueAtom(add((YoyooLong) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooFloat) {
			return new RuntimeValueAtom(add((YoyooFloat) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooDouble) {
			return new RuntimeValueAtom(add((YoyooDouble) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooChar) {
			return new RuntimeValueAtom(add((YoyooChar) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooString) {
			return new RuntimeValueAtom(add((YoyooString) leftObj, rightObj, ctx),
					node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooObject add(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().add(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().add(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal()
					.add(((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().add(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().add(
					((YoyooChar) right).getVal()));
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().add(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().add(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal()
					.add(((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().add(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().add(
					((YoyooChar) right).getVal()));
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooLong(left.getVal().add(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooLong(left.getVal().add(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal()
					.add(((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().add(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooLong(left.getVal()
					.add(((YoyooChar) right).getVal()));
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooFloat(left.getVal().add(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooFloat(left.getVal().add(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().add(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().add(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooFloat(left.getVal().add(
					((YoyooChar) right).getVal()));
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooDouble(left.getVal().add(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooDouble(left.getVal().add(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().add(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().add(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooDouble(left.getVal().add(
					((YoyooChar) right).getVal()));
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooChar left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().add(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().add(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal()
					.add(((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().add(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().add(
					((YoyooChar) right).getVal()));
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooString left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooString(left.getVal()
					+ ((YoyooInteger) right).getVal());
		} else if (right instanceof YoyooShort) {
			return new YoyooString(left.getVal()
					+ ((YoyooShort) right).getVal());
		} else if (right instanceof YoyooLong) {
			return new YoyooString(left.getVal() + ((YoyooLong) right).getVal());
		} else if (right instanceof YoyooFloat) {
			return new YoyooString(left.getVal()
					+ ((YoyooFloat) right).getVal());
		} else if (right instanceof YoyooDouble) {
			return new YoyooString(left.getVal()
					+ ((YoyooDouble) right).getVal());
		} else if (right instanceof YoyooChar) {
			return new YoyooString(left.getVal() + ((YoyooChar) right).getVal());
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else if (right instanceof YoyooBoolean) {
			return new YoyooString(left.getVal()
					+ ((YoyooBoolean) right).getVal());
		} else if (right instanceof YoyooNull) {
			return new YoyooString(left.getVal()
					+ "null");
		} else {
			return new YoyooString(left.getVal()
					+ right);
		}
	}

	protected IType getReturnClassName4Short(IType right)
			throws CompileException {
		try {
			return super.getReturnClassName4Short(right);
		} catch (CompileException e) {

			if (PrimitiveType.isString(right)) {
				return new PrimitiveType.YoyooString(unit, node);
			} else {
				throw new CompileException.InvalidExpr(node, unit);
			}
		}

	}

	protected IType getReturnClassName4Integer(IType right)
			throws CompileException {

		try {
			return super.getReturnClassName4Integer(right);
		} catch (CompileException e) {

			if (PrimitiveType.isString(right)) {
				return new PrimitiveType.YoyooString(unit, node);
			} else {
				throw new CompileException.InvalidExpr(node, unit);
			}
		}

	}

	protected IType getReturnClassName4Long(IType right)
			throws CompileException {
		try {
			return super.getReturnClassName4Long(right);
		} catch (CompileException e) {

			if (PrimitiveType.isString(right)) {
				return new PrimitiveType.YoyooString(unit, node);
			} else {
				throw new CompileException.InvalidExpr(node, unit);
			}
		}

	}

	protected IType getReturnClassName4Float(IType right)
			throws CompileException {

		try {
			return super.getReturnClassName4Float(right);
		} catch (CompileException e) {

			if (PrimitiveType.isString(right)) {
				return new PrimitiveType.YoyooString(unit, node);
			} else {
				throw new CompileException.InvalidExpr(node, unit);
			}
		}

	}

	protected IType getReturnClassName4Double(IType right)
			throws CompileException {
		try {
			return super.getReturnClassName4Double(right);
		} catch (CompileException e) {

			if (PrimitiveType.isString(right)) {
				return new PrimitiveType.YoyooString(unit, node);
			} else {
				throw new CompileException.InvalidExpr(node, unit);
			}
		}

	}

	protected IType getReturnClassName4Char(IType right)
			throws CompileException {
		try {
			return super.getReturnClassName4Char(right);
		} catch (CompileException e) {

			if (PrimitiveType.isString(right)) {
				return new PrimitiveType.YoyooString(unit, node);
			} else {
				throw new CompileException.InvalidExpr(node, unit);
			}
		}

	}

	protected IType getReturnClassName4String(IType right)
			throws CompileException {

		if (PrimitiveType.isInteger(right)
				|| PrimitiveType.isShort(right)
				|| PrimitiveType.isLong(right)
				|| PrimitiveType.isFloat(right)
				|| PrimitiveType.isDouble(right)
				|| PrimitiveType.isChar(right)
				|| PrimitiveType.isString(right)
				|| PrimitiveType.isBoolean(right)) {
			return new PrimitiveType.YoyooString(unit, node);
		} else {
			return new PrimitiveType.YoyooString(unit, node);
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
		} else if (PrimitiveType.isString(leftType)) {
			return getReturnClassName4String(rightType);
		} else {
			throw new CompileException.InvalidExpr(node, unit);
		}

	}

}
