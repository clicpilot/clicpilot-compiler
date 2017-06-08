package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.INumber;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class PlusAssignOperator extends AbstractAssignOperator {
	protected PlusAssignOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		IRuntimeValueAtom atom = getValueAtom(left, ctx);

		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);
		if (leftObj instanceof INumber) {
			if (leftObj instanceof YoyooChar)
				atom.setValue(add((YoyooChar) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooShort)
				atom.setValue(add((YoyooShort) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooInteger)
				atom.setValue(add((YoyooInteger) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooLong)
				atom.setValue(add((YoyooLong) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooFloat)
				atom.setValue(add((YoyooFloat) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooDouble)
				atom.setValue(add((YoyooDouble) leftObj, rightObj, ctx));

			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (leftObj instanceof YoyooString) {
			atom.setValue(add((YoyooString) leftObj, rightObj, ctx));

			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooObject add(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooDouble(left.getVal().add(
					((YoyooInteger) right).getVal()).doubleValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooDouble(left.getVal().add(
					((YoyooShort) right).getVal()).doubleValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().add(
					((YoyooLong) right).getVal()).doubleValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().add(
					((YoyooFloat) right).getVal()).doubleValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().add(
					((YoyooDouble) right).getVal()).doubleValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooDouble(left.getVal().add(
					((YoyooChar) right).getVal()).doubleValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooLong(left.getVal().add(
					((YoyooInteger) right).getVal()).longValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooLong(left.getVal().add(
					((YoyooShort) right).getVal()).longValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal()
					.add(((YoyooLong) right).getVal()).longValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooLong(left.getVal().add(
					((YoyooFloat) right).getVal()).longValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooLong(left.getVal().add(
					((YoyooDouble) right).getVal()).longValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooLong(left.getVal()
					.add(((YoyooChar) right).getVal()).longValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().add(
					((YoyooInteger) right).getVal()).intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().add(
					((YoyooShort) right).getVal()).intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooInteger(left.getVal().add(
					((YoyooLong) right).getVal()).intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooInteger(left.getVal().add(
					((YoyooFloat) right).getVal()).intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooInteger(left.getVal().add(
					((YoyooDouble) right).getVal()).intValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().add(
					((YoyooChar) right).getVal()).intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooShort(left.getVal().add(
					((YoyooInteger) right).getVal()).shortValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooShort(left.getVal().add(
					((YoyooShort) right).getVal()).shortValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooShort(left.getVal().add(
					((YoyooLong) right).getVal()).shortValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooShort(left.getVal().add(
					((YoyooFloat) right).getVal()).shortValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooShort(left.getVal().add(
					((YoyooDouble) right).getVal()).shortValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooShort(left.getVal().add(
					((YoyooChar) right).getVal()).shortValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooFloat(left.getVal().add(
					((YoyooInteger) right).getVal()).floatValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooFloat(left.getVal().add(
					((YoyooShort) right).getVal()).floatValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooFloat(left.getVal().add(
					((YoyooLong) right).getVal()).floatValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().add(
					((YoyooFloat) right).getVal()).floatValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooFloat(left.getVal().add(
					((YoyooDouble) right).getVal()).floatValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooFloat(left.getVal().add(
					((YoyooChar) right).getVal()).floatValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooChar left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooChar((char) left.getVal().add(
					((YoyooInteger) right).getVal()).intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooChar((char) left.getVal().add(
					((YoyooShort) right).getVal()).intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooChar((char) left.getVal().add(
					((YoyooLong) right).getVal()).intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooChar((char) left.getVal().add(
					((YoyooFloat) right).getVal()).intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooChar((char) left.getVal().add(
					((YoyooDouble) right).getVal()).intValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooChar((char) left.getVal().add(
					((YoyooChar) right).getVal()).intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject add(YoyooString left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooChar) {
			return new YoyooString(left.getVal()
					+ (char) (((YoyooChar) right).getVal().intValue()));
		} else if (right instanceof INumber) {
			return new YoyooString(left.getVal() + ((INumber) right).getVal());
		} else if (right instanceof YoyooString) {
			return new YoyooString(left.getVal()
					+ ((YoyooString) right).getVal());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}
}
