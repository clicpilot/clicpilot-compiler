package org.yoyoo.core.engine.test.uncompilable;

import java.util.Iterator;

import org.junit.Test;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.test.AbstractTestCase;

public class TestStaticMethod  extends AbstractTestCase{
	
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
		Iterator<CompileException> i = env.getErrors().iterator();
		int j=0;
		while(i.hasNext()) {
			if(i.next() instanceof CompileException.CannotCallNonStaticMethod) {
				j++;
			}
		} 
		assert(j==1);
	}
	
	
	
	
	
}
