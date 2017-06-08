package org.yoyoo.core.engine.encode.exception;

import org.yoyoo.core.engine.compile.declaration.IDeclaration;

public class UnsupportDeclException extends EncodeException{


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8610306228118815287L;
	private IDeclaration decl;
	public IDeclaration getDecl() {
		return decl;
	}
	public UnsupportDeclException(IDeclaration decl) {
		super();
		this.decl = decl;
	}

}
