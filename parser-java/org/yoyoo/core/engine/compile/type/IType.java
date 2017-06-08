package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;


public interface IType{

	public Class<? extends IYoyooObject> map2JavaClass(YoyooEnvironment env);
	
	public YoyooClass map2YoyooClass(YoyooEnvironment env);

	public boolean isPrimitiveType();

	public boolean equalsTo(IType type);

	public boolean isTypeOf(IType type, YoyooEnvironment env);
	
	public String getName();

	public boolean isArray();

	public int getArrayDim();
	
	public void setArray(boolean array);
	
	public void setArrayDim(int dim);
	
	public String getArrayDimStr();
	
	public String getTypeArgumentsStr();
	
	public String getFullDisplayName();
	
	public IType typeClone(SimpleNode node);

	public SimpleNode getNode();
}
