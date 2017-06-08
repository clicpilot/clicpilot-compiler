package org.yoyoo.core.engine.runtime;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class RuntimeValueAtom implements IRuntimeValueAtom {
	
	protected YoyooObject objValue;

	protected SimpleNode node;

	protected CompilationUnit unit;

	public RuntimeValueAtom(YoyooObject objValue, SimpleNode node,
			CompilationUnit unit) {
		super();
		this.objValue = objValue;
		this.node = node;
		this.unit = unit;
	}

	public SimpleNode getNode() {
		return node;
	}

	public YoyooObject getValue() {
		return objValue;
	}

	public CompilationUnit getUnit() {
		return unit;
	}

	public void setValue(YoyooObject val) {
		this.objValue = val;

	}

	public RuntimeAtomType getRuntimeAtomType() {
		// TODO Auto-generated method stub
		return RuntimeAtomType.Value;
	}

	public YoyooArray getArray() throws YoyooRTException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isArray() {
		return false;
	}

	public void setValue(YoyooArray val) throws YoyooRTException {

		
	}

	@Override
	public IRuntimeValueAtom cloneAtom() throws YoyooRTException {
		//YoyooObject newObjValue = (YoyooObject)objValue.clone();
		return new RuntimeValueAtom(objValue, node,
				unit);
	}

}
