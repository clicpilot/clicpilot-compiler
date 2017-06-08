package org.yoyoo.core.engine.yoyoo.lang.util.collection;



import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;


public class YoyooHashSet<R extends YoyooObject> extends YoyooSet<R> {
	
	protected Set<R> data =  new HashSet<R>();
	public YoyooHashSet() {
		
	}
	
	public YoyooHashSet(Collection<R> c) {
		data.addAll(c);
		YoyooTypeDefineClass ycls =  YoyooEnvironment.getDefault().getDeclaration("yoyoo.lang.HashSet");
		this.setUnit(ycls);
	}
	
	public YoyooHashSet(Set<R> data) {
		this.data = data;
		YoyooTypeDefineClass ycls =  YoyooEnvironment.getDefault().getDeclaration("yoyoo.lang.HashSet");
		this.setUnit(ycls);
	}

	public void size(YoyooRef<YoyooInteger> result) {
		Integer i = data.size();
		result.setValue(new YoyooInteger(i));
	} 

	@Override
	public void add(R r, YoyooRef<YoyooBoolean> result) {
		boolean b = data.add(r);
		result.setValue(new YoyooBoolean(b));		
	}

	@Override
	public void addAll(YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooCollection) {
			boolean b = data.addAll(((YoyooHashSet<? extends R>)c).getData());
			result.setValue(new YoyooBoolean(b));
		} else {
			System.err.println("cannot addAll YoyooCollection "+c);
		}
	}

	@Override
	public void clearAll() {
		data.clear();	
	}

	@Override
	public void contains(R o, YoyooRef<YoyooBoolean> result) {
		boolean b = data.contains(o);
		result.setValue(new YoyooBoolean(b));
	}

	@Override
	public void containsAll(YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooCollection) {
			boolean b = data.containsAll(((YoyooHashSet<? extends R>)c).getData());
			result.setValue(new YoyooBoolean(b));
		} else {
			System.err.println("cannot containsAll YoyooCollection "+c);
		}
	}

	@Override
	public void isEmpty(YoyooRef<YoyooBoolean> result) {
		boolean b = data.isEmpty();
		result.setValue(new YoyooBoolean(b));		
	}

	@Override
	public void iterator(YoyooRef<YoyooIterator<R>> result) {
		Iterator<R> iterator = data.iterator();
		result.setValue(new YoyooListIterator<R>(iterator));
	}

	@Override
	public void remove(R o, YoyooRef<YoyooBoolean> result) {
		boolean b = data.remove(o);
		result.setValue(new YoyooBoolean(b));
		
	}

	@Override
	public void removeAll(YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooCollection) {
			boolean b = data.removeAll(((YoyooCollection<? extends R>)c).getData());
			result.setValue(new YoyooBoolean(b));
		} else {
			System.err.println("cannot removeAll YoyooCollection "+c);
		}
	}

	@Override
	public void retainAll(YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooCollection) {
			boolean b = data.retainAll(((YoyooCollection<R>)c).getData());
			result.setValue(new YoyooBoolean(b));
		} else {
			System.err.println("cannot retainAll YoyooCollection "+c);
		}
		
	}

	@Override
	public Collection<R> getData() {
		return data;
	}



	
	
	
	
}
