package org.yoyoo.core.engine.compile.opt;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.NullAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.compile.declaration.YoyooRefClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.RefType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.runtime.YoyooRTException.CannotEval;
import org.yoyoo.core.engine.runtime.YoyooRTException.NullPoint;
import org.yoyoo.core.engine.yoyoo.lang.INumber;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;


public class VariableAssignOperator extends AbstractOperator {
	public VariableAssignOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IType type, IAtom assignAtom, String name) {
		super(node, unit, ycls);
		this.type = type;
		this.assignAtom = assignAtom;
		this.name = name;
	}


	protected IAtom assignAtom;

	protected String name;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		
		YoyooObject obj = null;
		ReferenceAtom atom;
		if (assignAtom != null && !(assignAtom instanceof NullAtom)) {
			obj = assign(ctx);
			try{
				
				obj.setInstanceName(name);	
			} catch (NullPointerException e) {
				YoyooRTException rt = new YoyooRTException("varaible "+name+" is null!", node,  unit , ctx);
				rt.setFunctionCallStack(ctx.getFunctionCallStack());
				throw  rt;
			}
			
		} else {
			YoyooClass myycls = type.map2YoyooClass(YoyooEnvironment.getDefault()) ;
			if(myycls instanceof YoyooTypeDefineClass) {
				YoyooObject innerObj = ((YoyooTypeDefineClass)myycls).createStaticInstance(ctx);
				innerObj.setInstanceName(name);
				obj = new YoyooNull(innerObj);
				obj.setFieldOwner(innerObj.getFieldOwner());
				obj.setInstanceName(name);
			} else if(myycls instanceof YoyooRefClass) {
				YoyooObject innerObj = ((YoyooRefClass)myycls).instance(ctx);
				innerObj.setInstanceName(name);
				obj = new YoyooRef<YoyooObject>(((RefType)this.type), innerObj);
				obj.setFieldOwner(innerObj.getFieldOwner());
				obj.setInstanceName(name);
			} else {
				YoyooObject innerObj = ((YoyooPrimitiveClass)myycls).instance(ctx);
				innerObj.setInstanceName(name);
				obj = new YoyooNull(innerObj);
				obj.setFieldOwner(innerObj.getFieldOwner());
				obj.setInstanceName(name);
			}
			
		}
		
		ctx.registerVariable(name, new RuntimeValueAtom(obj, node, unit), node, unit);
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

	}

	protected YoyooObject assign(RuntimeContext ctx) throws YoyooRTException {
		YoyooEnvironment env = YoyooEnvironment.getDefault();
		IRuntimeValueAtom val = assignAtom.getVal(ctx);
		if(val==null) {
			NullPoint nullPoint = new YoyooRTException.NullPoint(node, unit, ctx);
			nullPoint.setFunctionCallStack(ctx.getFunctionCallStack());
			throw nullPoint;
			
		}
		YoyooObject obj = val.getValue();
		if (type.isPrimitiveType()) {
			Class<? extends IYoyooObject> cls = type.map2JavaClass(env);
			Class<? extends IYoyooObject> assignCls;
			if(obj instanceof YoyooNull) {
				assignCls = ((YoyooNull) obj).getClassInstance().getClass();
			} else {
				assignCls = obj.getClass();
			}
			
			if(assignCls == YoyooRef.class) {
				try {
					if(obj instanceof YoyooNull) {
						return (YoyooObject)obj.cloneAtom();
					} else {
						return (YoyooObject)((YoyooRef<?>)obj).getValue().cloneAtom();
					}
				} catch (YoyooRTException e) {
					throw new YoyooRTException.ObjectCannotbeCloned(node, unit, ctx);
				}
			}
			else if (cls == assignCls 
					|| type.isArray()
					) {
				return obj;
			} else if (obj instanceof INumber) {
				if (cls == YoyooShort.class) {
					return new YoyooShort(((INumber) obj).getVal().shortValue());
				} else if (cls == YoyooInteger.class) {
					return new YoyooInteger(((INumber) obj).getVal().intValue());
				} else if (cls == YoyooLong.class) {
					return new YoyooLong(((INumber) obj).getVal().longValue());
				} else if (cls == YoyooFloat.class) {
					return new YoyooFloat(((INumber) obj).getVal().floatValue());
				} else if (cls == YoyooDouble.class) {
					return new YoyooDouble(((INumber) obj).getVal()
							.doubleValue());
				} else if (cls == YoyooChar.class) {
					return new YoyooInteger(((INumber) obj).getVal().intValue());
				}
			}

		} else if(type instanceof RefType) {
			((RefType)type).getValueType().map2YoyooClass(env);
			return new YoyooRef<YoyooObject>((RefType)type, obj);
		} else {
			if(obj instanceof YoyooRef<?> && !(type instanceof RefType)) {
				return ((YoyooRef<?>)obj).getValue();
			} else {
				return obj;
			}
		}
		CannotEval cannotEval = new YoyooRTException.CannotEval(node, unit, ctx);
		cannotEval.setFunctionCallStack(ctx.getFunctionCallStack());
		throw cannotEval;

	}

	public IType operatorTypeCheck() throws CompileException {
		if(assignAtom!=null)
		{
			if(assignAtom instanceof ArrayInitOperator) {
				((ArrayInitOperator)assignAtom).type = type;
			}
			YoyooEnvironment env = YoyooEnvironment.getDefault();
			IType assginType = assignAtom.getYoyooType();
			if(assginType instanceof ReferenceNameType) {
				List<IType> assignTypeArguments = ((ReferenceNameType)assginType).getTypeArguments();
				if(assignTypeArguments!=null)
				for(Iterator<IType> iterator = assignTypeArguments.listIterator(); iterator.hasNext();) {
					IType assignTypeArgument = iterator.next();
					if(assignTypeArgument instanceof ReferenceNameType && ((ReferenceNameType)assignTypeArgument).isTypeParameter()) {
						if(assignAtom instanceof FuncCallOperator) {
							int index = ((ReferenceNameType)assignTypeArgument).getTypeParameterIndex();
							IType replacement = ((FuncCallOperator)assignAtom).getRefNameType().getTypeArguments().get(index);
							((ListIterator<IType>)iterator).set(replacement);
						}
						
					}
				}
			}
			
			if(type instanceof RefType && !type.isTypeOf(assginType, env)) {
				throw new CompileException.TypeMismatch(assginType, type, node,
						unit);
			} else if (!(type instanceof RefType) && !assginType.isTypeOf(type, env) && !(assignAtom instanceof NullAtom)) {
				throw new CompileException.TypeMismatch(assginType, type, node,
								unit);
			} else {
				return new PrimitiveType.YoyooVoid(unit, node);
			}

		}
		else
		{
			return new PrimitiveType.YoyooVoid(unit, node);
		}
	}

}
