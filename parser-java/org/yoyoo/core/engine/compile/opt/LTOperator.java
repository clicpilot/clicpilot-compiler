package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;


public class LTOperator extends AbstractCompareOperator {

	protected LTOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);

		if (leftObj instanceof YoyooInteger) {
			return new RuntimeValueAtom(compare((YoyooInteger) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooShort) {
			return new RuntimeValueAtom(
					compare((YoyooShort) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooLong) {
			return new RuntimeValueAtom(compare((YoyooLong) leftObj, rightObj, ctx),
					node, unit);
		} else if (leftObj instanceof YoyooFloat) {
			return new RuntimeValueAtom(
					compare((YoyooFloat) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooDouble) {
			return new RuntimeValueAtom(
					compare((YoyooDouble) leftObj, rightObj, ctx), node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooBoolean compare(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooBoolean(
					left.getVal().shortValue() < ((YoyooInteger) right)
							.getVal().intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooBoolean(
					left.getVal().shortValue() < ((YoyooShort) right).getVal()
							.intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooBoolean(
					left.getVal().shortValue() < ((YoyooLong) right).getVal()
							.intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooBoolean(
					left.getVal().shortValue() < ((YoyooFloat) right).getVal()
							.intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooBoolean(
					left.getVal().shortValue() < ((YoyooDouble) right).getVal()
							.intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooBoolean compare(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooBoolean(
					left.getVal().intValue() < ((YoyooInteger) right).getVal()
							.intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooBoolean(
					left.getVal().intValue() < ((YoyooShort) right).getVal()
							.intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooBoolean(
					left.getVal().intValue() < ((YoyooLong) right).getVal()
							.intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooBoolean(
					left.getVal().intValue() < ((YoyooFloat) right).getVal()
							.intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooBoolean(
					left.getVal().intValue() < ((YoyooDouble) right).getVal()
							.intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooBoolean compare(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooBoolean(
					left.getVal().longValue() < ((YoyooInteger) right).getVal()
							.intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooBoolean(
					left.getVal().longValue() < ((YoyooShort) right).getVal()
							.intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooBoolean(
					left.getVal().longValue() < ((YoyooLong) right).getVal()
							.intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooBoolean(
					left.getVal().longValue() < ((YoyooFloat) right).getVal()
							.intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooBoolean(
					left.getVal().longValue() < ((YoyooDouble) right).getVal()
							.intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooBoolean compare(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooBoolean(
					left.getVal().floatValue() < ((YoyooInteger) right)
							.getVal().intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooBoolean(
					left.getVal().floatValue() < ((YoyooShort) right).getVal()
							.intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooBoolean(
					left.getVal().floatValue() < ((YoyooLong) right).getVal()
							.intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooBoolean(
					left.getVal().floatValue() < ((YoyooFloat) right).getVal()
							.intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooBoolean(
					left.getVal().floatValue() < ((YoyooDouble) right).getVal()
							.intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooBoolean compare(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooBoolean(
					left.getVal().doubleValue() < ((YoyooInteger) right)
							.getVal().intValue());
		} else if (right instanceof YoyooShort) {
			return new YoyooBoolean(
					left.getVal().doubleValue() < ((YoyooShort) right).getVal()
							.intValue());
		} else if (right instanceof YoyooLong) {
			return new YoyooBoolean(
					left.getVal().doubleValue() < ((YoyooLong) right).getVal()
							.intValue());
		} else if (right instanceof YoyooFloat) {
			return new YoyooBoolean(
					left.getVal().doubleValue() < ((YoyooFloat) right).getVal()
							.intValue());
		} else if (right instanceof YoyooDouble) {
			return new YoyooBoolean(
					left.getVal().doubleValue() < ((YoyooDouble) right)
							.getVal().intValue());
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

}
