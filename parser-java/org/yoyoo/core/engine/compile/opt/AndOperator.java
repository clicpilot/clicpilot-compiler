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


public class AndOperator extends AbstractOperator {

	protected AndOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
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
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooObject rightObj = calculateAtom(right, ctx);

		if (leftObj instanceof YoyooBoolean && rightObj instanceof YoyooBoolean) {
			YoyooBoolean b1 = (YoyooBoolean) leftObj;
			YoyooBoolean b2 = (YoyooBoolean) rightObj;
			return new RuntimeValueAtom(new YoyooBoolean(b1.getVal()
					&& b2.getVal()), node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

	}

	public IType operatorTypeCheck() throws CompileException {

		IType leftType = left.getYoyooType();
		IType rightType = right.getYoyooType();
		if (PrimitiveType.isBoolean(leftType)
				&& PrimitiveType.isBoolean(rightType)) {
			return new PrimitiveType.YoyooBoolean(unit, node);
		} else {
			if (!PrimitiveType.isBoolean(leftType))
				throw new CompileException.TypeMismatch(leftType,
						new PrimitiveType.YoyooBoolean(unit, node), node, unit);
			else
				throw new CompileException.TypeMismatch(rightType,
						new PrimitiveType.YoyooBoolean(unit, node), node, unit);
		}
	}

}
