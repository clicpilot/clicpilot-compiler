package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class StopOperator extends AbstractOperator {

	public StopOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, Operator opt, String label, boolean brk) {
		super(node, unit, ycls);
		this.opt = opt;
		this.label = label;
		this.brk = brk;
	}

	private Operator opt;
	
	private Operator loopOpt;

	private String label;

	private boolean brk;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		ctx.stopLastLoopOperator(node, unit, label, brk, loopOpt);
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
	}

	public String getLabel() {
		return label;
	}

	public Operator getOpt() {
		return opt;
	}

	public void setOpt(Operator opt) {
		this.opt = opt;
	}

	public boolean isBrk() {
		return brk;
	}

	public IType operatorTypeCheck() throws CompileException {
		if(opt!=null)
		{
			opt.typeCheck();
		}
		return new PrimitiveType.YoyooVoid(unit, node);
	}

	public Operator getLoopOpt() {
		return loopOpt;
	}

	public void setLoopOpt(Operator loopOpt) {
		this.loopOpt = loopOpt;
	}

}
