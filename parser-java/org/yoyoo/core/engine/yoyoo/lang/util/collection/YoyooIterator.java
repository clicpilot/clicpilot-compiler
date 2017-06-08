package org.yoyoo.core.engine.yoyoo.lang.util.collection;


import java.util.Iterator;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class YoyooIterator<V extends YoyooObject> extends YoyooTypeDefine {

	protected Iterator<V> iterator;

	public YoyooIterator(Iterator<V> iterator) {
		super();
		this.iterator = iterator;
	}

	public void hasNext(YoyooRef<YoyooBoolean> result) {		
		result.setValue(new YoyooBoolean(iterator.hasNext()));
	}

	public void next(YoyooRef<V> result) {
		result.setValue(iterator.next());
	}

	public void removeCurrent() {
		iterator.remove();		
	}

	

	
	
	
	
	
	
	
	
}
