package org.yoyoo.core.engine.encode.exception;

import org.yoyoo.core.engine.compile.stm.Stm;

public class UnsupportStmException extends EncodeException{


	
	public Stm getStm() {
		return stm;
	}
	public UnsupportStmException(Stm stm) {
		super();
		this.stm = stm;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5603511582606704325L;
	private Stm stm;

}
