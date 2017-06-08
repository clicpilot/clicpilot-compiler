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


public class RemAssignOperator extends AbstractAssignOperator {

	protected RemAssignOperator(SimpleNode node, CompilationUnit unit,
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
				atom.setValue(remainder((YoyooChar) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooShort)
				atom.setValue(remainder((YoyooShort) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooInteger)
				atom.setValue(remainder((YoyooInteger) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooLong)
				atom.setValue(remainder((YoyooLong) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooFloat)
				atom.setValue(remainder((YoyooFloat) leftObj, rightObj, ctx));
			else if (leftObj instanceof YoyooDouble)
				atom.setValue(remainder((YoyooDouble) leftObj, rightObj, ctx));

			ctx.updateVariable(right, atom, node, unit);
			return atom;
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooObject remainder(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooInteger) right).getVal()).doubleValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooShort) right).getVal()).doubleValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooLong) right).getVal()).doubleValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooFloat) right).getVal()).doubleValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()).doubleValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooChar) right).getVal()).doubleValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooInteger) right).getVal()).longValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooShort) right).getVal()).longValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooLong) right).getVal()).longValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooFloat) right).getVal()).longValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooDouble) right).getVal()).longValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooChar) right).getVal()).longValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooInteger) right).getVal()).intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooShort) right).getVal()).intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooLong) right).getVal()).intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooFloat) right).getVal()).intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooDouble) right).getVal()).intValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooChar) right).getVal()).intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooShort(left.getVal().remainder(
					((YoyooInteger) right).getVal()).shortValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooShort(left.getVal().remainder(
					((YoyooShort) right).getVal()).shortValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooShort(left.getVal().remainder(
					((YoyooLong) right).getVal()).shortValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooShort(left.getVal().remainder(
					((YoyooFloat) right).getVal()).shortValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooShort(left.getVal().remainder(
					((YoyooDouble) right).getVal()).shortValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooShort(left.getVal().remainder(
					((YoyooChar) right).getVal()).shortValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooInteger) right).getVal()).floatValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooShort) right).getVal()).floatValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooLong) right).getVal()).floatValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooFloat) right).getVal()).floatValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooDouble) right).getVal()).floatValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooChar) right).getVal()).floatValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooChar left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {

		if (right instanceof YoyooInteger) {
			return new YoyooChar((char) left.getVal().remainder(
					((YoyooInteger) right).getVal()).intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooChar((char) left.getVal().remainder(
					((YoyooShort) right).getVal()).intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooChar((char) left.getVal().remainder(
					((YoyooLong) right).getVal()).intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooChar((char) left.getVal().remainder(
					((YoyooFloat) right).getVal()).intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooChar((char) left.getVal().remainder(
					((YoyooDouble) right).getVal()).intValue());
		} else if (right instanceof YoyooChar) {
			return new YoyooChar((char) left.getVal().remainder(
					((YoyooChar) right).getVal()).intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

}
