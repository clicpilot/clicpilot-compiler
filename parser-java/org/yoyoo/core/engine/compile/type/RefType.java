package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooRefClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooRefValueType;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;


public class RefType extends ArrayBasedType{

	private IType valueType;

	
	public RefType(CompilationUnit unit, SimpleNode node) {
		super(unit, node);
	}

	
	
	public boolean equalsTo(IType type) {
		
		return valueType.equalsTo(type)
		&& super.equalsTo(type);
	}

	

	public String getName() {
		
		return "Ref<"+valueType.getName()+">";
	}

	
	
	public boolean isPrimitiveType() {
		
		return false;
	}

	public Class<? extends IYoyooObject> map2JavaClass(YoyooEnvironment env) {
		return org.yoyoo.core.engine.yoyoo.lang.YoyooRef.class;
	}

	public YoyooClass map2YoyooClass(YoyooEnvironment env) {		
		return new YoyooRefClass(this);
	}

	public IType typeClone(SimpleNode node) {
		RefType exprType = new RefType(unit, node);
		exprType.array = this.array;
		exprType.arrayDim = this.arrayDim;
		exprType.valueType = this.valueType.typeClone(node);
		return exprType;
	}



	@Override
	public Object visit(YoyooRefValueType node, Object data) {
		TypeVisitor v = new TypeVisitor(unit, node);
		node.childrenAccept(v, data);
		valueType = v.getType();
		return null;
	}



	public IType getValueType() {
		return valueType;
	}



	@Override
	public boolean isTypeOf(IType type, YoyooEnvironment env) {
		return this.equalsTo(type) ;
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
