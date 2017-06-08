package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;


public class PreIncreaseOperator extends AbstractOperator {
	protected PreIncreaseOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	private IAtom right;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		IRuntimeValueAtom atom = getValueAtom(right, ctx);

		YoyooObject rightObj = calculateAtom(right, ctx);

		if (rightObj instanceof YoyooInteger) {
			int i = ((YoyooInteger) rightObj).getVal().intValue();
			i++;

			atom.setValue(new YoyooInteger(i));

			ctx.updateVariable(right, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooShort) {
			short i = (short) ((YoyooShort) rightObj).getVal().shortValue();
			i++;

			atom.setValue(new YoyooShort(i));

			ctx.updateVariable(right, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooLong) {
			long i = ((YoyooLong) rightObj).getVal().longValue();
			i++;

			atom.setValue(new YoyooLong(i));

			ctx.updateVariable(right, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooFloat) {
			float i = (float) ((YoyooFloat) rightObj).getVal().floatValue();
			i++;

			atom.setValue(new YoyooFloat(i));

			ctx.updateVariable(right, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooDouble) {
			double i = (double) ((YoyooDouble) rightObj).getVal().doubleValue();
			i++;

			atom.setValue(new YoyooDouble(i));

			ctx.updateVariable(right, atom, node, unit);
			return atom;
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 1);
		this.right = arguments[0];

	}

	public IType operatorTypeCheck() throws CompileException {
		IType rightType = right.getYoyooType();
		if (!(PrimitiveType.isInteger(rightType)
				|| PrimitiveType.isShort(rightType)
				|| PrimitiveType.isLong(rightType)
				|| PrimitiveType.isFloat(rightType)
				|| PrimitiveType.isDouble(rightType) 
				|| PrimitiveType.isChar(rightType))
				|| rightType.isArray()) {
			throw new CompileException.InvalidExpr(node, unit);
		}
		if (PrimitiveType.isShort(rightType)) {
			return new PrimitiveType.YoyooInteger(unit, node);
		} else {
			return rightType;
		}
	}
}
