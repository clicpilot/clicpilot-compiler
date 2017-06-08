package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class CondOperator extends AbstractOperator {

	public CondOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		
	}

	private IAtom trueAtom;

	private IAtom falseAtom;

	private IAtom condAtom;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject b = ( (condAtom.getVal(ctx))).getValue();
		if (b instanceof YoyooBoolean) {
			if (((YoyooBoolean) b).getVal()) {
				return trueAtom.getVal(ctx);
			} else {
				return falseAtom.getVal(ctx);
			}
		}
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		checkNumOfArguments(arguments.length, 3);
		this.condAtom = arguments[0];;
		this.trueAtom = arguments[1];
		this.falseAtom = arguments[2];

	}

	public IAtom getCondAtom() {
		return condAtom;
	}

	public void setCondAtom(IAtom condAtom) {
		this.condAtom = condAtom;
	}

	

	public IType operatorTypeCheck() throws CompileException {
		IType condType = condAtom.getYoyooType();
		if (!PrimitiveType.isBoolean(condType)) {
			throw new CompileException.TypeMismatch(condType,
						new PrimitiveType.YoyooBoolean(unit, node), node, unit);
		}
		IType trueType = trueAtom.getYoyooType();
		IType falseType = falseAtom.getYoyooType();
		if(trueType.equalsTo(falseType)) {
			return trueType;
		} else if (trueType.isTypeOf(falseType, YoyooEnvironment.getDefault())){
			return trueType;
		} else if (falseType.isTypeOf(trueType, YoyooEnvironment.getDefault())){
			return falseType;
		} else {
			throw new CompileException.TypeMismatch(trueType,
					falseType, node, unit);
		}
		
	}

}
