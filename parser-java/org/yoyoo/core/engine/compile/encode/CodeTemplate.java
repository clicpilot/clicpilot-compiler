package org.yoyoo.core.engine.compile.encode;

public abstract class CodeTemplate {

	private String body;
	
	protected void append(String str) {
		body+=str;
	} 
	
}
