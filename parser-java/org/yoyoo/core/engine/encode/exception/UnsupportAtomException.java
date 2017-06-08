package org.yoyoo.core.engine.encode.exception;

import org.yoyoo.core.engine.compile.atom.IValueAtom;

public class UnsupportAtomException extends EncodeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3396100142223930210L;
	
	private IValueAtom atom;

	public IValueAtom getAtom() {
		return atom;
	}

	public UnsupportAtomException(IValueAtom atom) {
		super();
		this.atom = atom;
	}
}
