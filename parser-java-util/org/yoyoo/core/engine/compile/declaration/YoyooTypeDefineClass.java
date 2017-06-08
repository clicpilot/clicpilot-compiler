package org.yoyoo.core.engine.compile.declaration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.JavaMethodAndYoyooMethodWrapper;
import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.ParserUtil;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.YoyooTypeDefineLoader;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.PrimitiveType.YoyooNull;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAbstract;
import org.yoyoo.core.engine.parser.YoyooConstructor;
import org.yoyoo.core.engine.parser.YoyooDeclarationName;
import org.yoyoo.core.engine.parser.YoyooExtendsList;
import org.yoyoo.core.engine.parser.YoyooExtendsName;
import org.yoyoo.core.engine.parser.YoyooFieldDecl;
import org.yoyoo.core.engine.parser.YoyooImplementsList;
import org.yoyoo.core.engine.parser.YoyooImplementsName;
import org.yoyoo.core.engine.parser.YoyooMedthodDecl;
import org.yoyoo.core.engine.parser.YoyooTypeDeclName;
import org.yoyoo.core.engine.parser.YoyooTypeParameter;
import org.yoyoo.core.engine.parser.YoyooTypeParameterName;
import org.yoyoo.core.engine.parser.YoyooTypeParameters;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.type.Interface;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class YoyooTypeDefineClass extends AbstractDeclaration implements
		YoyooClass {

	public YoyooTypeDefineClass(CompilationUnit unit, SimpleNode node) {
		super(node);
		constructors = new SymbolTable<YoyooClassConstructor>();
		methods = new SymbolTable<YoyooMethod>();
		fields = new SymbolTable<YoyooField>();
		this.compilationUnit = unit;
	}

	private YoyooClassConstructor staticClassConstructor;
	
	private SymbolTable<YoyooClassConstructor> constructors;

	private SymbolTable<YoyooMethod> methods;	

	private SymbolTable<YoyooField> fields;
	
	private transient SymbolTable<YoyooField> initedStaticField =  new SymbolTable<YoyooField>();
	
	private transient SymbolTable<YoyooField> ambiguousFields = new SymbolTable<YoyooField>();

	private CompilationUnit compilationUnit;

	private String typeName;

	//private String extendsName;
	
	private ReferenceNameType extendsReferenceType;
	
	private List<ReferenceNameType> imlpmentsReferenceNames;

	private Class<? extends IYoyooObject> typeClass;
	
	private YoyooTypeDefineClass parentYClass;
	
	private boolean isInterface;
	
	private boolean isAbstract;
	
	private  List<TypeArgument> typeArguments;
	
	private boolean typeChecked;
	
	private boolean classChecked;


	private static class TypeArgument {
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getBound() {
			return bound;
		}
		public void setBound(String bound) {
			this.bound = bound;
		}
		private String bound;
	}

	
	public List<TypeArgument> getTypeArguments() {
		return typeArguments;
	}
	
	public YoyooClass getParentYClass() {
		return parentYClass;
	}
	
	public boolean isInterface() {
		return isInterface;
	}
	
	public boolean isAbstract() {
		return isAbstract;
	}
	

	public List<ReferenceNameType> getImlpmentsReferenceNames() {
		return imlpmentsReferenceNames;
	}

	
	private Map<String, YoyooObject> staticFieldsValue = new LinkedHashMap<String, YoyooObject>();
	
	public boolean isDescendant(YoyooTypeDefineClass yclass) {
		if(!yclass.isInterface) {
			if(parentYClass !=null && parentYClass == yclass) {
				return true;
			} else if (parentYClass !=null) {
				return parentYClass.isDescendant(yclass);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean isTypeof(IType type) {
		if(type instanceof ReferenceNameType) {
			ReferenceNameType referenceNameType = (ReferenceNameType)type;
			if(referenceNameType.getFullName().equals(this.getFullName())) {
				return true;
			} else if(parentYClass !=null && parentYClass.isTypeof(type)) {
				return true;
			} else if(imlpmentsReferenceNames!=null) {
				for(ReferenceNameType impleType : imlpmentsReferenceNames) {
					
					if(type.equalsTo(impleType)) {
						return true;
					} else {
						YoyooTypeDefineClass ycls = (YoyooTypeDefineClass)impleType.map2YoyooClass(YoyooEnvironment.getDefault());
						if(ycls.isTypeof(type)) {
							return true;
						}

					}
				}
			}
		}
		return false;
				
	} 

	public int isTypeParameterName(String key) {
		int i=0;
		if(typeArguments!=null) {
			for(TypeArgument typeArgument : typeArguments) {
				if(typeArgument.name.equals(key)) {
					return i;
				}
				i++;
			}
		}
		return -1;
	}

	@Override
	public Object visit(YoyooAbstract node, Object data) {
		this.isAbstract = true;
		return null;
	}

	@Override
	public Object visit(YoyooTypeDeclName node, Object data) {
		
		typeName = node.first_token.image;
		//try {
			typeClass = YoyooEnvironment.getDefault().getTypeLoader()
					.loadClass(typeName);
			if (typeClass == null) {
				compilationUnit.addError(new CompileException.Undefined(typeName, node,
						compilationUnit));
			}
			isInterface = typeName.equals(YoyooTypeDefineLoader.formatClassName(Interface.class));

			
		//} catch (ClassNotFoundException e) {
		//	compilationUnit.addError(new CompileException.Undefined(typeName, node, compilationUnit));
		//}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.declaration.AbstractDeclaration#getUnit()
	 */
	@Override
	public YoyooTypeDefineClass getUnit() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.stm.AbstractStm#visit(org.yoyoo.core.engine.parser.YoyooDeclarationName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooDeclarationName node, Object data) {
		name = (String) super.visit(node, data);
		return null;
	}
	


	@Override
	public Object visit(YoyooTypeParameters node, Object data) {
		int size = node.jjtGetNumChildren();
		if(typeArguments == null) {
			typeArguments = new ArrayList<TypeArgument>(size);
		}
		for(int i=0;i<size;i++) {
			YoyooTypeParameter typeParam = (YoyooTypeParameter)node.jjtGetChild(i);
			YoyooTypeParameterName nameNode = (YoyooTypeParameterName)typeParam.jjtGetChild(0);
			TypeArgument arg = new TypeArgument();
			arg.setName(nameNode.first_token.image);
			typeArguments.add(arg);
		}
		return null;
	}
	
	

	private void addItems(SimpleNode node, AbstractDeclaration decl) {
		try {

			if (node instanceof YoyooConstructor) {
				if(((YoyooClassConstructor) decl).isStaticMethod()){
					if(this.staticClassConstructor == null)
						this.staticClassConstructor = (YoyooClassConstructor)decl;
					else
						compilationUnit.addError(new CompileException.StaticConstructorDefined(decl.getName(), node, getCompilationUnit()));
				} else{					
					constructors.putSymbol(((YoyooClassConstructor)decl).getFullName(),
						(YoyooClassConstructor) decl, node, compilationUnit);
				}
			} else if (node instanceof YoyooMedthodDecl) {
				methods.putSymbol(((YoyooMethod)decl).getFullName(), (YoyooMethod) decl, node,
						compilationUnit);
			} else if (node instanceof YoyooFieldDecl) {
				fields.putSymbol(decl.getName(), (YoyooField) decl, node, compilationUnit);
			}

		} catch (CompileException e) {
			compilationUnit.addError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooConstructor,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooConstructor node, Object data) {
		YoyooClassConstructor decl = new YoyooClassConstructor(this, node);
		this.compilationUnit.setMethodInCompiling(decl);
		compilationUnit.pushNewLocalVariableStack();
		node.childrenAccept(decl, decl);
		this.addItems(node, decl);
		compilationUnit.popLocalVariableStack();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooFieldDecl,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooFieldDecl node, Object data) {
		YoyooField decl = new YoyooField(this, node);
		node.childrenAccept(decl, decl);
		this.addItems(node, decl);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooMedthodDecl,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooMedthodDecl node, Object data) {
		YoyooMethod decl = new YoyooMethod(this, node);
		this.compilationUnit.setMethodInCompiling(decl);
		compilationUnit.pushNewLocalVariableStack();
		node.childrenAccept(decl, decl);
		this.addItems(node, decl);
		compilationUnit.popLocalVariableStack();
		return null;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooExtendsName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooExtendsName node, Object data) {
		TypeVisitor v = new TypeVisitor(this.getCompilationUnit(), node);
		v.visit(node, data);
		extendsReferenceType = (ReferenceNameType)v.getType();
		return null;
	}
	
	

	@Override
	public Object visit(YoyooExtendsList node, Object data) {
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(YoyooImplementsList node, Object data) {
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(YoyooImplementsName node, Object data) {
		TypeVisitor v = new TypeVisitor(this.getCompilationUnit(), node);
		v.visit(node, data);
		if(imlpmentsReferenceNames==null) {
			imlpmentsReferenceNames = new ArrayList<ReferenceNameType>(1);
		}
		imlpmentsReferenceNames.add((ReferenceNameType)v.getType());

		return node.childrenAccept(this, data);
	}
	
//	@Override
//	public Object visit(YoyooReferenceTypeName node, Object data) {
//		ReferenceNameType type = new ReferenceNameType(this.compilationUnit, node);
//		node.jjtAccept(type, data);
//		if(imlpmentsReferenceNames==null) {
//			imlpmentsReferenceNames = new ArrayList<ReferenceNameType>(1);
//		}
//		imlpmentsReferenceNames.add(type);
//		return null;
//	}

	/**
	 * @return the unit
	 */
	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getFullName() {
		return compilationUnit.getPackageName() + "."
				+ name;
	}

	// private String createMethodKeyName(String name, Class[] paramCls)
	// {
	// StringBuffer sb = new StringBuffer(name);
	// sb.append("(");
	// boolean first = true;
	// for(Class cls :paramCls)
	// {
	// if(first)
	// {
	// first = false;
	// }
	// else
	// {
	// sb.append(", ");
	// }
	// sb.append(cls.getName());
	// }
	// sb.append(")");
	// return sb.toString();
	// }
	//	
	// private String createMethodKeyName(String name, String[] paramCls)
	// {
	// StringBuffer sb = new StringBuffer(name);
	// sb.append("(");
	// boolean first = true;
	// for(String cls :paramCls)
	// {
	// if(first)
	// {
	// first = false;
	// }
	// else
	// {
	// sb.append(", ");
	// }
	// sb.append(cls);
	// }
	// sb.append(")");
	// return sb.toString();
	// }

	// public YoyooMethod getMethod(String name, Class[] paramCls)
	// {
	// String mName = createMethodKeyName(name, paramCls);
	// if(methods.contains(mName))
	// {
	// return (YoyooMethod) methods.getSymbol(mName);
	// }
	// else
	// {
	// return null;
	// }
	//		
	// }

	// public YoyooClassConstructor getConstrutor(String name, Class[] paramCls)
	// {
	// String mName = createMethodKeyName(name, paramCls);
	// if(constructors.contains(mName))
	// {
	// return (YoyooClassConstructor) constructors.getSymbol(mName);
	// }
	// else
	// {
	// return null;
	// }
	//		
	// }
	

	public SymbolTable<YoyooMethod> getMethods() {
		return methods;
	}
	
	private void copyParentMethods(SymbolTable<YoyooMethod> mymethods) {
		if(this.imlpmentsReferenceNames!=null) {
			for(ReferenceNameType type : imlpmentsReferenceNames) {
				YoyooClass interfaceClass = type.map2YoyooClass(YoyooEnvironment.getDefault());
				SymbolTable<YoyooMethod> pmethods = ((YoyooTypeDefineClass)interfaceClass).getMethods();
				for(YoyooMethod pmethod : pmethods.getSymbols().values()) {
					if(!mymethods.contains(pmethod.getFullName())) {
						mymethods.forceputSymbol(pmethod.getFullName(), pmethod, pmethod.node, pmethod.unit.compilationUnit);
					}
				}
				((YoyooTypeDefineClass)interfaceClass).copyParentMethods(mymethods);
			}
			
		}
		
		if(parentYClass!=null) {
			SymbolTable<YoyooMethod> pmethods = this.parentYClass.getMethods();
			for(YoyooMethod pmethod : pmethods.getSymbols().values()) {
				if(!mymethods.contains(pmethod.getFullName())) {
					mymethods.forceputSymbol(pmethod.getFullName(), pmethod, pmethod.node, pmethod.unit.compilationUnit);					
				} else if(mymethods.contains(pmethod.getFullName())) {
					if(mymethods.getSymbol(pmethod.getFullName()).isAbstract()) {
						mymethods.forceputSymbol(pmethod.getFullName(), pmethod, pmethod.node, pmethod.unit.compilationUnit);
					}
				}
			}
			this.parentYClass.copyParentMethods(mymethods);
		} 
	}
	
	


	public SymbolTable<YoyooField> getFields() {

		return fields;
	}
	
	private void copyParentFields(SymbolTable<YoyooField> myfields, SymbolTable<YoyooField> myAmbiguousFields){
		if(this.imlpmentsReferenceNames!=null) {
			for(ReferenceNameType type : imlpmentsReferenceNames) {
				YoyooClass interfaceClass = type.map2YoyooClass(YoyooEnvironment.getDefault());
				SymbolTable<YoyooField> pfields = ((YoyooTypeDefineClass)interfaceClass).getFields();
				for(YoyooField pfield : pfields.getSymbols().values()) {
					if(!myfields.contains(pfield.getName())) {
						myfields.forceputSymbol(pfield.getName(), pfield, pfield.node, pfield.unit.compilationUnit);
					} else {
						YoyooField field = myfields.getSymbol(pfield.getName());
						if(field.getUnit().isInterface() && pfield.getUnit().isInterface() && pfield.getUnit() != field.getUnit() ) {
							myfields.forceremoveSymbol(pfield.getName(), pfield.node, pfield.unit.compilationUnit);
							myAmbiguousFields.forceputSymbol(pfield.getName(), pfield, pfield.node, pfield.unit.compilationUnit);
						}
					}
				}
				((YoyooTypeDefineClass)interfaceClass).copyParentFields(myfields, myAmbiguousFields);
			}
			
		}
		if(this.parentYClass!=null) {
			SymbolTable<YoyooField> pfields = this.parentYClass.getFields();
			for(YoyooField pfield : pfields.getSymbols().values()) {
				if(!myfields.contains(pfield.name)) {
					try {
						myfields.putSymbol(pfield.name, pfield, pfield.node, pfield.compilationUnit);
					} catch (CompileException e) {
						e.printStackTrace();
					}
				}
			}
			this.parentYClass.copyParentFields(myfields, myAmbiguousFields);
		}
	}

	public YoyooField getField(String name)  {
		YoyooField field = getFields().getSymbol(name);
		return field;
	}

	public JavaMethodAndYoyooMethodWrapper getMethod(String methodName,
			IType[] argTypes, List<IType> typeArgType, SimpleNode node, CompilationUnit unit)
			throws CompileException {
		if(methodName.equals(RuntimeContext.THIS)) {
			JavaMethodAndYoyooMethodWrapper r = getConstructor(argTypes, node, unit);
			if(!r.isNativeMethod())
				return r;
		} else if(methodName.equals(RuntimeContext.SUPER)) {
			if(this.parentYClass!=null) {
				JavaMethodAndYoyooMethodWrapper r = parentYClass.getConstructor(argTypes, node, unit);
				if(!r.isNativeMethod())
					return r;
			} else if(this.parentYClass==null && argTypes.length==0){
				return new JavaMethodAndYoyooMethodWrapper(node, unit);
			}
		} else {
			YoyooMethod ymethod = getYoyooMethod(methodName, argTypes, typeArgType);
			
			if (ymethod == null) {
				
				Method method = getNativeJavaMethod(methodName, argTypes);
				if (method != null) {
					return new JavaMethodAndYoyooMethodWrapper(method, node, unit);
				}
			} else {
				IType returnType = ymethod.getReturnType().typeClone(node);
				if(returnType instanceof ReferenceNameType && ((ReferenceNameType)returnType).isTypeParameter()) {
					if(typeArgType!=null && typeArgType.size()>((ReferenceNameType)returnType).getTypeParameterIndex()) {
						returnType = typeArgType.get(((ReferenceNameType)returnType).getTypeParameterIndex());
					}								
				} else if(returnType instanceof ReferenceNameType && ((ReferenceNameType)returnType).getTypeArguments()!=null && !((ReferenceNameType)returnType).getTypeArguments().isEmpty()) {
					List<IType> returnTypeArguments = ((ReferenceNameType)returnType).getTypeArguments();
					for(ListIterator<IType> returnTypeArgumentsIterator = returnTypeArguments.listIterator(); returnTypeArgumentsIterator.hasNext(); ) {
						IType returnTypeArgument = returnTypeArgumentsIterator.next();
						if(typeArgType!=null && ((ReferenceNameType)returnTypeArgument).isTypeParameter() && typeArgType.size()>((ReferenceNameType)returnTypeArgument).getTypeParameterIndex()) {
							IType returnTypeArgumentReplacemnt = typeArgType.get(((ReferenceNameType)returnTypeArgument).getTypeParameterIndex());
							returnTypeArgumentsIterator.set(returnTypeArgumentReplacemnt);
						}	
					}
					((ReferenceNameType)returnType).setTypeArguments(returnTypeArguments);												
				}  
				return new JavaMethodAndYoyooMethodWrapper(ymethod, returnType, node, unit);
			}
		}
		throw new CompileException.MethodUndefine(methodName, name, node, unit);
	}
	
	

	private YoyooMethod getYoyooMethod(String methodName, IType[] argTypes, List<IType> typeArgType) {
		YoyooEnvironment env = YoyooEnvironment.getDefault();
			cc: for (YoyooMethod method : getMethods().getSymbols().values()) {
	
				if (method.getName().equals(methodName)) {
					Collection<FormalParameter> params = method.getParameters()
							.getSymbols().values();
					if (params.size() == argTypes.length) {
						int i = 0;
						for (FormalParameter param : params) {
							IType paramType = param.getType();
							if(paramType instanceof ReferenceNameType && ((ReferenceNameType)paramType).isTypeParameter()) {
								if(typeArgType==null) {
									paramType = argTypes[((ReferenceNameType)paramType).getTypeParameterIndex()];
								}
								else if(typeArgType==null || typeArgType.size()<=((ReferenceNameType)paramType).getTypeParameterIndex()) {
									continue cc;
								} else {
									paramType = typeArgType.get(((ReferenceNameType)paramType).getTypeParameterIndex());
								}								
							}
							
							if ((argTypes[i] instanceof ReferenceNameType && ((ReferenceNameType)argTypes[i]).isTypeParameter()) || (!(argTypes[i] instanceof PrimitiveType.YoyooNull) && !argTypes[i].isTypeOf(paramType, env))) {
								continue cc;
							}
							i++;
						}
						
						return method;
					}
				}
	
			
		}
		return null;
	}

	private Method getNativeJavaMethod(String methodName, IType[] argTypes) {
		YoyooEnvironment env = YoyooEnvironment.getDefault();
		Class<?>[] argClasses = new Class[argTypes.length];
		boolean[] isArray = new boolean[argTypes.length]; 
		int i = 0;
		for (IType type : argTypes) {
			if(type instanceof ReferenceNameType && ((ReferenceNameType)type).isTypeParameter()) {
				argClasses[i] = YoyooObject.class;
			} else {
				argClasses[i] = type.map2JavaClass(env);
			}
			isArray[i] =  type.isArray();
			i++;
		}
		Method method = ParserUtil.findNativeMethod(methodName, argClasses, isArray, typeClass);
		return method;

	}

	public JavaMethodAndYoyooMethodWrapper getConstructor(IType[] argTypes,
			SimpleNode node, CompilationUnit unit) throws CompileException {
		;
		YoyooClassConstructor ymethod = getYoyooConstructor(argTypes);
		if (ymethod == null) {
			Method method = getNativeMethodAsConstructor(argTypes);
			if (method != null) {
				return new JavaMethodAndYoyooMethodWrapper(method, node, unit);				
			}			
		} else {
			return new JavaMethodAndYoyooMethodWrapper(ymethod, node, unit);
		}
		throw new CompileException.ConstructorUndefined(name, node, unit);

	}

	private YoyooClassConstructor getYoyooConstructor(IType[] argTypes) {
		;
		YoyooEnvironment env = YoyooEnvironment.getDefault();
		cc: for (YoyooClassConstructor method : constructors.getSymbols()
				.values()) {
			Collection<FormalParameter> params = method.getParameters()
					.getSymbols().values();
			if (params.size() == argTypes.length) {
				int i = 0;
				for (FormalParameter param : params) {
					IType paramtype = param.getType();
					if (!paramtype.equalsTo(argTypes[i]) && !argTypes[i].isTypeOf(paramtype, env) && !paramtype.isTypeOf(argTypes[i], env) && !(argTypes[i] instanceof YoyooNull)) {
						continue cc;
					}
					i++;
				}
				return method;
			}
		}
		if ((
				(constructors.getSymbols().size() == 0) ||
				(constructors.getSymbols().size()==1 &&
				constructors.getSymbols().values().iterator().next().isStaticMethod())
				) && argTypes.length == 0) {
			return new YoyooClassConstructor(this, null);
		} else {
			return null;
		}
	}

	protected Method getNativeMethodAsConstructor(IType[] argTypes) {
		YoyooEnvironment env = YoyooEnvironment.getDefault();
		Class<?>[] argClasses = new Class[argTypes.length];
		boolean[] isArray = new boolean[argTypes.length]; 
		int i = 0;
		for (IType type : argTypes) {
			argClasses[i] = type.map2JavaClass(env);
			isArray[i] = type.isArray();
			i++;
		}
		Method method = ParserUtil.findNativeMethodAsConstructor(argClasses, isArray, typeClass);
		return method;

	}

	public SymbolTable<YoyooClassConstructor> getConstructors() {
		return constructors;
	}

	public Class<? extends IYoyooObject>  getTypeClass() {
		return typeClass;
	}

//	private Method findNativeMethod(String name, Class<?>[] paramCls, boolean[] isArray ) {
//		if(typeClass!=null)
//		{
//			Method[] methods = typeClass.getMethods();
//			if (methods != null && methods.length > 0) {
//				bb: for (Method method : methods) {
//					if (method.getName().equals(name)
//							&& method.getParameterTypes().length == paramCls.length) {
//						Class<?> paramClz[] = method.getParameterTypes();
//						cc: for (int i = 0; i < paramClz.length; i++) {
//							
//							if ((paramClz[i]== paramCls[i]
//									&& !isArray[i]) ||
//									(paramClz[i] == YoyooArray.class && isArray[i])) {
//								continue cc;
//							} 
//							else {
//								Class<?> tmpClass = paramCls[i].getSuperclass();
//								while (tmpClass != null) {
//									if (tmpClass == paramClz[i]) {
//										continue cc;
//									} else {
//										tmpClass = tmpClass.getSuperclass();
//									}
//								}
//								continue bb;
//							}
//	
//						}
//						return method;
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	
//	private Method findNativeMethodAsConstructor(Class<?>[] paramCls, boolean[] isArray ) {
//		return findNativeMethod("yoyooConstructor", paramCls, isArray);
//		
//	}
	/*
	protected Constructor findNativeConstructor(Class[] paramCls, boolean[] isArray ) {
		if(typeClass!=null)
		{
			Constructor[] methods = typeClass.getConstructors();
			if (methods != null && methods.length > 0) {
				bb: for (Constructor method : methods) {
					if (method.getParameterTypes().length == paramCls.length) {
						Class paramClz[] = method.getParameterTypes();
						cc: for (int i = 0; i < paramClz.length; i++) {
							
							if ((paramClz[i]== paramCls[i]
									&& !isArray[i]) ||
									(paramClz[i] == YoyooArray.class && isArray[i])) {
								continue cc;
							} 
							else {
								Class tmpClass = paramCls[i].getSuperclass();
								while (tmpClass != null) {
									if (tmpClass == paramClz[i]) {
										continue cc;
									} else {
										tmpClass = tmpClass.getSuperclass();
									}
								}
								continue bb;
							}
	
						}
						return method;
					}
				}
			}
		}
		return null;
		
	}
*/


	public void typeCheck() throws CompileException {
		if(typeChecked) {
			return;
		} else {
			typeChecked = true;
		}
		for (YoyooField field : fields.getSymbols().values()) {
			field.typeCheck();
			
		}
		
		for (YoyooClassConstructor constructor : constructors.getSymbols()
				.values()) {
			constructor.check();
		}
		if(this.staticClassConstructor!=null)
			this.staticClassConstructor.check();
		for (YoyooMethod method : methods.getSymbols().values()) {
			
			method.check();
		}
		
		
		
	}
	
	public void classCheck() throws CompileException {
		if(classChecked) {
			return;
		} else {
			classChecked = true;
		}
		
		if(this.extendsReferenceType!=null && parentYClass==null) {

			parentYClass = (YoyooTypeDefineClass)extendsReferenceType.map2YoyooClass(compilationUnit.getEnv());
			if(parentYClass==null) {
				throw new CompileException.Undefined(extendsReferenceType.getFullName(), node, compilationUnit);
			}
			parentYClass.classCheck();
			copyParentMethods(methods);
			copyParentFields(fields, this.ambiguousFields);
			
			boolean constructorFound = false;
			boolean defaultConstructorFound = false;
			Collection<YoyooClassConstructor> constructors = this.constructors.getSymbols().values();
			Collection<YoyooClassConstructor> pconstructors = parentYClass.constructors.getSymbols().values();
			if(pconstructors.isEmpty()) {
				defaultConstructorFound = true;
			}
			for(YoyooClassConstructor constructor : constructors) {
				for(YoyooClassConstructor pconstructor : pconstructors) {
					if(constructor.getParametersString().equals(pconstructor.getParametersString())) {
						constructorFound = true;
					}
					if(pconstructor.parameters.isEmpty()) {
						defaultConstructorFound = true;
					}
				}
			}
//			if(!constructorFound && !defaultConstructorFound) {
//				throw new CompileException.SuperConstructorUndefined(parentYClass.name, node, compilationUnit);					
//			}			
		}
		if(this.imlpmentsReferenceNames!=null) {
			copyParentMethods(methods);
			copyParentFields(fields, this.ambiguousFields);
		}
		if(!isInterface && !isAbstract) {
			for(YoyooMethod method : this.getMethods().getSymbols().values()) {
				if(method.isAbstract()) {
					throw new CompileException.MethodNotImplemented(method.name, node, compilationUnit);				
				}
			}
		} else {
			for(YoyooMethod method : this.getMethods().getSymbols().values()) {		
				if(this==method.getUnit() ||this.isDescendant(method.getUnit())) {
					if(method.modifier != Modifier.PUBLIC && method.modifier != Modifier.PROTECTED) {
						throw new CompileException.MethodIsNotPublic(method.name, method.node, method.unit.getCompilationUnit());
					}
				}
				else if(method.modifier != Modifier.PUBLIC) {
					throw new CompileException.MethodIsNotPublic(method.name, method.node, method.unit.getCompilationUnit());
				}
				
			}
			if(isInterface) {
				for(YoyooField field : this.getFields().getSymbols().values()) {	
					field.setStaticField();
					if(field.modifier != Modifier.PUBLIC) {
						throw new CompileException.FieldIsNotPublic(field.name, field.node, field.unit.getCompilationUnit());
					}
					
				}
			}
			
		}
		

	}

	public boolean isPrimitive() {
		return false;
	}
	
	public YoyooTypeDefine instance(RuntimeContext ctx) {
		Class<?> cls = null;
		try {
			cls = YoyooEnvironment
			.getDefault().getTypeLoader().loadClass(YoyooTypeDefineLoader.formatClassName(typeClass));
			//YoyooTypeDefine typeInstance = (YoyooTypeDefine)cls.newInstance();
			YoyooTypeDefine typeInstance = (YoyooTypeDefine)ParserUtil.newInstance(cls);
			typeInstance.setUnit(this);
			if(staticClassConstructor!=null) {
				staticClassConstructor.execute(ctx, typeInstance);
			}
			
			return typeInstance;
//		}catch (InstantiationException e) {
//			//System.err.println(cls.getCanonicalName());
//			//System.err.println(this.typeClass.getSimpleName());
//			e.printStackTrace();
		} 
		catch (YoyooRTException e) {

			e.printStackTrace();
		} //catch (ClassNotFoundException e) {
		//	e.printStackTrace();
		//} 
//		catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
		return null;

	}
	/*
	public YoyooObject createJavaInstance(RuntimeContext ctx) throws YoyooRTException {
		
			YoyooTypeDefine typeInstance = null;
			ctx.pushStackFrame(false);
			try {
				typeInstance = (YoyooTypeDefine) instance(ctx);
				ctx.putStaticInstance(this.getFullName(), typeInstance);
				RuntimeValueAtom atom = new RuntimeValueAtom(typeInstance, node, compilationUnit);
				ctx.registerThis(atom, node, compilationUnit);
				Collection<YoyooField> fieldsDecl = getFields().getSymbols()
						.values();
				for (YoyooField field : fieldsDecl) {
					Operator opt = field.convert2Operator(field.getNode());
	//				System.out.println(this.name + " " + field.name);
					ctx.evaluateOperator(opt);
				}
				typeInstance.iniFields();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			ctx.popStackFrame();
			return typeInstance;
		
	}
	*/
	public IRuntimeValueAtom createInstance(RuntimeContext ctx)
									throws YoyooRTException {
		IRuntimeValueAtom atom = null;
		ctx.pushStackFrame(false);
		try {

			YoyooTypeDefine typeInstance = (YoyooTypeDefine) instance(ctx);
			atom = new RuntimeValueAtom(typeInstance, node, compilationUnit);
			
			ctx.registerThis(atom, node, compilationUnit);
			Collection<YoyooField> fieldsDecl = getFields().getSymbols()
					.values();
			for (YoyooField field : fieldsDecl) {
				//Operator opt = 
				//System.out.println(this.name + " " + field.name+" "+field.isStaticField()+" "+field.isStaticAndInitialized());
				
				if(!initedStaticField.contains(field.name)) {
					if(field.isStaticField())
						initedStaticField.putSymbol(field.name, field, field.node, field.compilationUnit);
					field.eval(ctx);					
				}
				
				//ctx.evaluateOperator(opt);
			}
			typeInstance.iniFields();
			
			
			
			
		//	ctx.putObject(typeInstance, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ctx.popStackFrame();
		return atom;
	}
	public YoyooObject createStaticInstance(RuntimeContext ctx) throws YoyooRTException {
		YoyooObject obj = ctx.getStaticInstance(this.getFullName());
		
		if(obj!=null) {
			return obj;
		}
		else {
			YoyooTypeDefine typeInstance = null;
			ctx.pushStackFrame(false);
			try {
				typeInstance = (YoyooTypeDefine) instance(ctx);
				ctx.putStaticInstance(this.getFullName(), typeInstance);
				RuntimeValueAtom atom = new RuntimeValueAtom(typeInstance, node, compilationUnit);
				ctx.registerThis(atom, node, compilationUnit);
//				Collection<YoyooField> fieldsDecl = getFields().getSymbols()
//						.values();
//				for (YoyooField field : fieldsDecl) {
//					Operator opt = field.convert2Operator(field.getNode());
//					ctx.evaluateOperator(opt);
//				}
//				typeInstance.iniFields();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			ctx.popStackFrame();
			return typeInstance;
		}
	}
	 

	public void setStaticClassConstructor(
			YoyooClassConstructor staticClassConstructor) {
		this.staticClassConstructor = staticClassConstructor;
	}
	
	
	public ReferenceNameType getExtendsReferenceType() {
		return extendsReferenceType;
	}
	
	public boolean isAmbiguousField(String name) {
		return this.ambiguousFields.contains(name);
	}

	public void setStaticFieldsValue(Map<String, YoyooObject> staticFieldsValue) {
		this.staticFieldsValue = staticFieldsValue;
	}

	public Map<String, YoyooObject> getStaticFieldsValue() {
		return staticFieldsValue;
	}
	
	public void setStaticFieldsValue(String name, YoyooObject value) {
		this.staticFieldsValue.put(name, value);
	}

}
