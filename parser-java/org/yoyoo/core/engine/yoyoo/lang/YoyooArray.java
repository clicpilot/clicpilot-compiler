package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;


public class YoyooArray extends YoyooObject{
	

	private final static String LENGTH = "length";
	private YoyooObject[] data;
	private int length;

	
	@Override
	public Object cloneAtom() throws YoyooRTException {
		/*
		YoyooArray array = new YoyooArray(length);
		for(int i=0; i<length; i++){
			try {
				YoyooObject v = getData(i);
				array.setData(i, v);								
			} catch (YoyooRTException e) {
				throw new CloneNotSupportedException(e.getMessage());
			} catch (Exception e) {
				throw new CloneNotSupportedException(e.getMessage());
			}							
		}
		return array;
		*/
		return this;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooArray)
			if(((YoyooArray)(arg0)).getData()!=null) {
				if(((YoyooArray)(arg0)).getLength() == length){
					for(int i=0; i<length; i++){
						try {
							YoyooObject v1 = ((YoyooArray)(arg0)).getData(i, RuntimeContext.getCurrentContext());
							YoyooObject v2 = getData(i, RuntimeContext.getCurrentContext());
							if(v1 !=null && v2 != null && v1.equals(v2)) {
								continue;
							} else {
								return false;
							}								
						} catch (YoyooRTException e) {
							return false;
						}						
					}
					return true;
				}
				return false;
			}
			else 
				return false;
		else
			return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i=0; i<length; i++){
			if(i > 0)
				sb.append(",");
			try {
				YoyooObject v = getData(i, RuntimeContext.getCurrentContext());
				sb.append(v.toString());	
				
			} catch (YoyooRTException e) {
				sb.append(e.toString());
			} catch (Exception e) {
				sb.append(e.toString());
			}							
		}
		sb.append("]");
		return sb.toString();
	}
	
	public YoyooArray(int length) {
		super();
		this.length = length;
		data = new YoyooObject[length];

	}
	
	public void setData(int index, YoyooObject r, RuntimeContext ctx) throws YoyooRTException
	{
		if(index >= this.length)
		{
			throw new YoyooRTException.OutOfBound(index, this.length, null, null, ctx);
		}
		else
		{
			data[index] = r;
		}
	}
	
	public YoyooObject getData(int index, RuntimeContext ctx) throws YoyooRTException
	{
		if(index >= this.length || index<0)
		{
			throw new YoyooRTException.OutOfBound(index, this.length, null, null, ctx);
		}
		else
		{
			return data[index];
		}
	}

	public int getLength() {
		return length;
	}
	
	public static IType fieldCheck(String fieldName, SimpleNode node, CompilationUnit unit) throws CompileException
	{
		if(LENGTH.equals(fieldName))
		{
			return new PrimitiveType.YoyooInteger(unit, node);
		}
		else
		{
			throw new CompileException.Undefined(fieldName, node, unit);
		}
	}
	
	public static YoyooObject fieldValue(YoyooArray array, String fieldName, RuntimeContext ctx) throws YoyooRTException
	{
		if(LENGTH.equals(fieldName))
		{
			return new YoyooInteger(array.getLength());
		}
		else
		{
			throw new YoyooRTException.VariableCannotFound(fieldName, null, null, ctx);
		}
	}

	public YoyooObject[] getData() {
		return data;
	}




}
