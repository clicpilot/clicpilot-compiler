package org.yoyoo.core.engine.yoyoo.lang.util.collection;

import java.util.Map;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public abstract class YoyooMap<K extends YoyooObject, V extends YoyooObject> extends YoyooTypeDefine {
	
	public abstract void clearAll();
	
	public abstract void containsKey(K arg0, YoyooRef<YoyooBoolean> result);
	
	public abstract void containsValue(V arg0, YoyooRef<YoyooBoolean> result);
	
	public abstract void get(K arg0, YoyooRef<V> result);
	
	public abstract void isEmpty(YoyooRef<YoyooBoolean> result);
	
	public abstract void keySet(YoyooRef<YoyooSet<K>> result);
	
	public abstract void put(K arg0, V arg1, YoyooRef<V> result);
	
	public abstract void putMap(YoyooMap<K, V> arg0);
	
	public abstract void remove(K arg0, YoyooRef<V> result);
	
	public abstract void size(YoyooRef<YoyooInteger> result);
	
	public abstract void values(YoyooRef<YoyooList<V>> result);	
	
	public abstract Map<K, V> getData();
	
	
}
