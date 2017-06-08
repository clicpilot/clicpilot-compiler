package org.yoyoo.core.engine.yoyoo.lang.util.collection;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;


public class YoyooArrayList<R extends YoyooObject> extends YoyooList<R> {
	
	protected List<R> data =  new ArrayList<R>();
	
	public YoyooArrayList() {
		//System.out.println("new ArrayList");
	}
	
	public YoyooArrayList(Collection<R> c) {
		data.addAll(c);
		YoyooTypeDefineClass ycls =  YoyooEnvironment.getDefault().getDeclaration("yoyoo.lang.ArrayList");
		this.setUnit(ycls);
	}

	public void size(YoyooRef<YoyooInteger> result) {
		Integer i = data.size();
		result.setValue(new YoyooInteger(i));
	} 
	
	public void get(YoyooInteger index, YoyooRef<R> ref) {
		ref.setValue(data.get(index.getVal().intValue()));
	}
	
	public void set(YoyooInteger index, R r) {
		data.set(index.getVal().intValue(), r);		
	}

	@Override
	public void add(R r, YoyooRef<YoyooBoolean> result) {
		boolean b = data.add(r);
		result.setValue(new YoyooBoolean(b));		
	}

	@Override
	public void addAll(YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooArrayList) {
			boolean b = data.addAll(((YoyooArrayList<R>)c).data);
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
		if(c instanceof YoyooArrayList) {
			boolean b = data.containsAll(((YoyooArrayList<? extends R>)c).data);
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
		if(c instanceof YoyooArrayList) {
			boolean b = data.removeAll(((YoyooArrayList<? extends R>)c).data);
			result.setValue(new YoyooBoolean(b));
		} else {
			System.err.println("cannot containsAll YoyooCollection "+c);
		}
	}

	@Override
	public void add(YoyooInteger index, R element) {
		data.add(index.getVal().intValue(), element);		
	}

	@Override
	public void addAll(YoyooInteger index, YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooArrayList) {
			result.setValue(new YoyooBoolean(
					data.addAll(index.getVal().intValue(), ((YoyooArrayList<? extends R>)c).data))
			);
		} else {
			System.err.println("cannot containsAll YoyooCollection "+c);
			
		}
		
	}

	@Override
	public void indexOf(R r, YoyooRef<YoyooInteger> result) {
		result.setValue(new YoyooInteger(data.indexOf(r)));		
	}

	@Override
	public void lastIndexOf(R r, YoyooRef<YoyooInteger> result) {
		result.setValue(new YoyooInteger(data.lastIndexOf(r)));		
	}

	@Override
	public void listIterator(YoyooRef<YoyooListIterator<R>> result) {
		ListIterator<R> listIterator = data.listIterator();
		result.setValue(new YoyooListIterator<R>(listIterator));
		
	}

	@Override
	public void listIterator(YoyooInteger index, YoyooRef<YoyooListIterator<R>> result) {
		ListIterator<R> listIterator = data.listIterator(index.getVal().intValue());
		result.setValue(new YoyooListIterator<R>(listIterator));
	}

	@Override
	public void remove(YoyooInteger index, YoyooRef<R> result) {
		R r = data.remove(index.getVal().intValue());
		result.setValue(r);
		
	}

	@Override
	public void retainAll(YoyooCollection<R> c, YoyooRef<YoyooBoolean> result) {
		if(c instanceof YoyooArrayList) {
			result.setValue(new YoyooBoolean(data.retainAll(((YoyooArrayList<R>)c).data)));			
		} else {
			System.err.println("cannot containsAll YoyooCollection "+c);
		}
		
		
	}

	@Override
	public void set(YoyooInteger index, R element, YoyooRef<R> result) {
		result.setValue(data.set(index.getVal().intValue(), element));		
	}

	@Override
	public void subList(YoyooInteger fromIndex, YoyooInteger toIndex, YoyooRef<YoyooCollection<R>> result) {
		List<R> list = data.subList(fromIndex.getVal().intValue(), toIndex.getVal().intValue());
		result.setValue(new YoyooArrayList<R>(list));
	}

	@Override
	public Collection<R> getData() {
		return data;
	}



	
	
	
	
}
