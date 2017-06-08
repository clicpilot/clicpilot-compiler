package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.opt.StopOperator;
import org.yoyoo.core.engine.parser.SimpleNode;

public class BreakStm extends StopStm {

	public BreakStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);

	}

	public Operator convert2Operator() throws CompileException {
		Operator loopOpt = null;
		if ((loopOpt = this.unit.findMark(this.label, false))!=null) {
			StopOperator opt = new StopOperator(node,
					ycls.getCompilationUnit(), ycls, this.stopAt, this.label,
					true);
			opt.setLoopOpt(loopOpt);
			return opt;
		} else {
			if (label == null)
				throw new CompileException.BreakNotInLoopOrSwitch(node,
						this.unit);
			else
				throw new CompileException.LabelNotDefined(label, node,
						this.unit);
		}

	}

}
