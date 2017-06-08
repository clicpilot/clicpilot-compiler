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


public class SubtractOperator extends AbstractNumberOperator {

	protected SubtractOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);

		if (leftObj instanceof YoyooInteger) {
			return new RuntimeValueAtom(subtract((YoyooInteger) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooShort) {
			return new RuntimeValueAtom(
					subtract((YoyooShort) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooLong) {
			return new RuntimeValueAtom(
					subtract((YoyooLong) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooFloat) {
			return new RuntimeValueAtom(
					subtract((YoyooFloat) leftObj, rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooDouble) {
			return new RuntimeValueAtom(subtract((YoyooDouble) leftObj,
					rightObj, ctx), node, unit);
		} else if (leftObj instanceof YoyooChar) {
			return new RuntimeValueAtom(
					subtract((YoyooChar) leftObj, rightObj, ctx), node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	private YoyooObject subtract(YoyooShort left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject subtract(YoyooInteger left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject subtract(YoyooLong left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject subtract(YoyooFloat left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject subtract(YoyooDouble left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	private YoyooObject subtract(YoyooChar left, YoyooObject right, RuntimeContext ctx)
			throws YoyooRTException {
		if (right instanceof YoyooInteger) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooInteger) right).getVal()));
		} else if (right instanceof YoyooShort) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooShort) right).getVal()));
		} else if (right instanceof YoyooLong) {
			return new YoyooLong(left.getVal().subtract(
					((YoyooLong) right).getVal()));
		} else if (right instanceof YoyooFloat) {
			return new YoyooFloat(left.getVal().subtract(
					((YoyooFloat) right).getVal()));
		} else if (right instanceof YoyooDouble) {
			return new YoyooDouble(left.getVal().subtract(
					((YoyooDouble) right).getVal()));
		} else if (right instanceof YoyooChar) {
			return new YoyooInteger(left.getVal().subtract(
					((YoyooChar) right).getVal()));
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

}
