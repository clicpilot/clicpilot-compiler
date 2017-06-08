package org.yoyoo.core.engine.yoyoo.lang.util.collection;


import java.util.Iterator;
import java.util.ListIterator;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;



public class YoyooListIterator<R extends YoyooObject> extends YoyooIterator<R> {

	public YoyooListIterator(Iterator<R> iterator) {
		super(iterator);
	}

	public void add(R arg0) {
		((ListIterator<R>)iterator).add(arg0);		
	}

	public void hasPrevious(YoyooRef<YoyooBoolean> result) {
		result.setValue(new YoyooBoolean(((ListIterator<R>)iterator).hasPrevious()));
	}

	public void nextIndex(YoyooRef<YoyooInteger> result) {
		result.setValue(new YoyooInteger(((ListIterator<R>)iterator).nextIndex()));
	}

	public void previous(YoyooRef<R> result) {
		result.setValue(((ListIterator<R>)iterator).previous());
	}

	public void previousIndex(YoyooRef<YoyooInteger> result) {
		result.setValue(new YoyooInteger(((ListIterator<R>)iterator).previousIndex()));
	}

	public void setItem(R arg0) {
		((ListIterator<R>)iterator).set(arg0);
	}

}
