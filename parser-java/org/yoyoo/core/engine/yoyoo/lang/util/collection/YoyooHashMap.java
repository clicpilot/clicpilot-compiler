package org.yoyoo.core.engine.yoyoo.lang.util.collection;


import java.util.HashMap;
import java.util.Map;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;



public class YoyooHashMap<K extends YoyooObject, V extends YoyooObject> extends YoyooMap<K, V> {
	
	protected Map<K, V> data =  new HashMap<K, V>();


	public void clearAll() {
		data.clear();		
	}


	public void containsKey(K arg0, YoyooRef<YoyooBoolean> result) {
		boolean b = data.containsKey(arg0);
		result.setValue(new YoyooBoolean(b));
	}


	public void containsValue(V arg0, YoyooRef<YoyooBoolean> result) {
		boolean b = data.containsValue(arg0);
		result.setValue(new YoyooBoolean(b));
	}


	public void get(K arg0, YoyooRef<V> result) {
		V val = data.get(arg0);
		result.setValue(val);
	}

	public void isEmpty(YoyooRef<YoyooBoolean> result) {
		result.setValue(new YoyooBoolean(data.isEmpty()));
	}


	public void keySet(YoyooRef<YoyooSet<K>> result) {
		result.setValue(new YoyooHashSet<K>(data.keySet()));
	}


	public void put(K arg0, V arg1, YoyooRef<V> result) {
		data.put(arg0, arg1);
		result.setValue(arg1);
	}

	
	public void putMap(YoyooMap<K, V> arg0) {
		data.putAll(arg0.getData());	
	}


	public void remove(K arg0, YoyooRef<V> result) {
		V val = data.remove(arg0);
		result.setValue(val);
	}


	public void size(YoyooRef<YoyooInteger> result) {
		result.setValue(new YoyooInteger(data.size()));
	}


	public void values(YoyooRef<YoyooList<V>> result) {
		YoyooArrayList<V> list = new YoyooArrayList<V>(data.values());
		result.setValue(list);
	}


	@Override
	public Map<K, V> getData() {
		return data;
	}

	
	
}
