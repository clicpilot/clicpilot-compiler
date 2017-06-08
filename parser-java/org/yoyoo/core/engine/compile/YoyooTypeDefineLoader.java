package org.yoyoo.core.engine.compile;


import java.util.HashMap;
import java.util.Map;

import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeContainer;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeEncoder;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeRunner;
import org.yoyoo.core.engine.yoyoo.lang.type.Function;
import org.yoyoo.core.engine.yoyoo.lang.type.Interface;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;
import org.yoyoo.core.engine.yoyoo.lang.type.Yclass;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooBooleanObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooCalendar;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooDoubleObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooException;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooFloatObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooIntegerObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooLongObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooShortObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooStringObject;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooArrayList;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooCollection;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooHashMap;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooHashSet;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooIterator;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooLinkedHashMap;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooList;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooListIterator;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooMap;
import org.yoyoo.core.engine.yoyoo.lang.util.collection.YoyooSet;



public class YoyooTypeDefineLoader {

	private Map<String, Class<? extends IYoyooObject>> classes = new HashMap<String, Class<? extends IYoyooObject>>();
	
	//private static final String YoyooTypeDefinePath = "yoyoo.lang.type.";

	public YoyooTypeDefineLoader() {
		
		
		String str = Function.class.getName();		
		str = formatClassName(str);
		classes.put(str, Function.class);		
		str = Interface.class.getName();
		str = formatClassName(str);
		classes.put(str, Interface.class);
		str = TestCase.class.getName();
		str = formatClassName(str);
		classes.put(str, TestCase.class);
		str = Yclass.class.getName();
		str = formatClassName(str);
		classes.put(str, Yclass.class);
		str = CodeRunner.class.getName();
		str = formatClassName(str);
		classes.put(str, CodeRunner.class);
		str = CodeContainer.class.getName();
		str = formatClassName(str);
		classes.put(str, CodeContainer.class);
		str = CodeEncoder.class.getName();
		str = formatClassName(str);
		classes.put(str, CodeEncoder.class);
		str = YoyooException.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooException.class);
		
		str = YoyooStringObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooStringObject.class);
		
		str = YoyooIntegerObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooIntegerObject.class);
		
		str = YoyooLongObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooLongObject.class);
		
		str = YoyooShortObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooShortObject.class);
		
		str = YoyooFloatObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooFloatObject.class);
		
		str = YoyooDoubleObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooDoubleObject.class);
		
		str = YoyooBooleanObject.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooBooleanObject.class);
		
		str = YoyooCalendar.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooCalendar.class);
		
		str = YoyooArrayList.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooArrayList.class);
		
		str = YoyooCollection.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooCollection.class);
		
		str = YoyooHashMap.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooHashMap.class);
		
		str = YoyooLinkedHashMap.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooLinkedHashMap.class);
		
		str = YoyooHashSet.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooHashSet.class);

		str = YoyooIterator.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooIterator.class);
		
		str = YoyooList.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooList.class);
		
		str = YoyooListIterator.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooListIterator.class);
		
		str = YoyooMap.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooMap.class);
		
		str = YoyooSet.class.getName();
		str = formatClassName(str);
		classes.put(str, YoyooSet.class);
		
		str = org.yoyoo.core.engine.yoyoo.lang.type.Class.class.getName();
		str = formatClassName(str);
		classes.put(str, org.yoyoo.core.engine.yoyoo.lang.type.Class.class);
		
		str = org.yoyoo.core.engine.yoyoo.lang.type.YoyooStringBuffer.class.getName();
		str = formatClassName(str);
		classes.put(str,  org.yoyoo.core.engine.yoyoo.lang.type.YoyooStringBuffer.class);
	}

	protected static String formatClassName(String str) {
		int i = str.lastIndexOf(".");
		str = str.substring(i+1,i+2).toLowerCase() + str.substring(i+2);
		return str;
	}
	
	public static String  formatClassName(Class<?> cls) {
		String str = cls.getName();
		int i = str.lastIndexOf(".");
		str = str.substring(i+1,i+2).toLowerCase() + str.substring(i+2);
		return str;
	}

	public Class<? extends IYoyooObject> loadClass(String className){// throws ClassNotFoundException {
		return findClass(className);
	}

	public Class<? extends IYoyooObject> findClass(String className) {
		// byte classByte[];
		
		Class<? extends IYoyooObject> result = null;
		className = formatClassName(className);
		result = classes.get(className);
		if (result != null) {
			return result;
		} else {
			return null;
		}
/*
		ClassLoader.getSystemClassLoader().getResource(".");
		try {

			Class<? extends YoyooTypeDefine> cls = null;
			if (!Compiler.containsPackageName(className)) {
				String trueClassName = className.substring(0, 1).toUpperCase()
						+ className.substring(1);
				cls = Class.forName(YoyooTypeDefinePath + trueClassName);
			} else {
				cls = Class.forName(className);
			}
			Class<?> superClass = cls.getSuperclass();
			boolean isYoyooTypeDefine = false;
			while (superClass != null) {
				if (superClass == YoyooTypeDefine.class) {
					result = cls;
					classes.put(className, result);
					isYoyooTypeDefine = true;
					break;
				} else {
					superClass = superClass.getSuperclass();
				}
			}

			if (!isYoyooTypeDefine) {
				return null;
			} else {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
*/		
	}

	// private byte[] loadClassData(String className) throws IOException {
	// DataInputStream dis = null;
	// FileInputStream fis = null;
	// try{
	// File f;
	// f = new File(className);
	// int size = (int) f.length();
	// byte buff[] = new byte[size];
	// fis = new FileInputStream(f);
	// dis = new DataInputStream(fis);
	// dis.readFully(buff);
	// dis.close();
	// return buff;
	// }
	// catch(IOException e)
	// {
	// throw e;
	// }
	// finally{
	// if(fis!=null)fis.close();
	// if(dis!=null)dis.close();
	// }
	// }

	

}
