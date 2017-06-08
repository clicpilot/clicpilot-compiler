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
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class ArrayIndexOperator extends AbstractOperator {
	
	private IAtom array;

	private IAtom index;

	public ArrayIndexOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom atom) {
		super(node, unit, ycls);
		this.index = atom;
	}

	public IAtom getArray() {
		return array;
	}

	public void setArray(IAtom array) {
		this.array = array;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		// TODO Auto-generated method stub

	}

	public IType operatorTypeCheck() throws CompileException {
		IType arrayType = array.getYoyooType();
		IType indexType = index.getYoyooType();
		if(!arrayType.isArray())
		{
			throw new CompileException.NotArray(arrayType, node, unit);
		}
		else if(!PrimitiveType.isInteger(indexType))
		{
			throw new CompileException.TypeMismatch(indexType, new PrimitiveType.YoyooInteger(unit, node), node, unit);
		}
		int dim = arrayType.getArrayDim();
		if(dim>1)
		{
			type = arrayType.typeClone(node);
			type.setArrayDim(dim-1);
			return type;
		}
		else
		{
			type = arrayType.typeClone(node);;
			type.setArrayDim(0);
			type.setArray(false);
			return type;
		}
		
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		IRuntimeValueAtom arrayAtom = array.getVal(ctx);
		IRuntimeValueAtom indexAtom = index.getVal(ctx);
		int indexVal = ((YoyooInteger)indexAtom.getValue()).getVal().intValue();
		return new RuntimeValueAtom(((YoyooArray)arrayAtom.getValue()).getData(indexVal, ctx), node, unit);
	}
	
	public IRuntimeValueAtom updateValue(RuntimeContext ctx,
			IRuntimeValueAtom runtimeAtom) throws YoyooRTException {
		IRuntimeValueAtom arrayAtom = array.getVal(ctx);
		IRuntimeValueAtom indexAtom = index.getVal(ctx);
		int indexVal = ((YoyooInteger)indexAtom.getValue()).getVal().intValue();
		
		YoyooObject yobj = null;
		switch (arrayAtom.getRuntimeAtomType()) {
		case Ref:
			return super.eval(ctx);
		case Value:
			yobj = ((RuntimeValueAtom) arrayAtom).getValue();
			((YoyooArray) yobj).setData(indexVal, runtimeAtom
					.getValue(), ctx);
			return new RuntimeValueAtom(((YoyooArray) yobj).getData(indexVal, ctx), node, unit);
		default:
			return super.eval(ctx);
		}
	}

}
