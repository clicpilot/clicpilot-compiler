package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;

public class YoyooStringBuffer extends YoyooTypeDefine  {
	private StringBuffer buffer;
	
	public void init(YoyooString str) {
		buffer = new StringBuffer(str.getVal());
	}
	
	public void init() {
		buffer = new StringBuffer();
	}

	public StringBuffer appendVal(boolean arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(char arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(char[] arg0, int arg1, int arg2) {
		return buffer.append(arg0, arg1, arg2);
	}

	public StringBuffer appendVal(char[] arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(CharSequence arg0, int arg1, int arg2) {
		return buffer.append(arg0, arg1, arg2);
	}

	public StringBuffer appendVal(CharSequence arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(double arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(float arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(int arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(long arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(Object arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(String arg0) {
		return buffer.append(arg0);
	}

	public StringBuffer appendVal(StringBuffer arg0) {
		return buffer.append(arg0);
	}

//	public StringBuffer appendCodePoint(int arg0) {
//		return buffer.appendCodePoint(arg0);
//	}

	public int capacity() {
		return buffer.capacity();
	}

	public char charAt(int arg0) {
		return buffer.charAt(arg0);
	}

//	public int codePointAt(int arg0) {
//		return buffer.codePointAt(arg0);
//	}

//	public int codePointBefore(int arg0) {
//		return buffer.codePointBefore(arg0);
//	}
//
//	public int codePointCount(int arg0, int arg1) {
//		return buffer.codePointCount(arg0, arg1);
//	}

	public StringBuffer del(int arg0, int arg1) {
		return buffer.delete(arg0, arg1);
	}

	public StringBuffer delCharAt(YoyooInteger arg0) {
		return buffer.deleteCharAt(arg0.getVal().intValue());
	}

	public void ensureCapacity(int arg0) {
		buffer.ensureCapacity(arg0);
	}

	public boolean equals(Object obj) {
		return buffer.equals(obj);
	}

	public void getChars(int arg0, int arg1, char[] arg2, int arg3) {
		buffer.getChars(arg0, arg1, arg2, arg3);
	}

	public int hashCode() {
		return buffer.hashCode();
	}

	public int indexOf(String arg0, int arg1) {
		return buffer.indexOf(arg0, arg1);
	}

	public int indexOf(String arg0) {
		return buffer.indexOf(arg0);
	}

	public StringBuffer insert(int arg0, boolean arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, char arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, char[] arg1, int arg2, int arg3) {
		return buffer.insert(arg0, arg1, arg2, arg3);
	}

	public StringBuffer insert(int arg0, char[] arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, CharSequence arg1, int arg2, int arg3) {
		return buffer.insert(arg0, arg1, arg2, arg3);
	}

	public StringBuffer insert(int arg0, CharSequence arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, double arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, float arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, int arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, long arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, Object arg1) {
		return buffer.insert(arg0, arg1);
	}

	public StringBuffer insert(int arg0, String arg1) {
		return buffer.insert(arg0, arg1);
	}

	public int lastIndexOf(String arg0, int arg1) {
		return buffer.lastIndexOf(arg0, arg1);
	}

	public int lastIndexOf(String arg0) {
		return buffer.lastIndexOf(arg0);
	}

	public void length(YoyooRef<YoyooInteger> tar) {
		tar.setValue(new YoyooInteger(buffer.length()));
	}

//	public int offsetByCodePoints(int arg0, int arg1) {
//		return buffer.offsetByCodePoints(arg0, arg1);
//	}

	public StringBuffer replace(int arg0, int arg1, String arg2) {
		return buffer.replace(arg0, arg1, arg2);
	}

//	public StringBuffer reverse() {
//		return buffer.reverse();
//	}

	public void setCharAt(int arg0, char arg1) {
		buffer.setCharAt(arg0, arg1);
	}

	public void setLength(int arg0) {
		buffer.setLength(arg0);
	}

	public CharSequence subSequence(int arg0, int arg1) {
		return buffer.subSequence(arg0, arg1);
	}

	public String substring(int arg0, int arg1) {
		return buffer.substring(arg0, arg1);
	}

	public String substring(int arg0) {
		return buffer.substring(arg0);
	}

	public void toString(YoyooRef<YoyooString> tar) {
		tar.setValue(new YoyooString(buffer.toString()));
	}

	public void trimToSize() {
		buffer.trimToSize();
	}
}
