package org.yoyoo.core.engine.compile.opt;

import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.CompileException.UnhandledException;
import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.JavaMethodAndYoyooMethodWrapper;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.AtomType;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.compile.stm.CatchStatement;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;


public class FuncCallOperator extends AbstractOperator{

	private IAtom func;

	protected IAtom args[];

	protected int numOfParam;

	protected Class<?>[] paramCls;

	protected Object[] param;

	// protected YoyooTypeDefineClass ycls;
	private String methodName;


	protected JavaMethodAndYoyooMethodWrapper method;
	
	
	protected IRuntimeValueAtom returnValueAtom;
	
	private ReferenceNameType refNameType;

	private String refName;
	
	private transient IType[] argTypes;
	
	protected transient List<IType> catchExceptionTypes;

	protected transient List<IType> methodThrownExceptionTypes;
	
	//protected transient boolean returned;

	protected FuncCallOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		this(node, unit, ycls, null);
	}

	public FuncCallOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom func) {
		super(node, unit, ycls);
		this.func = func;


	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		// loadParam(ctx);

		
		JavaMethodAndYoyooMethodWrapper executeMethod = this.method;
		
		IRuntimeValueAtom ref = null;

		switch (func.getAtomType()) {
			case Ref:
			case Operator:
				if(refName!=null) {
					if(refName.equals(RuntimeContext.SUPER)) {
						ref = ctx.lookupAtom(RuntimeContext.THIS, node, unit);
					} else {
						ref = ctx.lookupAtom(refName, node, unit);
					}
				} else if(func instanceof DotOperator) {
					ref = ((DotOperator) func).getLeft().getVal(ctx);					
				}
				
				if(ref!=null && ref.getValue()!=null && ref.getValue().getYoyooClass()!=null && !this.method.isNativeMethod() && !this.method.isDummyMethod() && !(this.method.getYmethod() instanceof YoyooClassConstructor)){ //&&((YoyooTypeDefineClass)ref.getValue().getYoyooClass()).isDescendant(this.method.getYmethod().getUnit())) {
					try {
						JavaMethodAndYoyooMethodWrapper methodWrapper = ((YoyooTypeDefineClass)ref.getValue().getYoyooClass()).getMethod(methodName, argTypes, refNameType==null?null:refNameType.getTypeArguments(), node, unit);
//						if(methodWrapper.getYmethod().getUnit() != this.method.getYmethod().getUnit()) {
//							//System.out.println(this.methodName+" "+methodWrapper.getYmethod().getUnit().getFullName()+ " "+this.method.getYmethod().getUnit().getFullName());
//							executeMethod = methodWrapper;
//						}
						if(methodWrapper.getYmethod()!=null) {
							((YoyooMethod)methodWrapper.getYmethod()).accessibilityCheck((YoyooTypeDefineClass)this.ycls);
						}
						executeMethod = methodWrapper;
					} catch (CompileException e) {
						e.printStackTrace();
						throw new YoyooRTException(e.getMessage(), node,  unit, ctx);
					}
					
				}
				break;
			
				
			case Value:
				// IRuntimeValueAtom mAtom = func.getVal(ctx);
				// switch(mAtom.getRuntimeAtomType())
				// {
				// case Ref:
				// String name = ((RuntimeReferenceAtom)mAtom).getName();
				// thisRef = ctx.lookupAtom(name, node, unit);
				// break;
				// case Value:
				// thisRef = mAtom;
				// break;
				// }
				//				
				super.eval(ctx);
				break;
			default:
				super.eval(ctx);

		}
		IRuntimeValueAtom argAtoms[] = calculateArguments(ctx); 
		if(!this.method.isNativeMethod() && !this.method.isDummyMethod() && this.method.getYmethod().isAbstract()) {
			try {
				if(ref.getValue() instanceof YoyooRef<?>) {
					YoyooRef<?> refValue = (YoyooRef<?>)ref.getValue();
					executeMethod = ((YoyooTypeDefineClass)refValue.getValue().getYoyooClass()).getMethod(methodName, argTypes, refNameType==null?null:refNameType.getTypeArguments(), node, unit);
				} else {
					executeMethod = ((YoyooTypeDefineClass)ref.getValue().getYoyooClass()).getMethod(methodName, argTypes, refNameType==null?null:refNameType.getTypeArguments(), node, unit);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		} 

		executeMethod.call(ctx, ref, argAtoms, this);
		

			
		return this.returnValueAtom;
	}
	
	protected IRuntimeValueAtom[] calculateArguments(RuntimeContext ctx) throws YoyooRTException
	{
		if(args!=null)
		{
			IRuntimeValueAtom argAtoms[] = new IRuntimeValueAtom[args.length]; 
			for(int i = 0;i<args.length;i++)
			{
				IRuntimeValueAtom vAtom = null;
				switch (args[i].getAtomType()) {
				case Ref:
					String mName = ((ReferenceAtom) args[i]).getName();
					vAtom = ctx.lookupAtom(mName, node, unit);
					try {
							if(vAtom!=null) {
								
									argAtoms[i] = vAtom.cloneAtom();
							}
							else
								argAtoms[i] = null;
					} catch (YoyooRTException e) {
						throw e;//new YoyooRTException.ObjectCannotbeCloned(node, unit, ctx);
					}
					break;
				case Value:
					vAtom = args[i].getVal(ctx);
					argAtoms[i] = vAtom;
					break;
				case Operator:
					Operator operator = (Operator) args[i];
					IRuntimeValueAtom val = ctx.evaluateOperator(operator);
					if(val!=null)
					{
						switch (val.getRuntimeAtomType()) {
						case Ref:
							throw new YoyooRTException.CannotEval(node, unit, ctx);
						case Value:
							argAtoms[i] = val;
							break;
						default:
							throw new YoyooRTException.CannotEval(node, unit, ctx);
						}
					}
					else
					{
						throw new YoyooRTException.CannotEval(node, unit, ctx);
					}
					break;
				default:
					throw new YoyooRTException.CannotEval(node, unit, ctx);
				}
			}
			
			return argAtoms;
		}
		return new IRuntimeValueAtom[0];
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		if (super.checkNumOfArguments(1, arguments.length)) {
			FuncArgumentsOperator arg = (FuncArgumentsOperator) arguments[0];
			if (arg != null) {
				args = arg.getArguments();
				numOfParam = args == null ? 0 : args.length;
			} else {
				args = null;
				numOfParam = 0;
			}
		}

		switch (func.getAtomType()) {
		case Ref:
			methodName = this.getReferenceName(func);
			if(methodName.indexOf(".")!=-1)
			{
				String[] names = methodName.split("\\.");
				int i = methodName.lastIndexOf(".");
				this.refName = methodName.substring(0, i);
				methodName = names[names.length-1];
				
				
				
			}
			else if (this.unit != null)
			{
				this.refName = RuntimeContext.THIS;
				this.ycls = this.unit.getClassInCompiling();
			}
			else
			{
				this.refName = RuntimeContext.THIS;
			}
			
			break;
		case Operator:
			if(func instanceof DotOperator)
			{
				this.refName = ((DotOperator)func).getLeftName();
				this.methodName = ((DotOperator)func).getRightName();
			}
			break;
		case Value:
			// IType type = func.getYoyooType();
			//				
			// if(!type.isPrimitiveType())
			// {
			// ycls = ((ReferenceNameType)type).map2YoyooClass(env);
			// }
			// break;
			throw new CompileException.InvaildAccessMethod(methodName, node,
					unit);
		default:
			throw new CompileException.InvaildAccessMethod(methodName, node,
					unit);

		}

	}

	// protected void loadParam(RuntimeContext ctx) throws YoyooRTException
	// {
	//
	// paramCls = new Class[numOfParam];
	// param = new Object[numOfParam];
	//
	// for(int i=0;i<numOfParam;i++)
	// {
	// IAtom tmpAtom = args[i];
	// switch(tmpAtom.getAtomType())
	// {
	// case Ref:
	// param[i] = ((ValueAtom)tmpAtom.getVal(ctx)).getValue();
	// if(param[i]==null)
	// {
	// throw new YoyooRTException(new
	// String[]{((ReferenceNameAtom)tmpAtom).getName()},
	// CompileErrorMessage.Undefined, node, unit);
	// }
	//
	// break;
	// case Operator:
	//					
	// param[i] =
	// ((ValueAtom)ctx.evaluateOperator((Operator)tmpAtom)).getValue();
	// break;
	// case Value:
	//				
	// param[i] = (YoyooObject)(((ValueAtom)tmpAtom).getValue());
	//					
	// break;
	// default:
	// throw new
	// YoyooRTException(CompileErrorMessage.InvaildAccessMethod.getMsg(), node,
	// unit );
	//				
	// }
	//			
	//			
	// paramCls[i] = param[i].getClass();
	//			
	// }
	// }

	protected IType[] loadParamType() throws CompileException {
		IType[] paramClsName = new IType[numOfParam];
		for (int i = 0; i < numOfParam; i++) {
			paramClsName[i] = args[i].getYoyooType();
		}
		return paramClsName;
	}

	// protected ValueAtom callMethod(RuntimeContext ctx, ValueAtom thisRef,
	// String methodName, Class cls) throws YoyooRTException
	// {
	// if(thisRef.getValue() instanceof YoyooTypeDefine)
	// {
	// YoyooClass typeUnit = ((YoyooTypeDefine)thisRef.getValue()).getUnit();
	// YoyooBaseMethod yoyooMethod = typeUnit.checkMethod(methodName, paramCls);
	// if(yoyooMethod!=null)
	// {
	// ctx.pushStackFrame(false);
	// ctx.registerThis(thisRef);
	// Set<String> paramNames =
	// yoyooMethod.getParameters().getSymbols().keySet();
	// int j=0;
	// for(Iterator<String> i = paramNames.iterator();i.hasNext();)
	// {
	// String paramName = i.next();
	// String mName = null;
	// ValueAtom vAtom = null;
	// switch(args[j].getAtomType())
	// {
	// case Ref:
	// mName = ((ReferenceNameAtom)arg.getArguments()[j]).getName();
	// vAtom = ctx.lookupAtom(mName, node, unit);
	// ctx.registerVariable(paramName, vAtom);
	// break;
	// case Value:
	// ctx.registerVariable(paramName, (ValueAtom)arg.getArguments()[j]);
	// break;
	// case Operator:
	// Operator operator = (Operator)arg.getArguments()[j];
	// IValueAtom val = ctx.evaluateOperator(operator);
	// switch(val.getAtomType())
	// {
	// case Ref:
	// mName = ((ReferenceNameAtom)val).getName();
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
	// throw new YoyooRTException(CompileErrorMessage.CannotEval.getMsg(), node,
	// unit );
	// }
	//					
	// }
	//				
	// ValueAtom valAtom = yoyooMethod.execute(ctx, null, null);
	// ctx.popStackFrame();
	// return valAtom;
	//				
	// }
	// else
	// {
	// ctx.pushStackFrame(false);
	// ctx.registerThis(thisRef);
	//				
	// try {
	// Method method = findNativeMethod(cls, methodName, paramCls);
	// // Method method = cls.getMethod(methodName, paramCls);
	// if(method == null)
	// {
	// throw new YoyooRTException(new String[]{methodName},
	// CompileErrorMessage.Undefined, node, unit);
	// }
	// else
	// {
	// method.invoke(thisRef.getValue(), param);
	// }
	//					
	// } catch (SecurityException e) {
	// throw new YoyooRTException(new String[]{methodName},
	// CompileErrorMessage.Undefined, node, unit);
	// } catch (IllegalArgumentException e) {
	// throw new YoyooRTException(new String[]{methodName},
	// CompileErrorMessage.CannotInvokeMethod, node, unit);
	// } catch (IllegalAccessException e) {
	// throw new YoyooRTException(new String[]{methodName},
	// CompileErrorMessage.CannotInvokeMethod, node, unit);
	// } catch (InvocationTargetException e) {
	// throw new YoyooRTException(new String[]{methodName},
	// CompileErrorMessage.CannotInvokeMethod, node, unit);
	// }
	// ctx.popStackFrame();
	// }
	// }
	// return null;
	// }

	public IAtom getFunc() {
		return func;
	}

	public IType operatorTypeCheck() throws CompileException {

		
		argTypes = this.loadParamType();
		YoyooClass methodYcls = null;
		boolean isArg = false;
		boolean isVar = false;
		if(func.getAtomType()==AtomType.Ref && !refName.equals(RuntimeContext.THIS) && !refName.equals(RuntimeContext.SUPER)) {
			String names[] = refName.split("\\.");
			YoyooVariable yoyooVaiable =  ((ReferenceAtom)func).getStm().getVariableTable().getSymbol(names[0]);
			
			if(yoyooVaiable==null && ycls!=null)
			{
				CatchStatement catchStm = ((ReferenceAtom)func).getStm().getCatchStm();
				if(catchStm!=null && catchStm.getException().getName().equals(refName)) {
					this.refNameType = (ReferenceNameType)catchStm.getException().getType();
				} else {				
					YoyooBaseMethod stmMethod = ((ReferenceAtom)func).getStm().getMethod();
					FormalParameter param = stmMethod.getParameter(names[0]);
					if(param!=null) {
						this.refNameType = (ReferenceNameType)param.getType();
						isArg = true;
					} else {
						YoyooField field = ((YoyooTypeDefineClass)ycls).getField(names[0]);
						if(field == null) {
							ReferenceNameType staticReferenceName = new ReferenceNameType(unit, node, (YoyooTypeDefineClass)ycls);
							staticReferenceName.setName(names[0]);
							if(!unit.getEnv().solveUnknownStaticReferenceType(unit, staticReferenceName)) {
								throw new CompileException.Undefined(names[0], node, unit);
							} else {
								YoyooTypeDefineClass ycls = (YoyooTypeDefineClass) ((ReferenceNameType) staticReferenceName).map2YoyooClass(YoyooEnvironment.getDefault());
//								this.refNameType = ycls.getField(names[1]).getType();
								this.refNameType = staticReferenceName;
							}					
						} else if(field.accessibilityCheck((YoyooTypeDefineClass)ycls)) {
							this.refNameType = (ReferenceNameType)field.getType();
						}
					}
				}
			}
			else
			{
				IType varAssignedType = yoyooVaiable.getAssignedType();
				if(varAssignedType instanceof ReferenceNameType)
					this.refNameType = (ReferenceNameType)varAssignedType;
				if(this.refNameType==null)
					this.refNameType = (ReferenceNameType)yoyooVaiable.getType();
				isVar = true;
			}
			methodYcls = refNameType.map2YoyooClass(YoyooEnvironment.getDefault());

			for(int j=1;j<names.length;j++) {
				YoyooField field = ((YoyooTypeDefineClass)methodYcls).getField(names[j]);
				if(field == null)
					throw new CompileException.Undefined(names[j], node, unit);
				this.refNameType = (ReferenceNameType)field.getType();
				YoyooClass tempycls = refNameType.map2YoyooClass(YoyooEnvironment.getDefault());
				if(tempycls == null){
					throw new CompileException.Undefined(refNameType.getName(), node, unit);
				}
			}
			
			methodYcls = (YoyooTypeDefineClass)refNameType.map2YoyooClass(YoyooEnvironment.getDefault());
		} else if(func.getAtomType()==AtomType.Operator) {
			
			if(func instanceof DotOperator)
			{
				try{
					this.refNameType = (ReferenceNameType)((DotOperator)func).getLeft().getYoyooType();
				} catch (java.lang.ClassCastException e) {
					System.err.println(func.getCompilationUnit().getSource().getFile().getPath());
					System.err.println(((DotOperator)func).getLeft().getYoyooType().getName());
					e.printStackTrace();
				}
				
				this.methodName = ((DotOperator)func).getRightName();
				methodYcls = (YoyooTypeDefineClass)refNameType.map2YoyooClass(YoyooEnvironment.getDefault());
			}

		} else if(func.getAtomType()==AtomType.Ref && refName.equals(RuntimeContext.SUPER)) {
			if(this.ycls!=null && this.ycls instanceof YoyooTypeDefineClass) {
				if(((YoyooTypeDefineClass)this.ycls).getParentYClass()!=null) {
					methodYcls = ((YoyooTypeDefineClass)this.ycls).getParentYClass();
				} else {
					throw new CompileException.ParentClassUndefine(((YoyooTypeDefineClass)this.ycls).getFullName(), node, unit);
				}
			}
		}
		List<IType> typeArgumentTypes = refNameType==null?null:refNameType.getTypeArguments();		
		
		this.method = ((YoyooTypeDefineClass)(methodYcls==null?ycls:methodYcls)).getMethod(methodName, argTypes, typeArgumentTypes, node, unit);
		if(func instanceof ReferenceAtom && ((ReferenceAtom)func).getStm()!=null)
		{
			YoyooBaseMethod ymethod = ((ReferenceAtom)func).getStm().getMethod();
			if(ymethod.isStaticMethod() && !this.method.isNativeMethod() && (!isVar && !isArg && !this.method.getYmethod().isStaticMethod())) {
				throw new CompileException.CannotCallMethodInStatic(methodName, node, unit);
			} else if(refNameType!=null && refNameType.isStaticReference() && this.method.getYmethod()!=null && !this.method.getYmethod().isStaticMethod()) {
				throw new CompileException.CannotCallNonStaticMethod(methodName, node, unit);
			} else if(refNameType!=null && !refNameType.isStaticReference() && this.method.getYmethod()!=null && !this.method.getYmethod().isStaticMethod()){
				//warining here
			}
		}
		
		checkThrows();
		
		if (this.method == null) {
			throw new CompileException.MethodUndefine(methodName, ((YoyooTypeDefineClass)(methodYcls==null?ycls:methodYcls))
					.getFullName(), node, unit);
		}
		else {
			IType retType = method.getReturnType();
			if(retType instanceof ReferenceNameType && ((ReferenceNameType)retType).isTypeParameter() && this.refNameType.getTypeArguments()!=null) {
				return this.refNameType.getTypeArguments().get(((ReferenceNameType)retType).getTypeParameterIndex());
			} else {
				return retType;
			}
		}

		// return checkMethod(ctx, thisRef, methodName, cls, paramClsName);
	}
	
	protected void checkThrows() throws UnhandledException {
		if(method!=null && !method.isNativeMethod() && !method.isDummyMethod()) {
			List<ReferenceNameType> throwsList = method.getYmethod().getThrowsList();
			boolean foundHandler = false;
			for(ReferenceNameType exceptionType:throwsList) {
				if(catchExceptionTypes!=null) {
					for(IType type:catchExceptionTypes) {
						if(exceptionType.equalsTo(type)) {
							foundHandler = true;
							break;
						}
						
					}
				}
				if(!foundHandler && methodThrownExceptionTypes!=null) {
					for(IType type:methodThrownExceptionTypes) {
						if(exceptionType.equalsTo(type)) {
							foundHandler = true;
							break;
						}
						
					}
				}
				if(!foundHandler) {
					throw new CompileException.UnhandledException(exceptionType.getName(), node, unit);
				}
			}
			
		}
	}
	
	public List<ReferenceNameType> getThrowList() {
		if(method!=null && !method.isNativeMethod()) {
			return method.getYmethod().getThrowsList();
		} else {
			return null;
		}
	}

	public void setYcls(YoyooTypeDefineClass ycls) {
		this.ycls = ycls;
	}

	public void setReturnValueAtom(IRuntimeValueAtom value) {
		this.returnValueAtom = value;
	}
	
	public boolean hasReturnValueAtom() {
		if(!(this instanceof AllocationOperator) && !(PrimitiveType.YoyooVoid.isVoid(this.method.getReturnType()))) {
			return this.returnValueAtom != null;
		}
		else {
			return true;
		}
	}
	
	public boolean clearReturnValueAtom() {
		return this.returnValueAtom != null;
	}
	
	public boolean isCallThisConstructor () {
		return this.methodName!=null && this.methodName.equals(RuntimeContext.THIS);
	}
	
	public boolean isCallSuperConstructor () {
		return this.methodName!=null && this.methodName.equals(RuntimeContext.SUPER);
	}
	
	
	public void setCatchExceptionTypes(List<IType> catchExceptionTypes) {
		this.catchExceptionTypes = catchExceptionTypes;
	}

	public void setMethodThrownExceptionTypes(List<IType> methodThrownExceptionTypes) {
		this.methodThrownExceptionTypes = methodThrownExceptionTypes;
	}
	
	
	public ReferenceNameType getRefNameType() {
		return refNameType;
	}
	public JavaMethodAndYoyooMethodWrapper getMethod() {
		return method;
	}


	public IAtom[] getArgs() {
		return args;
	}

	public String getMethodName() {
		return methodName;
	}

}
