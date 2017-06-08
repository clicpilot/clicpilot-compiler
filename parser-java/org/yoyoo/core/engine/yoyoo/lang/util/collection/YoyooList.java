package org.yoyoo.core.engine.yoyoo.lang.util.collection;


import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;

public abstract class YoyooList<R extends YoyooObject> extends YoyooCollection<R> {	
	
	public abstract void add(YoyooInteger index, R r);
	
	public abstract void addAll(YoyooInteger index, YoyooCollection<R> c, YoyooRef<YoyooBoolean> result);
	
	public abstract void get(YoyooInteger index, YoyooRef<R> result);
	
	public abstract void indexOf(R r, YoyooRef<YoyooInteger> result);
	
	public abstract void lastIndexOf(R r, YoyooRef<YoyooInteger> result);
	
	public abstract void listIterator(YoyooRef<YoyooListIterator<R>> result);
	
	public abstract void listIterator(YoyooInteger index, YoyooRef<YoyooListIterator<R>> result);
	
	public abstract void remove(YoyooInteger index, YoyooRef<R> result);

	public abstract void set(YoyooInteger index, R r, YoyooRef<R> result);
	
	public abstract void subList(YoyooInteger fromIndex, YoyooInteger toIndex, YoyooRef<YoyooCollection<R>> result);
	
}
