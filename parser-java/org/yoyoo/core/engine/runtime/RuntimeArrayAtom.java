package org.yoyoo.core.engine.runtime;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class RuntimeArrayAtom implements IRuntimeValueAtom {
	protected YoyooArray objValue;

	protected SimpleNode node;

	protected CompilationUnit unit;

	public RuntimeArrayAtom(YoyooArray objValue, SimpleNode node,
			CompilationUnit unit) {
		super();
		this.objValue = objValue;
		this.node = node;
		this.unit = unit;
	}

	public RuntimeArrayAtom(SimpleNode node,
			CompilationUnit unit) {
		super();

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
		this.objValue = (YoyooArray)val;

	}

	public RuntimeAtomType getRuntimeAtomType() {
		// TODO Auto-generated method stub
		return RuntimeAtomType.Value;
	}

	@Override
	public IRuntimeValueAtom cloneAtom() throws YoyooRTException {
		YoyooArray newObjValue = (YoyooArray)objValue.cloneAtom();
		return new RuntimeArrayAtom(newObjValue, node,
				unit);
	}


}
