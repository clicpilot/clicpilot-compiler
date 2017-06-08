package org.yoyoo.core.engine.compile.opt;

import java.math.BigDecimal;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.INumber;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;


public class NegativeOperator extends AbstractOperator {

	protected NegativeOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	private IAtom right;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject rightObj = null;
		switch (right.getAtomType()) {
		case Ref:
		case Operator:
		case Value:
			rightObj = calculateAtom(right, ctx);
			break;
		default:
			super.eval(ctx);
		}

		if (rightObj instanceof INumber) {
			BigDecimal b = ((INumber) rightObj).getVal().negate();
			if (rightObj instanceof YoyooChar)
				return new RuntimeValueAtom(new YoyooChar(b), node, unit);
			else if (rightObj instanceof YoyooShort)
				return new RuntimeValueAtom(new YoyooShort(b), node, unit);
			else if (rightObj instanceof YoyooInteger)
				return new RuntimeValueAtom(new YoyooInteger(b), node, unit);
			else if (rightObj instanceof YoyooLong)
				return new RuntimeValueAtom(new YoyooLong(b), node, unit);
			else if (rightObj instanceof YoyooFloat)
				return new RuntimeValueAtom(new YoyooFloat(b), node, unit);
			else if (rightObj instanceof YoyooDouble)
				return new RuntimeValueAtom(new YoyooDouble(b), node, unit);
			else
				return super.eval(ctx);
		} else {
			return super.eval(ctx);
		}
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 1);
		this.right = arguments[0];
	}

	public IType operatorTypeCheck() throws CompileException {

		IType rightType = right.getYoyooType();
		if (!(PrimitiveType.isInteger(rightType))
				&& PrimitiveType.isShort(rightType)
				&& PrimitiveType.isLong(rightType)
				&& PrimitiveType.isFloat(rightType)
				&& PrimitiveType.isDouble(rightType)) {
			throw new CompileException.InvalidExpr(node, unit);
		}
		return rightType;
	}

}
