package org.yoyoo.core.engine.compile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;


public class JavaMethodAndYoyooMethodWrapper {
	
	
	private Method method;

	public Method getMethod() {
		return method;
	}

	private YoyooBaseMethod ymethod;

	public YoyooBaseMethod getYmethod() {
		return ymethod;
	}

	private SimpleNode node;

	private CompilationUnit unit;

	private boolean nativeMethod;
	
	private boolean dummyMethod;
	
	private IType returnType;
	
	
	
	public JavaMethodAndYoyooMethodWrapper(SimpleNode node, CompilationUnit unit) {
		super();
		this.node = node;
		this.unit = unit;
		this.dummyMethod = true;
	}
	
	public JavaMethodAndYoyooMethodWrapper(YoyooBaseMethod ymethod, IType returnType,
			SimpleNode node, CompilationUnit unit) {
		super();
		this.ymethod = ymethod;
		this.returnType = returnType;
		this.node = node;
		this.unit = unit;
		this.nativeMethod = false;
	}
	
	public JavaMethodAndYoyooMethodWrapper(YoyooBaseMethod ymethod,	SimpleNode node, CompilationUnit unit) {
		this(ymethod, null, node, unit);		
	}

	public JavaMethodAndYoyooMethodWrapper(Method method, SimpleNode node,
			CompilationUnit unit) {
		super();
		this.method = method;
		this.node = node;
		this.unit = unit;
		this.nativeMethod = true;
	}
	


	public IType getReturnType() {
		if (ymethod != null && ymethod instanceof YoyooMethod) {
			if(this.returnType!=null)
				return returnType;
			else
				return ((YoyooMethod) ymethod).getReturnType();
		}
			
		else if (ymethod != null && ymethod instanceof YoyooClassConstructor) {
			return ReferenceNameType.createConstructorReturnType(((YoyooClassConstructor) ymethod));
		} else
			return new PrimitiveType.YoyooVoid(unit, node);
	}

	public void call(RuntimeContext ctx,
			IRuntimeValueAtom thisRef, IRuntimeValueAtom args[], FuncCallOperator funcCaller) throws YoyooRTException {
		if(!dummyMethod) {
			ctx.pushStackFrame(false);
			try {
				if (ymethod != null) {
	
					ctx.registerThis(thisRef, node, unit);
					Set<String> paramNames = ymethod.getParameters().getSymbols()
							.keySet();
					int j = 0;
					for (Iterator<String> i = paramNames.iterator(); i.hasNext();) {
						String paramName = i.next();
	//					String mName = null;
	//					IRuntimeValueAtom vAtom = null;
						if(args[j]==null) {
							System.err.println(paramName + " is null;");
							ctx.printCallStack();
						}
						switch (args[j].getRuntimeAtomType()) {
						case Ref:
							throw new YoyooRTException.CannotEval(node, unit, ctx);
	
						case Value:
							ctx.registerVariable(paramName, args[j], node, unit);
							break;
						
						default:
							throw new YoyooRTException.CannotEval(node, unit, ctx);
						}
						j++;
	
					}
	
					ymethod.execute(ctx, thisRef, args, funcCaller);
	
					
				} else {
	
					ctx.registerThis(thisRef, node, unit);
					Object[] params = null;
					try {
						params = new Object[args.length];
						int j = 0;
						for (IRuntimeValueAtom arg : args) {
	
							switch (arg.getRuntimeAtomType()) {
							case Ref:
								throw new YoyooRTException.CannotEval(node, unit, ctx);
							case Value:
								
								YoyooObject object = args[j].getValue();
								if(object instanceof YoyooNull && ((YoyooNull) object).getClassInstance().getClass()==YoyooRef.class) {
									params[j] = (YoyooObject) ((YoyooNull) object).getClassInstance();
									//params[j] = null;
								} else {
									
									if(object instanceof YoyooNull) {
										params[j] = null;
									} else {
										params[j] = object;
									}
									//params[j] = null;
								}
								
								
								break;
							
							default:
								throw new YoyooRTException.CannotEval(node, unit, ctx);
							}
							j++;
	
						}
						if(thisRef.getValue() instanceof YoyooRef) {
							method.invoke(((YoyooRef<?>)thisRef.getValue()).getValue(), params);
						} else {
							method.invoke(thisRef.getValue(), params);
						}
						
							
	
					} catch (SecurityException e) {
						e.printStackTrace();
						throw new YoyooRTException.CannotInvokeMethod(method
								.getName(), node, unit, ctx);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						throw new YoyooRTException.CannotInvokeMethod(method
								.getName(), node, unit, ctx);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new YoyooRTException.CannotInvokeMethod(method
								.getName(), node, unit, ctx);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						throw new YoyooRTException.CannotInvokeMethod(method
								.getName(), node, unit, ctx);					
					}
				}
			} catch (YoyooRTException e) {
				throw e;
			} finally {
				ctx.popStackFrame();
			}
		}

	}

	public boolean isNativeMethod() {
		return nativeMethod;
	}

	public boolean isDummyMethod() {
		return dummyMethod;
	}

}
