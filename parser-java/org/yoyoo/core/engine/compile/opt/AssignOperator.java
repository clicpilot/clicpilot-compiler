package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.NullAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.RefType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class AssignOperator extends AbstractOperator {

	protected AssignOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);

	}

	private IAtom left;

	private IAtom right;

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 2);
		this.left = arguments[0];
		this.right = arguments[1];

	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		IRuntimeValueAtom atom = getValueAtom(right, ctx);

		YoyooObject rightObj = calculateAtom(right, ctx);

		if (rightObj instanceof YoyooInteger) {
			int i = ((YoyooInteger) rightObj).getVal().intValue();
			atom.setValue(new YoyooInteger(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooShort) {
			short i = (short) ((YoyooShort) rightObj).getVal().shortValue();
			atom.setValue(new YoyooShort(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooLong) {
			long i = ((YoyooLong) rightObj).getVal().longValue();
			atom.setValue(new YoyooLong(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooFloat) {
			float i = (float) ((YoyooFloat) rightObj).getVal().floatValue();
			atom.setValue(new YoyooFloat(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooDouble) {
			double i = (double) ((YoyooDouble) rightObj).getVal().doubleValue();
			atom.setValue(new YoyooDouble(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooBoolean) {
			boolean i = (boolean) ((YoyooBoolean) rightObj).getVal();
			atom.setValue(new YoyooBoolean(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooString) {
			String i = (String) ((YoyooString) rightObj).getVal();
			atom.setValue(new YoyooString(i));
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else if (rightObj instanceof YoyooObject) {
			atom.setValue(rightObj);
			ctx.updateVariable(left, atom, node, unit);
			return atom;
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	public IType operatorTypeCheck() throws CompileException {

		IType leftType = left.getYoyooType();
		IType rightType = right.getYoyooType();
		if(left instanceof ReferenceAtom && right instanceof ReferenceAtom) {
			((ReferenceAtom)left).setAssignedType(rightType);
		}
		if(leftType instanceof RefType && !leftType.isTypeOf(rightType, YoyooEnvironment.getDefault())) {
			throw new CompileException.TypeMismatch(rightType, leftType, node,
					unit);
		}
		else if (!(leftType instanceof RefType) && !rightType.isTypeOf(leftType, YoyooEnvironment.getDefault()) && !(right instanceof NullAtom)) {
			throw new CompileException.TypeMismatch(rightType, leftType, node,
							unit);
		}
//		if (!rightType.isTypeOf(leftType, YoyooEnvironment.getDefault())) {
//			throw new CompileException.TypeMismatch(rightType, leftType, node,
//					unit);
//		}
		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
