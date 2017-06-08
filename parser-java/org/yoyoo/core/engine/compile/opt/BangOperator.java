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
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class BangOperator extends AbstractOperator {

	public BangOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
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

		if (rightObj instanceof YoyooBoolean) {
			boolean b = ((YoyooBoolean) rightObj).getVal();
			b = !b;
			return new RuntimeValueAtom(new YoyooBoolean(b), node, unit);
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
		if (PrimitiveType.isBoolean(rightType)) {
			return new PrimitiveType.YoyooBoolean(unit, node);
		} else {
			throw new CompileException.TypeMismatch(rightType,
					new PrimitiveType.YoyooBoolean(unit, node), node, unit);
		}

	}

}
