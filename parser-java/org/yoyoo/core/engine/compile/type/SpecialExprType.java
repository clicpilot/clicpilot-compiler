package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooSpecialExprClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooSpecialExprTypeName;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;


public class SpecialExprType extends ArrayBasedType{

	private String exprTypeName;

	
	public SpecialExprType(CompilationUnit unit, SimpleNode node) {
		super(unit, node);
	}

	
	
	public boolean equalsTo(IType type) {
		return type instanceof SpecialExprType
		&& ((SpecialExprType)type).exprTypeName.equals(this.exprTypeName)
		&& super.equalsTo(type);
	}

	

	public String getName() {
		// TODO Auto-generated method stub
		return "Expr<"+exprTypeName+">";
	}

	
	
	public boolean isPrimitiveType() {
		// TODO Auto-generated method stub
		return false;
	}

	public Class<? extends IYoyooObject> map2JavaClass(YoyooEnvironment env) {
		return org.yoyoo.core.engine.yoyoo.lang.YoyooSpecialExpression.class;
	}

	public YoyooClass map2YoyooClass(YoyooEnvironment env) {
		// TODO Auto-generated method stub
		return new YoyooSpecialExprClass();
	}

	public IType typeClone(SimpleNode node) {
		SpecialExprType exprType = new SpecialExprType(unit, node);
		exprType.array = this.array;
		exprType.arrayDim = this.arrayDim;
		exprType.exprTypeName = this.exprTypeName;
		return exprType;
	}

	@Override
	public Object visit(YoyooSpecialExprTypeName node, Object data) {
		exprTypeName = node.first_token.image;
		return null;
	}

	
	public String getExprTypeName() {
		return exprTypeName;
	}

	public void setExprTypeName(String exprTypeName) {
		this.exprTypeName = exprTypeName;
	}



	@Override
	public boolean isTypeOf(IType type, YoyooEnvironment env) {
		return false;
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
