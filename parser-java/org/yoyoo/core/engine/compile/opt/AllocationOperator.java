package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.ParserUtil;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeArrayAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class AllocationOperator extends FuncCallOperator {


	
	private IAtom[] arrayDimAtom;

	private YoyooTypeDefineClass pycls;
	
	private ArrayInitOperator arrayInitOpt;
	

	public AllocationOperator(IType type, SimpleNode node,
			CompilationUnit unit, YoyooTypeDefineClass ycls, IAtom[] args, IAtom[] arrayDimAtom, ArrayInitOperator arrayInitOpt) {
		super(node, unit, ycls);
		this.type = type;
		this.args = args;
		this.numOfParam = args == null ? 0 : args.length;
		this.arrayDimAtom = arrayDimAtom;
		this.arrayInitOpt = arrayInitOpt;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		try{
			if(type.isPrimitiveType() && type.isArray())
			{
				if(arrayDimAtom!=null) {
					int len[] = new int[arrayDimAtom.length];
					for(int i=0;i<arrayDimAtom.length;i++)
					{
						IRuntimeValueAtom tempValAtom = arrayDimAtom[i].getVal(ctx);
						YoyooInteger tempArrayLen = (YoyooInteger)(tempValAtom.getValue());
						len[i] = tempArrayLen.getVal().intValue();
					}
					YoyooArray array = this.initArray(len, type, ctx);
					RuntimeArrayAtom arrayAtom = new RuntimeArrayAtom(array, node, unit);
					return arrayAtom;
				} else if(arrayInitOpt!=null) {
					return arrayInitOpt.getVal(ctx);
				} else {
					throw new YoyooRTException.CannotEval(node, unit, ctx);
				}
			}
			else
			{
				if(!type.isArray())
				{
					IRuntimeValueAtom thisRef = pycls.createInstance(ctx);
					IRuntimeValueAtom argAtoms[] = calculateArguments(ctx); 
					
					if (this.method == null)
						return thisRef;
					else {
						this.method.call(ctx, thisRef, argAtoms, this);
						return thisRef;
					}
				}
				else
				{
					if(arrayDimAtom!=null) {
						int len[] = new int[arrayDimAtom.length];
						for(int i=0;i<arrayDimAtom.length;i++)
						{
							IRuntimeValueAtom tempValAtom = arrayDimAtom[i].getVal(ctx);
							YoyooInteger tempArrayLen = (YoyooInteger)(tempValAtom.getValue());
							len[i] = tempArrayLen.getVal().intValue();
						}
						YoyooArray array = this.initArray(len, type, ctx);
						RuntimeArrayAtom arrayAtom = new RuntimeArrayAtom(array, node, unit);
						return arrayAtom;
					} else if(arrayInitOpt!=null) {
						return arrayInitOpt.getVal(ctx);
					} else {
						throw new YoyooRTException.CannotEval(node, unit, ctx);
					}
				}
			}
		} catch (YoyooRTException e) {
			throw e;
		} finally {
			ctx.allocationReturned(this);
		}
	}

	// protected IRuntimeValueAtom eval(RuntimeContext ctx) throws
	// YoyooRTException{
	// loadParam(ctx);
	// ValueAtom valAtom = null;
	// if(!type.isPrimitiveType())
	// {
	// valAtom = createInstance(cls, ctx);
	// YoyooClassConstructor yoyooMethod = cls.getConstrutor(paramCls);
	// if(yoyooMethod!=null)
	// {
	// ctx.pushStackFrame(false);
	// ctx.registerThis(valAtom);
	// Set<String> paramNames =
	// yoyooMethod.getParameters().getSymbols().keySet();
	// int j=0;
	// for(Iterator<String> i = paramNames.iterator();i.hasNext();)
	// {
	// String paramName = i.next();
	// String mName = null;
	// ValueAtom vAtom = null;
	// switch(arg.getArguments()[j].getAtomType())
	// {
	// case Ref:
	// mName = ((ReferenceAtom)arg.getArguments()[j]).getName();
	// vAtom = ctx.lookupAtom(mName, node, unit);
	// ctx.registerVariable(paramName, vAtom);
	// break;
	// case Value:
	// ctx.registerVariable(paramName, (ValueAtom)arg.getArguments()[j]);
	// break;
	// case Operator:
	// Operator operator = (Operator)arg.getArguments()[j];
	//
	// IValueAtom val = ctx.evaluateOperator(operator);
	// switch(val.getAtomType())
	// {
	// case Ref:
	// mName = ((ReferenceAtom)val).getName();
	// vAtom = ctx.lookupAtom(mName, node, unit);
	// ctx.registerVariable(paramName, vAtom);
	// break;
	// case Operator:
	// case Value:
	// default:
	//									
	// ctx.registerVariable(paramName,
	// (ValueAtom)ctx.evaluateOperator(operator));
	// break;
	//									
	// }
	// break;
	// default:
	// super.eval(ctx);
	// }
	//					
	// }
	//				
	// yoyooMethod.execute(ctx, null, null);
	// ctx.popStackFrame();
	// return valAtom;
	//				
	// }
	// else
	// {
	// throw new YoyooRTException(new String[]{methodName},
	// CompileErrorMessage.Undefined, node, unit);
	// }
	//			
	// }
	// else
	// {
	// return super.eval(ctx);
	// }
	// }

	public void installArguments(IAtom[] arguments) throws CompileException {
//		if (super.checkNumOfArguments(1, arguments.length)) {
//			FuncArgumentsOperator arg = (FuncArgumentsOperator) arguments[0];
//			if (arg != null) {
//				args = arg.getArguments();
//				numOfParam = args == null ? 0 : args.length;
//			} else {
//				args = null;
//				numOfParam = 0;
//			}
//		}
		if (ycls == null)
			ycls = this.unit.getClassInCompiling();
	}

	public IType typeCheck() throws CompileException {
		
		if(arrayDimAtom!=null)
		{
			for(IAtom atom : arrayDimAtom)
			{
				if(!PrimitiveType.isInteger(atom.getYoyooType()))
				{
					throw new CompileException.TypeMismatch(atom.getYoyooType(), new PrimitiveType.YoyooInteger(unit, node),
							node, unit);
				}
			}
			
		} else if(arrayInitOpt!=null) {
			arrayInitOpt.type = this.type;
			arrayInitOpt.typeCheck();
		}
		
		IType[] argTypes = this.loadParamType();
		if(type.isPrimitiveType())
		{
			if(type.isArray())
			{
				return this.type;
			}
			else
			{
				throw new CompileException.WrongOperatorArguments(node, unit);
			}
		}
		else
		{
			if(type.isArray())
			{
				IAtom lastArrayDimAtom = null;
				
				if(arrayDimAtom!=null && type.getArrayDim() == arrayDimAtom.length)
				{
					lastArrayDimAtom = arrayDimAtom[arrayDimAtom.length-1];
				}
				if(lastArrayDimAtom == null && arrayInitOpt==null)
				{
					throw new CompileException.WrongArrayInitializer(node, unit);
				}
				
			}
			else
			{
				pycls = (YoyooTypeDefineClass) ((ReferenceNameType) type)
						.map2YoyooClass(YoyooEnvironment.getDefault());
				if (pycls == null) {
					throw new CompileException.Undefined(type.getName(), node, unit);
				} else if(pycls.isInterface() || pycls.isAbstract()) {
					throw new CompileException.CannotCreateInstance(type, node, unit);
				}
				this.method = pycls.getConstructor(argTypes, node, unit);
		
				if (method == null)
					throw new CompileException.ConstructorUndefined(((YoyooTypeDefineClass)ycls).getFullName(),
							node, unit);
				
			}
			checkThrows();
			return this.type;
		}
		
		

	}

	private YoyooArray initArray(int[] len, IType type, RuntimeContext ctx) throws YoyooRTException
	{
		if(len.length>1)
		{
			YoyooArray array = new YoyooArray(len[0]);
			int[] sublen = new int[len.length-1];
			System.arraycopy(len, 1, sublen, 0, len.length-1);
			initMultiDimArray(array, sublen, type, ctx);
			return array;
		}
		else if (len.length==1)
		{
			YoyooArray array = new YoyooArray(len[0]);
			initSingleDimArray(array, type, ctx);
			return array;
		}
		else
		{
			System.err.println("array dim cannot be 0!");
			return null;
		}
	}
	private void initMultiDimArray(YoyooArray array, int[] len, IType type, RuntimeContext ctx) throws YoyooRTException
	{
		
//		for(int j=0;j<len.length;j++)
		{
			for(int i=0;i<array.getLength();i++)
			{
				YoyooArray subarray = new YoyooArray(len[0]);
				array.setData(i, subarray, ctx);
				if(len.length>1)
				{
					
					int[] sublen = new int[len.length-1];
					System.arraycopy(len, 1, sublen, 0, len.length-1);
					initMultiDimArray(subarray, sublen, type, ctx);
				}
				else
				{
					
					initSingleDimArray(subarray, type, ctx);
				}
			}
		}
		
	}	
	
	private void initSingleDimArray(YoyooArray array, IType type, RuntimeContext ctx) throws YoyooRTException
	{
		YoyooEnvironment env = YoyooEnvironment.getDefault();
		for(int j=0;j<array.getLength();j++)
		{
			if(type.isPrimitiveType())
			{
//				try {
//					YoyooObject obj = (YoyooObject)type.map2JavaClass(env).newInstance();
					YoyooObject obj = (YoyooObject)ParserUtil.newInstance(type.map2JavaClass(env));
					
					array.setData(j, obj, ctx);
//				} catch (InstantiationException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				}
			}
			else
			{
				YoyooObject obj = ((YoyooTypeDefineClass)((ReferenceNameType)type).map2YoyooClass(env)).createInstance(ctx).getValue();
				array.setData(j, obj, ctx);
			}
		}
	}

}
