package org.yoyoo.core.engine.compile;

import java.lang.reflect.Method;

import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;


public class ParserUtil {

	public static Object newInstance(Class<?> cls) {
		try {
			return  cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Method findNativeMethod(String name, Class<?>[] paramCls, boolean[] isArray, Class<? extends IYoyooObject> typeClass ) {
		if(typeClass!=null)
		{
			Method[] methods = typeClass.getMethods();
			if (methods != null && methods.length > 0) {
				bb: for (Method method : methods) {
					if (method.getName().equals(name)
							&& method.getParameterTypes().length == paramCls.length) {
						Class<?> paramClz[] = method.getParameterTypes();
						cc: for (int i = 0; i < paramClz.length; i++) {
							
							if ((paramClz[i]== paramCls[i]
									&& !isArray[i]) ||
									(paramClz[i] == YoyooArray.class && isArray[i])) {
								continue cc;
							} 
							else {
								Class<?> tmpClass = paramCls[i].getSuperclass();
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
	
	public static Method findNativeMethodAsConstructor(Class<?>[] paramCls, boolean[] isArray, Class<? extends IYoyooObject> typeClass) {
		return findNativeMethod("yoyooConstructor", paramCls, isArray, typeClass);
		
	}
	
}
