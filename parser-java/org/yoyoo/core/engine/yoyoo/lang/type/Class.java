package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class Class extends YoyooTypeDefine{
	public void print(YoyooObject obj) {
		if (obj instanceof YoyooString)
			System.out.print(((YoyooString) obj).getVal());
		else if (obj instanceof YoyooBoolean)
			System.out.print(((YoyooBoolean) obj).getVal());
		else if (obj instanceof YoyooInteger)
			System.out.print(((YoyooInteger) obj).getVal().intValue());
		else if (obj instanceof YoyooLong)
			System.out.print(((YoyooLong) obj).getVal());
		else if (obj instanceof YoyooShort)
			System.out.print(((YoyooShort) obj).getVal());
		else if (obj instanceof YoyooChar)
			System.out.print(((YoyooChar) obj).getVal());
		else if (obj instanceof YoyooDouble)
			System.out.print(((YoyooDouble) obj).getVal());
		else if (obj instanceof YoyooFloat)
			System.out.print(((YoyooFloat) obj).getVal().floatValue());
		else if (obj instanceof YoyooNull)
			System.out.print(((YoyooNull) obj).getVal());
		else 
			System.out.print(obj.toString());
		
	}

	public void println(YoyooObject obj) {
		if (obj instanceof YoyooString)
			System.out.println(((YoyooString) obj).getVal());
		else if (obj instanceof YoyooBoolean)
			System.out.println(((YoyooBoolean) obj).getVal());
		else if (obj instanceof YoyooInteger)
			System.out.println(((YoyooInteger) obj).getVal().intValue());
		else if (obj instanceof YoyooLong)
			System.out.println(((YoyooLong) obj).getVal());
		else if (obj instanceof YoyooShort)
			System.out.println(((YoyooShort) obj).getVal());
		else if (obj instanceof YoyooChar)
			System.out.println(((YoyooChar) obj).getVal());
		else if (obj instanceof YoyooDouble)
			System.out.println(((YoyooDouble) obj).getVal());
		else if (obj instanceof YoyooFloat)
			System.out.println(((YoyooFloat) obj).getVal().floatValue());
		else if (obj instanceof YoyooNull)
			System.out.println(((YoyooNull) obj).getVal());
		else 
			System.out.println(obj.toString());
		
	}
	
	
	public boolean isMyInstance(YoyooObject obj, YoyooBoolean b) {
		boolean bool = ((YoyooTypeDefineClass)obj.getYoyooClass()).getFullName().equals(((YoyooTypeDefineClass)this.getYoyooClass()).getFullName());
		b.setVal(bool);
		return bool;
	}
	
	public void getClsName(YoyooObject obj, YoyooRef<YoyooString> tar) {
		tar.setValue(new YoyooString(obj.getYoyooClass().getName()));
	}
	
	public void getPkgName(YoyooObject obj, YoyooRef<YoyooString> tar) {
		YoyooClass yoyooClass = obj.getYoyooClass();
		if(yoyooClass instanceof YoyooTypeDefineClass) {
			tar.setValue(new YoyooString(((YoyooTypeDefineClass)yoyooClass).getCompilationUnit().getPackageName()));
		} 
		
	}
	public void getFullClassName(YoyooObject obj, YoyooRef<YoyooString> tar) {
		YoyooClass yoyooClass = obj.getYoyooClass();
		if(yoyooClass instanceof YoyooTypeDefineClass) {
			
			tar.setValue(new YoyooString(((YoyooTypeDefineClass)yoyooClass).getCompilationUnit().getPackageName() + "." + obj.getYoyooClass().getName()));
		} 
		
	}
	
}
