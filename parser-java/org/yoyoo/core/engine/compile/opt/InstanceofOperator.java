package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
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


public class InstanceofOperator extends AbstractOperator {

	public InstanceofOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IType type) {
		super(node, unit, ycls);
		this.type = type;
	}

	private IAtom left;

	private IType type;

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 1);
		this.left = arguments[0];

	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject leftObj = calculateAtom(left, ctx);
		YoyooClass yoyooClass = leftObj.getYoyooClass();
		if(yoyooClass instanceof YoyooTypeDefineClass) {
			boolean r = ((YoyooTypeDefineClass) yoyooClass).isTypeof(type);
			return new RuntimeValueAtom(new YoyooBoolean(r), node, unit);
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}

		

	}

	public IType operatorTypeCheck() throws CompileException {

		left.getYoyooType();

		return new PrimitiveType.YoyooBoolean(unit, node);
	}

}
