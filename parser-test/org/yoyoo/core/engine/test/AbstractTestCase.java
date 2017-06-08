package org.yoyoo.core.engine.test;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.CodeEncoder;
import org.yoyoo.core.engine.yoyoo.lang.type.TestCase;


public abstract class AbstractTestCase {
	
	protected YoyooEnvironment env;
	
	@Before
	public void setup()
	{
		env = YoyooEnvironment.getDefault();
		env.addYoyooPath(getRootPath());
		RuntimeContext.getCurrentContext().clear();
		TestCase.clearValues();
	}
	
	@After
	public void teardown()
	{
		env.clearError();
	}
	
	protected String getRootPath()
	{
		String path = null;
		try {
			path = URLDecoder.decode(this.getClass().getResource("/").getPath(), Charset.defaultCharset().name());
		} catch (UnsupportedEncodingException e) {
		}
		return path;
	}



	
	protected void runDefaultTestFunction()
	{
		
		RuntimeContext.getCurrentContext().run(this.getClass().getPackage().getName(), this.getClass().getSimpleName(), getDefaultTestFunctionName());
		try {
			for(Boolean b : TestCase.resultOfEquals) {
				assertEquals(b, Boolean.TRUE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			TestCase.resultOfEquals.clear();
		}
		
		CodeEncoder encoder = new CodeEncoder();
		encoder.encodeClass(new YoyooString(this.getClass().getPackage().getName()), new YoyooString(this.getClass().getSimpleName()));
	}
	
	protected String getDefaultTestFunctionName()
	{
		return "test";
	}
}
