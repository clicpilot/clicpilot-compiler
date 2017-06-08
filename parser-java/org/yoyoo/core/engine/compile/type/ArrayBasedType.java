package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooArrayDecl;


public abstract class ArrayBasedType extends AbstractVisitor implements IType {


	
	
	
	@Override
	public Object visit(YoyooArrayDecl node, Object data) {
		arrayDim++;
		this.array = true;
		return super.visit(node, data);
	}

	

	
	
	protected boolean array;
	
	protected int arrayDim;

	
	public ArrayBasedType(CompilationUnit unit, SimpleNode node) {
		super(unit, node);
		arrayDim = 0;
		array = false;
	}

	public boolean isArray() {
		return array;
	}

	
	public int getArrayDim() {
		return arrayDim;
	}

	
	public boolean isPrimitiveType() {
		return true;
	}
	
	public boolean equalsTo(IType type) {
		return this.array == type.isArray()
				&& this.arrayDim == type.getArrayDim();
	}
	
	
	public void setArray(boolean array) {
		this.array = array;
		
	}

	public void setArrayDim(int dim) {
		this.arrayDim = dim;
		
	}

	public String getArrayDimStr() {
		String arrayDim1 = new String();
		if(isArray())
		{
			for(int i=0;i<getArrayDim();i++)
				arrayDim1+="[]";
		}
		return arrayDim1;
	}

	public SimpleNode getNode() {
		// TODO Auto-generated method stub
		return this.node;
	}

	

}
