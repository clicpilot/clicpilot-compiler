package org.yoyoo.core.engine.compile.atom;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class NullAtom extends ValueAtom {

	public NullAtom(IType type, YoyooObject obj, SimpleNode node,
			CompilationUnit unit) {
		super(type, obj, node, unit);
		// TODO Auto-generated constructor stub
	}

	public IRuntimeValueAtom getVal(RuntimeContext ctx) throws YoyooRTException {
		return new RuntimeValueAtom(new YoyooNull(null), node, unit);
	}
	
}
