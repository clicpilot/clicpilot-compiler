package org.yoyoo.core.engine.yoyoo.lang.util.collection;

import java.util.Collection;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public abstract class YoyooCollection<V extends YoyooObject> extends YoyooTypeDefine {
	
	public abstract void add(V e, YoyooRef<YoyooBoolean> result);

	public abstract void addAll(YoyooCollection<V> c, YoyooRef<YoyooBoolean> result);
	
	public abstract void clearAll();

	public abstract void contains(V o, YoyooRef<YoyooBoolean> result);

	public abstract void containsAll(YoyooCollection<V> c, YoyooRef<YoyooBoolean> result);

	public abstract void isEmpty(YoyooRef<YoyooBoolean> result);

	public abstract void iterator(YoyooRef<YoyooIterator<V>> result);

	public abstract void remove(V o, YoyooRef<YoyooBoolean> result);

	public abstract void removeAll(YoyooCollection<V> c, YoyooRef<YoyooBoolean> result);

	public abstract void size(YoyooRef<YoyooInteger> result);

	public abstract void retainAll(YoyooCollection<V> c, YoyooRef<YoyooBoolean> result);
	
	public abstract Collection<V> getData();
	
	
}
