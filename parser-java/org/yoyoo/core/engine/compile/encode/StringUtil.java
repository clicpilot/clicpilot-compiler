package org.yoyoo.core.engine.compile.encode;

import java.util.List;

public class StringUtil {

	public final static String firstUpper(String src) {
		return src.substring(0, 1).toUpperCase() + src.substring(1);
	}
	
	public final static String firstLower(String src) {
		return src.substring(0,1).toLowerCase() + src.substring(1);
	}
	
	public final static String upper(String src) {
		return src.toUpperCase();
	}
	
	public final static String lower(String src) {
		return src.toLowerCase();
	}
	
	public final static boolean equals(String str1, String str2) {
		return str1!=null && str2!=null && str1.equals(str2);
	}
	
	public final static String append(String str1, String str2) {
		return str1+str2;
	}
	
	public final static String quote(String str1) {
		return "\""+str1+"\"";
	}
	
	public final static String join(List<String> strarray, String sep) {
		String res = new String();
		for(String str : strarray) {
			if(res.length()>0) {
				res+=sep;
			}
			res+=str;
		}
		return res;
	}
	
	public final static boolean isNull(String str) {
		return str==null;
	}
	
	public final static String quoteNotNull(String str) {
		return str==null?null:"\""+str+"\"";
	}
	
	public final static boolean isEmpty(String str) {
		return str==null || str.length()==0;
	}
}
