package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;


public class DivideOperator extends AbstractNumberOperator {

	protected DivideOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);

		if (leftObj instanceof YoyooInteger) {
			return new RuntimeValueAtom(
					divide((YoyooInteger) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooShort) {
			return new RuntimeValueAtom(divide((YoyooShort) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooLong) {
			return new RuntimeValueAtom(divide((YoyooLong) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooFloat) {
			return new RuntimeValueAtom(divide((YoyooFloat) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooDouble) {
			return new RuntimeValueAtom(
					divide((YoyooDouble) leftObj, rightObj, ctx), node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooObject divide(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().divide(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().divide(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().divide(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().divide(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooDouble) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject divide(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().divide(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().divide(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().divide(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().divide(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooDouble) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject divide(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooLong(left.getVal().divide(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooLong(left.getVal().divide(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().divide(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooDouble) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject divide(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooFloat(left.getVal().divide(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooFloat(left.getVal().divide(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().divide(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooDouble) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject divide(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().divide(
					((YoyooDouble) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

}
