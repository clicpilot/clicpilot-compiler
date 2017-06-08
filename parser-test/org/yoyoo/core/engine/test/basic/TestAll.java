package org.yoyoo.core.engine.test.basic;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Test;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.test.AbstractTestCase;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeEncoder;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public class TestAll extends AbstractTestCase{
	
	private final String testYoyoo = "";
	
	@Test
	public void test()
	{
		runDefaultTestFunction();
	}
	
	@Override
	protected void runDefaultTestFunction()
	{
		
		String path = getRootPath();
		String pkg = this.getClass().getPackage().getName();
		String pkgpath = pkg.replace(".", "/");
		
		File f = new File(path+"/"+pkgpath);
		String fns[] = f.list(new FilenameFilter() {
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(testYoyoo+".yoyoo");
			}});
		
		
		
		for(String filename : fns) {
			int i = filename.lastIndexOf(".yoyoo");
			String yoyooName = filename.substring(0, i);
			System.out.println("run "+this.getClass().getPackage().getName()+"."+yoyooName+" "+getDefaultTestFunctionName());
			RuntimeContext.getCurrentContext().run(this.getClass().getPackage().getName(), yoyooName, getDefaultTestFunctionName());
			CodeEncoder encoder = new CodeEncoder();
			encoder.encodeClass(new YoyooString(this.getClass().getPackage().getName()), new YoyooString(yoyooName));
			
			try {
				for(Boolean b : TestCase.resultOfEquals) {
					assertEquals(b, Boolean.TRUE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				TestCase.resultOfEquals.clear();
				RuntimeContext.getCurrentContext().clear();
			}
		}
		
		
	}
}
