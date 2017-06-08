package org.yoyoo.core.engine.compile.atom;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class ValueAtom extends AbstractValueAtom {

	private YoyooObject obj;

	public ValueAtom(IType type, YoyooObject obj, SimpleNode node,
			CompilationUnit unit) {
		super(type, node, unit);
		this.obj = obj;
	}

	public AtomType getAtomType() {
		return AtomType.Value;
	}

	public IRuntimeValueAtom getVal(RuntimeContext ctx) throws YoyooRTException {
		return new RuntimeValueAtom(obj, node, unit);
	}

	public YoyooObject getValue() {
		return obj;
	}


}
