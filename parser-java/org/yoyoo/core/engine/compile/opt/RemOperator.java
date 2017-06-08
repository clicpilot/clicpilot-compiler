package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;


public class RemOperator extends AbstractNumberOperator {

	protected RemOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);

		if (leftObj instanceof YoyooInteger) {
			return new RuntimeValueAtom(remainder((YoyooInteger) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooShort) {
			return new RuntimeValueAtom(remainder((YoyooShort) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooLong) {
			return new RuntimeValueAtom(
					remainder((YoyooLong) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooFloat) {
			return new RuntimeValueAtom(remainder((YoyooFloat) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooDouble) {
			return new RuntimeValueAtom(remainder((YoyooDouble) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooChar) {
			return new RuntimeValueAtom(
					remainder((YoyooChar) leftObj, rightObj, ctx), node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooObject remainder(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject remainder(YoyooChar left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().remainder(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().remainder(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().remainder(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().remainder(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

}
