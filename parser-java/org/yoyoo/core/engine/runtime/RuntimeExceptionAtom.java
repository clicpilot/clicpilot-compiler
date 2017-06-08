package org.yoyoo.core.engine.runtime;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooException;


public class RuntimeExceptionAtom implements IRuntimeValueAtom {
	protected YoyooException exception;

	protected SimpleNode node;

	protected CompilationUnit unit;

	public RuntimeExceptionAtom(YoyooException exception, SimpleNode node,
			CompilationUnit unit) {
		super();
		this.exception = exception;
		this.node = node;
		this.unit = unit;
	}

	public SimpleNode getNode() {
		return node;
	}

	public YoyooObject getValue() {
		return exception;
	}

	public CompilationUnit getUnit() {
		return unit;
	}

	public void setValue(YoyooObject val) {
		this.exception = (YoyooException)val;

	}

	public RuntimeAtomType getRuntimeAtomType() {
		// TODO Auto-generated method stub
		return RuntimeAtomType.Value;
	}

	public YoyooArray getArray() throws YoyooRTException {
		return null;
	}

	public boolean isArray() {
		return false;
	}

	public void setValue(YoyooArray val) throws YoyooRTException {

		
	}

	@Override
	public IRuntimeValueAtom cloneAtom() throws YoyooRTException {
		YoyooException newObjValue = (YoyooException)exception.cloneAtom();
		return new RuntimeExceptionAtom(newObjValue, node,
				unit);
	}

}
