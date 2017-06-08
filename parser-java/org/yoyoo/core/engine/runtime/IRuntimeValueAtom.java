package org.yoyoo.core.engine.runtime;

import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;

public interface IRuntimeValueAtom {//extends Cloneable{

	public void setValue(YoyooObject val);

	public YoyooObject getValue() throws YoyooRTException;
	
	public RuntimeAtomType getRuntimeAtomType();

	public IRuntimeValueAtom cloneAtom() throws YoyooRTException;

}
