package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.AtomType;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public abstract class AbstractOperator implements Operator {



	protected SimpleNode node;

	protected CompilationUnit unit;

	protected Operator before;

	protected Operator after;

	protected IType type;

	protected YoyooClass ycls;

	private String label;	

	protected boolean typeChecked = false;


	protected void stop(boolean b, RuntimeContext ctx) {
		if (this instanceof IStop) {
			((IStop) this).setStop(b, ctx);
		}
	}

	protected void ctu(boolean b) {
		if (this instanceof IStop) {
			((IStop) this).setContinue(b);
		}
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public IRuntimeValueAtom getVal(RuntimeContext ctx) throws YoyooRTException {
		IRuntimeValueAtom val = this.evaluate(ctx);
		return val;
	}

	public IType getYoyooType() throws CompileException {
		return this.typeCheck();
	}

	protected AbstractOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super();
		this.node = node;
		this.unit = unit;
		this.ycls = ycls;
	}

	//
	// public void setNext(Operator opt) {
	// this.next = opt;
	//		
	// }

	protected boolean checkNumOfArguments(int src, int tar)
			throws CompileException {
		if (src == tar) {
			return true;
		} else {
			throw new CompileException.WrongOperatorArguments(node, unit);
		}
	}

	protected YoyooObject calculateAtom(IAtom atom, RuntimeContext ctx)
			throws YoyooRTException {
		IRuntimeValueAtom val =  atom.getVal(ctx);
		if(val!=null)
		{
			YoyooObject obj = val.getValue();
			return obj;
		}
		else
		{
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
	}

	public AtomType getAtomType() {
		// TODO Auto-generated method stub
		return AtomType.Operator;
	}

	public String getReferenceName(IAtom atom) {
		switch (atom.getAtomType()) {
		case Ref:
			return ((ReferenceAtom) atom).getName();
		case Operator:
		case Value:
			return null;
		default:
			return null;
		}
	}

	public IRuntimeValueAtom getValueAtom(IAtom atom, RuntimeContext ctx)
			throws YoyooRTException {
		return (IRuntimeValueAtom) atom.getVal(ctx);
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		throw new YoyooRTException.CannotEval(node, unit, ctx);
	}

	public final IRuntimeValueAtom evaluate(RuntimeContext ctx)
			throws YoyooRTException {

		ctx.evaluateOperator(before);
		IRuntimeValueAtom val = this.eval(ctx);
		ctx.evaluateOperator(after);
		return val;
	}

	public Operator getBefore() {
		return before;
	}

	public void setBefore(Operator before) {
		this.before = before;
	}

	public Operator getAfter() {
		return after;
	}

	public void setAfter(Operator after) {
		this.after = after;
	}

	public SimpleNode getNode() {
		return node;
	}

	public CompilationUnit getCompilationUnit() {
		return unit;
	}
	
	@Override
	public IType typeCheck() throws CompileException {

		IType type = operatorTypeCheck();
		typeChecked = true;
		return type;
	}
	
	protected abstract IType operatorTypeCheck() throws CompileException;

	
	public String toSourceLocationString() {
		String str = unit.getSource().getFile().getPath() + " ("+ 
		node.first_token.beginLine+", " +
		node.first_token.beginColumn+")";
		return str;
	}

}
