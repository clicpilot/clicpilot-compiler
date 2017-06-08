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

public class LabelOperator extends AbstractStopOperator {

	public LabelOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, String label, Operator opt) {
		super(node, unit, ycls);
		this.label = label;
		this.opt = opt;
	}

	private String label;

	private Operator opt;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		opt.setLabel(label);
		return opt.evaluate(ctx);
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		// TODO Auto-generated method stub

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

	public IType operatorTypeCheck() throws CompileException {
		opt.typeCheck();
		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
