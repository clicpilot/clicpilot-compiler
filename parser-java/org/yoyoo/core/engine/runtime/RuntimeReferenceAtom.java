package org.yoyoo.core.engine.runtime;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class RuntimeReferenceAtom implements IRuntimeValueAtom {
	protected String name;

	protected SimpleNode node;

	protected CompilationUnit unit;

	protected RuntimeContext ctx;

	public RuntimeReferenceAtom(RuntimeContext ctx, String name,
			SimpleNode node, CompilationUnit unit) {
		super();
		this.name = name;
		this.node = node;
		this.unit = unit;
		this.ctx = ctx;
	}

	public RuntimeAtomType getRuntimeAtomType() {
		// TODO Auto-generated method stub
		return RuntimeAtomType.Ref;
	}

	public String getName() {
		return name;
	}

	public YoyooObject getValue() throws YoyooRTException {

		return ctx.lookupAtom(name, node, unit).getValue();
	}

	public void setValue(YoyooObject val) {
		// ctx.updateVariable(name, new RuntimeValueAtom(val, node, unit));

	}

	@Override
	public IRuntimeValueAtom cloneAtom() throws YoyooRTException {
		throw new YoyooRTException.ObjectCannotbeCloned(null, null, null);
	}
}
