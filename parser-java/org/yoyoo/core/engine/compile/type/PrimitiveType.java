package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooPrimitiveTypeName;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;



public class PrimitiveType extends ArrayBasedType {


	public IType typeClone(SimpleNode node)  {
		PrimitiveType ptype = new PrimitiveType(unit, node);
		ptype.array = this.array;
		ptype.arrayDim = this.arrayDim;
		ptype.type = this.type;
		return ptype;
	}

	public static boolean isBoolean(IType type)
	{
		return TYPE.YoyooBoolean.equalsTo(type);
	}
	
	public final static class YoyooBoolean extends PrimitiveType
	{

		public YoyooBoolean(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooBoolean;
		}
		
	}
	
	public static boolean isString(IType type)
	{
		return TYPE.YoyooString.equalsTo(type);
	}
	
	public final static class YoyooString extends PrimitiveType
	{

		public YoyooString(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooString;
		}
		
	}
	
	public static boolean isInteger(IType type)
	{
		return TYPE.YoyooInteger.equalsTo(type);
	}
	
	public final static class YoyooInteger extends PrimitiveType
	{

		public YoyooInteger(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooInteger;
		}
		
	}
	
	public static boolean isShort(IType type)
	{
		return TYPE.YoyooShort.equalsTo(type);
	}
	
	public final static class YoyooShort extends PrimitiveType
	{

		public YoyooShort(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooShort;
		}
		
	}
	
	public static boolean isFloat(IType type)
	{
		return TYPE.YoyooFloat.equalsTo(type);
	}
	
	public final static class YoyooFloat extends PrimitiveType
	{

		public YoyooFloat(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooFloat;
		}
		
	}
	
	public static boolean isLong(IType type)
	{
		return TYPE.YoyooLong.equalsTo(type);
	}
	
	public final static class YoyooLong extends PrimitiveType
	{

		public YoyooLong(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooLong;
		}
		
	}
	
	public static boolean isChar(IType type)
	{
		return TYPE.YoyooChar.equalsTo(type);
	}
	
	public final static class YoyooChar extends PrimitiveType
	{

		public YoyooChar(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooChar;
		}
		
	}
	
	public static boolean isDouble(IType type)
	{
		return TYPE.YoyooDouble.equalsTo(type);
	}
	
	public final static class YoyooDouble extends PrimitiveType
	{

		public YoyooDouble(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooDouble;
		}
		
	}
	
	public static boolean isVoid(IType type)
	{
		return TYPE.YoyooVoid.equalsTo(type);
	}
	
	
	public final static class YoyooVoid extends PrimitiveType
	{

		public YoyooVoid(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooVoid;
		}
		
	}
	
	public final static class YoyooNull extends PrimitiveType
	{

		public YoyooNull(CompilationUnit unit, SimpleNode node) {
			super(unit, node);
			super.type = TYPE.YoyooNull;
		}
		
	}
	
//	public static boolean isYoyooSpecialExpr(IType type)
//	{
//		return TYPE.YoyooSpecialExpr.equalsTo(type);
//	}
//	
//	
//	public final static class YoyooSpecialExpr extends PrimitiveType
//	{
//
//		
//
//		public YoyooSpecialExpr(CompilationUnit unit, SimpleNode node) {
//			super(unit, node);
//			super.type = TYPE.YoyooSpecialExpr;
//		}
//		
//	}
//	
//	public static boolean isYoyooObjectRef(IType type)
//	{
//		return TYPE.YoyooObjectRef.equalsTo(type);
//	}
//	
//	
//	public final static class YoyooObjectRef extends PrimitiveType
//	{
//		private IType valueType;
//		public YoyooObjectRef(CompilationUnit unit, SimpleNode node, IType valueType) {
//			super(unit, node);
//			super.type = TYPE.YoyooObjectRef;
//			this.valueType = valueType;
//		}
//		public IType getValueType() {
//			return valueType;
//		}
//		@Override
//		public String getName() {
//			// TODO Auto-generated method stub
//			return super.getName()+"<"+valueType.getName()+">";
//		}
//		
//		@Override
//		public Object visit(YoyooPrimitiveTypeName node, Object data) {
//			PrimitiveTypeVisitor v = new PrimitiveTypeVisitor(unit, node);
//			node.jjtAccept(v, data);
//			super.type = v.getType();
//			this.valueType = v.getValueType();
//			return null;
//		}
//	}
	
	


	@Override
	public Object visit(YoyooPrimitiveTypeName node, Object data) {
		PrimitiveTypeVisitor v = new PrimitiveTypeVisitor(unit, node);
		node.jjtAccept(v, data);
		this.type = v.getType();
		return null;
	}

	public static enum TYPE{
		YoyooBoolean("boolean"), YoyooString("string"), YoyooInteger("int"), YoyooShort(
				"short"), YoyooFloat("float"), YoyooLong("long"), YoyooChar("char"), YoyooDouble(
				"double"), YoyooVoid("void"), YoyooNull("null");
	
		private String typeName;
	
		
		private TYPE(String typeName) {
			this.typeName = typeName;
		}


		private boolean equalsTo(IType rightType) {
			return rightType.isPrimitiveType()
			&& typeName.equals(rightType.getName());
		}
	}

	
	private TYPE type;
	
	public PrimitiveType(CompilationUnit unit, SimpleNode node) {
		super(unit, node);

	}


	
	public boolean isPrimitiveType() {
		return true;
	}
	
	public boolean equalsTo(IType type) {
		return type.isPrimitiveType()
				&& this.getName().equals(type.getName()) 
				&& super.equalsTo(type);
	}
	
	public Class<? extends IYoyooObject> map2JavaClass(YoyooEnvironment env) {
		
		switch (type) {
		case YoyooBoolean:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean.class;
		case YoyooString:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooString.class;
		case YoyooInteger:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooInteger.class;
		case YoyooShort:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooShort.class;
		case YoyooFloat:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooFloat.class;
		case YoyooLong:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooLong.class;
		case YoyooChar:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooChar.class;
		case YoyooDouble:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooDouble.class;
		case YoyooNull:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooNull.class;
		default:
		case YoyooVoid:
			return org.yoyoo.core.engine.yoyoo.lang.YoyooVoid.class;	
		}
		
	}
	
	

	public String getName() {
		// TODO Auto-generated method stub
		return type.typeName;
	}

	public void setType(TYPE type) {
		this.type = type;
	}





	public YoyooClass map2YoyooClass(YoyooEnvironment env) {
		switch (type) {
		case YoyooBoolean:
			return YoyooPrimitiveClass.YoyooBoolean;
		case YoyooString:
			return YoyooPrimitiveClass.YoyooString;
		case YoyooInteger:
			return YoyooPrimitiveClass.YoyooInteger;
		case YoyooShort:
			return YoyooPrimitiveClass.YoyooShort;
		case YoyooFloat:
			return YoyooPrimitiveClass.YoyooFloat;
		case YoyooLong:
			return YoyooPrimitiveClass.YoyooLong;
		case YoyooChar:
			return YoyooPrimitiveClass.YoyooChar;
		case YoyooDouble:
			return YoyooPrimitiveClass.YoyooDouble;
		case YoyooNull:
			return YoyooPrimitiveClass.YoyooNull;
	
		}
		return null;
	}

	@Override
	public boolean isTypeOf(IType type, YoyooEnvironment env) {
		return this.equalsTo(type);
	}

	@Override
	public String getTypeArgumentsStr() {
		return "";
	}

	@Override
	public String getFullDisplayName() {
		
		return this.getName();
	}

}
