package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;


public class ArrayInitOperator extends AbstractOperator {
	
	private IAtom[] value;
	


	public ArrayInitOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IType type, IAtom[] value) {
		super(node, unit, ycls);
		this.value = value;
		this.type = type;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {


	}

	public IType operatorTypeCheck() throws CompileException {
		for(int i=0;i<value.length;i++) {
			if(value[i] instanceof ArrayInitOperator) {
				((ArrayInitOperator)value[i]).type = type;
				((ArrayInitOperator)value[i]).operatorTypeCheck();
			} else {
				if(!value[i].getYoyooType().getName().equals(type.getName())) {
					throw new CompileException.TypeMismatch(value[i].getYoyooType(), type, node, unit);
				}
			}
		}
		return type;
		
	}
	


	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		return new RuntimeValueAtom(initArray(value, ctx), node, unit);
	}
	
	private YoyooArray initArray(IAtom[] value, RuntimeContext ctx) throws YoyooRTException
	{
		YoyooArray array = new YoyooArray(value.length);
		for(int i=0;i<value.length;i++) {
			if(value[i] instanceof ArrayInitOperator) {
				array.setData(i, initArray(((ArrayInitOperator)value[i]).value, ctx), ctx);
			} else {
				array.setData(i, value[i].getVal(ctx).getValue(), ctx);
			}
		}
		return array;
	}
	
	

}
