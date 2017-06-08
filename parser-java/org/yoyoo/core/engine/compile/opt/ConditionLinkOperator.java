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
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;


public class ConditionLinkOperator extends AbstractOperator {

	public ConditionLinkOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, Operator trueLink, Operator falseLink,
			IAtom condAtom) {
		super(node, unit, ycls);
		this.trueLink = trueLink;
		this.falseLink = falseLink;
		this.condAtom = condAtom;
	}

	private Operator trueLink;

	private Operator falseLink;

	private IAtom condAtom;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		YoyooObject b = ( (condAtom.getVal(ctx))).getValue();
		if (b instanceof YoyooBoolean) {
			if (((YoyooBoolean) b).getVal()) {
				return ctx.evaluateOperator(trueLink);
			} else if (falseLink != null) {
				return ctx.evaluateOperator(falseLink);
			}
		} else {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		// TODO Auto-generated method stub

	}

	public IAtom getCondAtom() {
		return condAtom;
	}

	public void setCondAtom(IAtom condAtom) {
		this.condAtom = condAtom;
	}

	public Operator getFalseLink() {
		return falseLink;
	}

	public void setFalseLink(Operator falseLink) {
		this.falseLink = falseLink;
	}

	public Operator getTrueLink() {
		return trueLink;
	}

	public void setTrueLink(Operator trueLink) {
		this.trueLink = trueLink;
	}

	public IType operatorTypeCheck() throws CompileException {
		if(condAtom!=null)
			condAtom.getYoyooType();
		if(trueLink!=null)
			trueLink.typeCheck();
		if(falseLink!=null)
			falseLink.typeCheck();
		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
