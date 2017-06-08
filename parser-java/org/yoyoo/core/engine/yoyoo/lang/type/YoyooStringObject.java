package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class YoyooStringObject extends YoyooTypeDefine {	
	
	private String val; 
	
	public void setString(YoyooString str) {
		val = str.getVal();
	}

	@Override
	public boolean equals(Object arg0) {
		if(val!=null && arg0 instanceof YoyooStringObject)
			return val.equals(((YoyooStringObject)arg0).val);
		else
			return false;
	}

	@Override
	public int hashCode() {
		if(val!=null)
			return val.hashCode();
		else
			return super.hashCode();
	}
	
	public void firstUpper(YoyooString src, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		if(str.length()>0)
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		tar.setValue(new YoyooString(str));
	}
	
	public void firstLower(YoyooString src, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		if(str.length()>0)
			str = str.substring(0,1).toLowerCase() + str.substring(1);
		tar.setValue(new YoyooString(str));
	}
	
	public void upper(YoyooString src, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooString(str.toUpperCase()));
	}
	
	public void lower(YoyooString src, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooString(str.toLowerCase()));
	}
	
	public void quote(YoyooString src, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooString("\""+str+"\""));
	}
	
	public void length(YoyooString src, YoyooRef<YoyooInteger> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooInteger(str.length()));
	}
	
	public void isNull(YoyooString src, YoyooRef<YoyooBoolean> tar) {
		boolean bool = src==null || src.getVal()==null;
		tar.setValue(new YoyooBoolean(bool));
	}
	
	public void len(YoyooString src, YoyooRef<YoyooInteger> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooInteger(str.length()));
	}
	
	public void substring(YoyooString src, YoyooInteger offset, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooString("\""+str.substring(offset.getVal().intValue())+"\""));
	}
	
	public void substring(YoyooString src, YoyooInteger offset, YoyooInteger len, YoyooRef<YoyooString> tar) {
		String str = src.getVal();
		tar.setValue(new YoyooString("\""+str.substring(offset.getVal().intValue(), offset.getVal().intValue()+len.getVal().intValue())+"\""));
	}
	
}
