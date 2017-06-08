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


public class PostIncreaseOperator extends AbstractOperator {

	protected PostIncreaseOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
	}

	private IAtom left;


	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

			IRuntimeValueAtom atom = getValueAtom(left, ctx);
			IRuntimeValueAtom atomClone = null;
			atomClone = atom.cloneAtom();
			
			YoyooObject leftObj = calculateAtom(left, ctx);
			
			if (leftObj instanceof YoyooInteger) {
				int i = ((YoyooInteger) leftObj).getVal().intValue();
				i++;
	
				atom.setValue(new YoyooInteger(i));
				
				ctx.updateVariable(left, atom, node, unit);
				return atomClone;
			} else if (leftObj instanceof YoyooShort) {
				short i = (short) ((YoyooShort) leftObj).getVal().shortValue();
				i++;
	
				atom.setValue(new YoyooShort(i));
	
				ctx.updateVariable(left, atom, node, unit);
				return atomClone;
			} else if (leftObj instanceof YoyooLong) {
				long i = ((YoyooLong) leftObj).getVal().longValue();
				i++;
	
				atom.setValue(new YoyooLong(i));
	
				ctx.updateVariable(left, atom, node, unit);
				return atomClone;
			} else if (leftObj instanceof YoyooFloat) {
				float i = (float) ((YoyooFloat) leftObj).getVal().floatValue();
				i++;
	
				atom.setValue(new YoyooFloat(i));
	
				ctx.updateVariable(left, atom, node, unit);
				return atomClone;
			} else if (leftObj instanceof YoyooDouble) {
				double i = (double) ((YoyooDouble) leftObj).getVal().doubleValue();
				i++;
	
				atom.setValue(new YoyooDouble(i));
	
				ctx.updateVariable(left, atom, node, unit);
				return atomClone;
			} else {
				throw new YoyooRTException.CannotEval(node, unit, ctx);
			}
		
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 1);
		this.left = arguments[0];

	}

	public IType operatorTypeCheck() throws CompileException {
		IType rightType = left.getYoyooType();
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
