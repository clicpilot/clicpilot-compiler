package org.yoyoo.core.engine.encode.exception;

import org.yoyoo.core.engine.compile.exp.Expr;

public class UnsupportExprException extends EncodeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4799740319248572348L;
	private Expr expr;
	public UnsupportExprException(Expr expr) {
		super();
		this.expr = expr;
	}
	public Expr getExpr() {
		return expr;
	}

}
