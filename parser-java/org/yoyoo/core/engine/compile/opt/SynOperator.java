package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class SynOperator extends AbstractOperator {

	private IAtom synItemAtom;
	
	private IAtom synBlockAtom;
	
	public SynOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom synItemAtom) {
		super(node, unit, ycls);
		this.synItemAtom = synItemAtom;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		synchronized (synItemAtom.getVal(ctx)) {
			return synBlockAtom.getVal(ctx);
		}
		
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		synBlockAtom = arguments[0];
	}

	public IType operatorTypeCheck() throws CompileException {
		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
