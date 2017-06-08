package org.yoyoo.core.engine.compile.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.Token;
import org.yoyoo.core.engine.parser.YoyooClassOrInterfaceName;
import org.yoyoo.core.engine.parser.YoyooClassOrInterfaceTypeArguments;
import org.yoyoo.core.engine.parser.YoyooThrowName;
import org.yoyoo.core.engine.parser.YoyooTypeArgument;
import org.yoyoo.core.engine.parser.YoyooTypeArgumentType;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;


public class ReferenceNameType extends ArrayBasedType {
	public ReferenceNameType(CompilationUnit unit, SimpleNode node, YoyooTypeDefineClass ownerClass) {
		super(unit, node);
		this.ownerClass = ownerClass;
		
	}

	private String name;

	private String pkgname;
	
	private boolean staticReference = false;
	
	private boolean typeParameter = false;
	
	private int typeParameterIndex = -1;

	
	private YoyooTypeDefineClass ownerClass;


	private transient List<String> nameList = new ArrayList<String>(1);
	
	private List<IType> typeArguments;
	
	//private List<String> nameList;
	


	public void setTypeArguments(List<IType> typeArguments) {
		this.typeArguments = typeArguments;
	}



	public IType typeClone(SimpleNode node)  {
		ReferenceNameType rtype = new ReferenceNameType(unit, node, this.ownerClass);
		rtype.array = this.array;
		rtype.arrayDim  =this.arrayDim;
		rtype.name = this.name;
		rtype.pkgname = this.pkgname;
		rtype.staticReference = this.staticReference;
		rtype.typeParameter = this.typeParameter;
		rtype.typeParameterIndex = this.typeParameterIndex;
		
		if(this.typeArguments!=null) {
			rtype.typeArguments = new ArrayList<IType>();
			rtype.typeArguments.addAll(typeArguments);
		}
		
		return rtype;
	}



	@Override
	public Object visit(YoyooThrowName node, Object data) {
		visitNameNode(node);
		return null;
	}
	
//	@Override
//	public Object visit(YoyooExtendsName node, Object data) {
//		visitNameNode(node);
//		return null;
//	}
	
	private void visitNameNode(SimpleNode node) {
		Token token1 = node.first_token;
		Token token2 = node.last_token;
		if (token1 != token2) {
			StringBuffer sb = new StringBuffer();
			while (token1 != token2) {
				sb.append(".");
				sb.append(token1.image);
				token1 = token1.next;
			}
			this.pkgname = sb.toString();
			name = token2.image;
			
		} else {
			name = token2.image;
			
		}
				
	}

	public int getTypeParameterIndex() {
		return typeParameterIndex;
	}


	public List<IType> getTypeArguments() {
		return typeArguments;
	}



	public boolean isPrimitiveType() {
		return false;
	}

	@Override
	public Object visit(YoyooClassOrInterfaceName node, Object data) {
		nameList.add(node.first_token.image);
		if(name!=null) {
			if(this.pkgname==null)
				this.pkgname = "";
			else if(this.pkgname.length()>0)
				this.pkgname += ".";
			this.pkgname += name;
		}		
		name = nameList.get(nameList.size()-1);
		return null;
	}



	@Override
	public Object visit(YoyooClassOrInterfaceTypeArguments node, Object data) {
		int size = node.jjtGetNumChildren();
		if(typeArguments==null)
			typeArguments = new ArrayList<IType>(size);
		for(int i=0;i<size;i++) {
			YoyooTypeArgument argNode = (YoyooTypeArgument)node.jjtGetChild(i);
			YoyooTypeArgumentType typeNode =  (YoyooTypeArgumentType)argNode.jjtGetChild(0);
			TypeVisitor v = new TypeVisitor(unit, node);
			typeNode.childrenAccept(v, data);
			typeArguments.add(v.getType());
		}
		return null;
	}

	
	
	public YoyooTypeDefineClass getOwnerClass() {
		return ownerClass;
	}

	public void setOwnerClass(YoyooTypeDefineClass ownerClass) {
		this.ownerClass = ownerClass;
	}

	public String getName() {
		
		return name;
	}

	public String getFullName() {
		return (pkgname == null ? name : pkgname + "." + name);
	}
	
	public String getFullDisplayName() {
		return (pkgname == null ? name : pkgname + "." + name) + this.getArrayDimStr() + this.getTypeArgumentsStr();
	}

	public Class<? extends IYoyooObject>  map2JavaClass(YoyooEnvironment env) {
		return env.getDeclaration(this.pkgname, this.name).getTypeClass();
	}

	public boolean equalsTo(IType type) {
		if(!type.isPrimitiveType() && ((ReferenceNameType) type).isTypeParameter()
				&& !this.isPrimitiveType() && ((ReferenceNameType) this).isTypeParameter()
				&& ((ReferenceNameType) this).getName().equals(((ReferenceNameType) type).getName())) {
			return true;
		} else if(!type.isPrimitiveType() && !((ReferenceNameType) type).isTypeParameter()
				&& ((ReferenceNameType) type).getPkgname().equals(
						this.getPkgname())
				&& ((ReferenceNameType) type).getName().equals(this.getName())
				&& super.equalsTo(type)) {
			return true;
		} else {
			return  false;
		}
			
	}

	public boolean isYoyooClass(YoyooTypeDefineClass ycls) {
		return ycls.getFullName().equals(this.pkgname+"."+this.name);
	}
	
	public boolean isYoyooInterface(YoyooEnvironment env) {
		YoyooClass yoyooClass = this.map2YoyooClass(env);
		if(yoyooClass instanceof YoyooTypeDefineClass)
			return ((YoyooTypeDefineClass)yoyooClass).isInterface();
		else
			return false;
	}
	
	public YoyooClass map2YoyooClass(YoyooEnvironment env) {
		return env.getDeclaration(this.pkgname, this.name);
	}
	
	public boolean isStaticReference() {
		return staticReference;
	}

	public void setStaticReference(boolean staticReference) {
		this.staticReference = staticReference;
	}

	public boolean isTypeParameter() {
		return typeParameter;
	}

	public void setTypeParameter(boolean typeParameter, int typeParameterIndex) {
		this.typeParameter = typeParameter;
		this.typeParameterIndex = typeParameterIndex;
	}

	public String getPkgname() {
		return pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	public boolean conatinPackage() {
		return this.pkgname != null;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public static ReferenceNameType createThisRefType(YoyooTypeDefineClass typeCls, SimpleNode node)
	{
		ReferenceNameType refType = new ReferenceNameType(typeCls.getCompilationUnit(), node, typeCls);
		refType.setName(typeCls.getName());
		refType.setPkgname(typeCls.getCompilationUnit().getPackageName());
		return refType;
	}
	
	public static ReferenceNameType createConstructorReturnType(YoyooClassConstructor constructor)
	{
		YoyooTypeDefineClass typeCls = constructor.getUnit();
		ReferenceNameType refType = new ReferenceNameType(typeCls.getCompilationUnit(), constructor.getNode(), typeCls);
		refType.setName(typeCls.getName());
		refType.setPkgname(typeCls.getCompilationUnit().getPackageName());
		return refType;
	}


	public boolean isObject(YoyooEnvironment env) {
		YoyooTypeDefineClass ycls = (YoyooTypeDefineClass)this.map2YoyooClass(env);
		return  ycls.getFullName().equals("yoyoo.lang.Object");
	}

	@Override
	public boolean isTypeOf(IType type, YoyooEnvironment env) {
		YoyooTypeDefineClass ycls = (YoyooTypeDefineClass)this.map2YoyooClass(env);			
		if(ycls.isTypeof(type)) {
			List<IType> typeArguments1 = ((ReferenceNameType)type).typeArguments;
			List<IType> typeArguments2 = this.typeArguments;
			if(typeArguments1!=null && typeArguments2!=null && typeArguments1.size() == typeArguments2.size() ) {
				int size = typeArguments1.size();
				for(int i=0;i<size;i++) {
					IType compareType1 = typeArguments1.get(i);
					IType compareType2 = typeArguments2.get(i);
					if(compareType1 instanceof ReferenceNameType && ((ReferenceNameType)compareType1).isTypeParameter()) {
						continue;
					}
					else if( !compareType1.equalsTo(compareType2)) {
						return false;
					}
				}
				return true;
			} else if(typeArguments1==null && typeArguments2==null) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
			
	}



	@Override
	public String getTypeArgumentsStr() {
		StringBuffer sb = new StringBuffer();
		if(this.typeArguments!=null) {
			sb.append("<");
			int j=0;
			for(Iterator<IType> i = this.typeArguments.iterator();i.hasNext();) {
				if(j>0) {
					sb.append(",");	
				}
				sb.append(i.next().getFullDisplayName());				
				j++;
			}
			sb.append(">");
		}
		return sb.toString();
	}

	
	
	
}
