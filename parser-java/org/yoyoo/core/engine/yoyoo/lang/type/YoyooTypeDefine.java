package org.yoyoo.core.engine.yoyoo.lang.type;


import java.util.LinkedHashMap;
import java.util.Map;

import org.yoyoo.core.engine.compile.ParserUtil;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooSpecialExpression;


public class YoyooTypeDefine extends YoyooObject {

	protected YoyooTypeDefineClass unit;

	protected Map<String, YoyooObject> fieldsValue = new LinkedHashMap<String, YoyooObject>();
	


//	private List<Method> yoyooConstrutorMethods;
	
	public YoyooTypeDefineClass getUnit() {
		return unit;
	}
	
	public YoyooClass getYoyooClass() {
		return unit;
	}

	public void setUnit(YoyooTypeDefineClass unit) {
		this.unit = unit;	}

	public void setFieldValue(String name, YoyooObject val) {
		fieldsValue.put(name, val);		
	}

	public YoyooObject getFieldValue(String name) {		
		return fieldsValue.get(name);		
	}

	public boolean containsField(String name) {
		return fieldsValue.containsKey(name);
	}
	
	public void iniFields(){}
	
	public void evalExpr(YoyooSpecialExpression expr, YoyooObject params, YoyooRef<YoyooObject> returnRef) throws YoyooRTException {
		expr.execute(params, returnRef, RuntimeContext.getCurrentContext());	
	}
	
	public void evalExpr(YoyooSpecialExpression expr) throws YoyooRTException {
		expr.execute(RuntimeContext.getCurrentContext());	
	}

	@Override
	public Object cloneAtom() throws YoyooRTException {
//		YoyooTypeDefine newObj;
//		try {
//			newObj = (YoyooTypeDefine)this.getClass().newInstance();
		YoyooTypeDefine newObj = (YoyooTypeDefine) ParserUtil.newInstance(this.getClass());
//		} catch (InstantiationException e) {
//			throw new CloneNotSupportedException(e.getMessage());
//		} catch (IllegalAccessException e) {
//			throw new CloneNotSupportedException(e.getMessage());
//		}
		//YoyooTypeDefine newObj = new YoyooTypeDefine();
		newObj.setInstanceName(this.getInstanceName());
		newObj.fieldsValue = fieldsValue;
		newObj.unit = unit;
		return newObj;
	}
	
	/*
	protected Object clone(YoyooTypeDefine newObj) throws CloneNotSupportedException {
		newObj.fieldsValue = fieldsValue;
		newObj.unit = unit;
		return newObj;
	}
	*/
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(unit.getName() + "{");
		int i = 0;
		for(String key : fieldsValue.keySet()){
			if(i > 0)
				sb.append(", ");
			i++;
			
			YoyooObject v = fieldsValue.get(key);
			sb.append(key+" = "+v.toString());							
		}
		sb.append("}");
		return sb.toString();
	}
	
	
	public boolean compareUnit(YoyooTypeDefine yoyooTypeDefine) {
		return this.unit!=null && yoyooTypeDefine.unit!=null && this.unit == yoyooTypeDefine.unit; 
	}
//	protected void addYoyooConstrutorMethod(Method m)
//	{
//		if(yoyooConstrutorMethods==null)
//			yoyooConstrutorMethods = new ArrayList<Method>();
//		yoyooConstrutorMethods.add(m);
//	}

//	@Override
//	public IYoyooObject newInstance() {
//		return new YoyooTypeDefine();
//	}

}
