package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;

public class OperatorFactory {
	private static OperatorFactory factory;

	private OperatorFactory() {

	}

	public static Operator createOperator(String opt, IAtom[] atoms,
			SimpleNode node, CompilationUnit unit, YoyooTypeDefineClass ycls)
			throws CompileException {
		if (factory == null) {
			factory = new OperatorFactory();
		}
		return factory.operator(opt, atoms, node, unit, ycls);
	}

	private Operator operator(String opt, IAtom[] atoms, SimpleNode node,
			CompilationUnit unit, YoyooTypeDefineClass ycls)
			throws CompileException {
		Operator operator = null;
		if (markEq(opt, OperatorMark.MARK.PLUS)) {
			operator = new AddOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.MINUS)) {
			if (atoms.length == 1)
				operator = new NegativeOperator(node, unit, ycls);
			else
				operator = new SubtractOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.STAR)) {
			operator = new MultiplyOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.SLASH)) {
			operator = new DivideOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.REM)) {
			operator = new RemOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.SC_OR)) {
			operator = new OrOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.SC_AND)) {
			operator = new AndOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.LE)) {
			operator = new LEOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.GE)) {
			operator = new GEOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.NE)) {
			operator = new NEOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.EQ)) {
			operator = new EQOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.GT)) {
			operator = new GTOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.LT)) {
			operator = new LTOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.INCR)) {
			if (atoms.length == 1)
				operator = new PostIncreaseOperator(node, unit, ycls);
			else
				operator = new PreIncreaseOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.DECR)) {
			operator = new PreDecreaseOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.BANG)) {
			operator = new BangOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.ASSIGN)) {
			operator = new AssignOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.PLUSASSIGN)) {
			operator = new PlusAssignOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.MINUSASSIGN)) {
			operator = new MinusAssignOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.STARASSIGN)) {
			operator = new StarAssignOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.SLASHASSIGN)) {
			operator = new SlashAssignOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.REMASSIGN)) {
			operator = new RemAssignOperator(node, unit, ycls);
		} else if (markEq(opt, OperatorMark.MARK.HOOK)) {
			operator = new CondOperator(node, unit, ycls);
		} else {
			throw new CompileException.UnsupportedOperator(opt, node, unit);
		}
		operator.installArguments(atoms);
		return operator;
	}

	private boolean markEq(String opt, OperatorMark.MARK mark) {
		return opt.equals(mark.getStr());
	}

}
