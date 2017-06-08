package org.yoyoo.core.engine.compile.opt;

import java.util.ArrayList;
import java.util.List;

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

public class OperatorList extends AbstractStopOperator {

	protected List<Operator> operators;

	// protected Operator head;
	// protected Operator tail;
	// private int count;
	// private String label;

	// protected List<Operator> opts;

	public OperatorList(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		IRuntimeValueAtom returnAtom = null;
		if (operators != null)
			for (Operator opt : operators) {
				// GotoOperatorOperator resumeAt = ctx.getSkipTo();
				if (opt != null) {
					returnAtom = ctx.evaluateOperator(opt);
					if (isContinue() || isStop()) {
						resetStop();
						break;
					}
				}
			}

		return returnAtom;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {


	}

	public void addOperator(Operator opt) {
		if (operators == null)
			operators = new ArrayList<Operator>();
		operators.add(opt);
	}
	
	public void setOperator(int index, Operator opt) {		
		operators.set(index, opt);
	}

	public boolean isEmpty() {
		return operators == null || operators.isEmpty();
	}

	public int size() {
		return operators == null ? 0 : operators.size();
	}

	@Override
	public void setLabel(String label) {
		super.setLabel(label);
		for (Operator opt : operators) {
			opt.setLabel(label);
		}
	}

	public Operator firstOperator() {
		if (operators != null)
			return operators.get(0);
		else
			return null;
	}

	@Override
	public void setStop(boolean stop, RuntimeContext ctx) {
		super.setStop(stop, ctx);
		boolean found = false;
		for (Operator opt : operators) {
			if(ctx.isCurrentOpt(opt)) {
				found = true;
				continue;
			}
			if(found)
				((AbstractOperator) opt).stop(stop, ctx);
		}
	}

	@Override
	public void setContinue(boolean ctu) {
		super.setContinue(ctu);
		for (Operator opt : operators) {
			((AbstractOperator) opt).ctu(ctu);
		}
	}

	public IType operatorTypeCheck() throws CompileException {
		if (operators != null && !operators.isEmpty())
		{
			for(Operator opt : operators)
				opt.typeCheck();
			return operators.get(operators.size() - 1).getYoyooType();
		}else
			return new PrimitiveType.YoyooVoid(unit, node);
	}

}
