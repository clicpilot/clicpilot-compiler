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


public class LoopOperator extends AbstractStopOperator {

	private Operator loopBody;

	private Operator loopUpdate;

	private IAtom condAtom;

	@Override
	public boolean isMark() {
		// TODO Auto-generated method stub
		return true;
	}

	public LoopOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom condAtom, Operator loopBody,
			Operator loopUpdate) {
		super(node, unit, ycls);
		this.condAtom = condAtom;
		this.loopBody = loopBody;
		this.loopUpdate = loopUpdate;
		super.loop();

	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {

		YoyooObject b = ( (condAtom.getVal(ctx))).getValue();
		while (b instanceof YoyooBoolean && ((YoyooBoolean) b).getVal()) {

			loopBody.evaluate(ctx);
			if (this.isStop()) {
				break;
			}

			b = ((condAtom.getVal(ctx))).getValue();
			if (loopUpdate != null && b instanceof YoyooBoolean
					&& ((YoyooBoolean) b).getVal()) {
				loopUpdate.evaluate(ctx);
				b = ((condAtom.getVal(ctx))).getValue();
			}

			this.setContinue(false);
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

	@Override
	public void setStop(boolean stop, RuntimeContext ctx) {
		super.setStop(stop, ctx);
		((AbstractOperator) loopBody).stop(stop, ctx);
	}

	@Override
	public void setContinue(boolean ctu) {
		// TODO Auto-generated method stub
		super.setContinue(ctu);
		((AbstractOperator) loopBody).ctu(ctu);
	}

	public Operator getLoopBody() {
		return loopBody;
	}

	public void setLoopBody(Operator loopBody) {
		this.loopBody = loopBody;
	}

	public Operator getLoopUpdate() {
		return loopUpdate;
	}

	public void setLoopUpdate(Operator loopUpdate) {
		this.loopUpdate = loopUpdate;
	}

	public boolean isLoop() {
		// TODO Auto-generated method stub
		return true;
	}

	

	public IType operatorTypeCheck() throws CompileException {
		loopBody.typeCheck();
		if(loopUpdate!=null)
			loopUpdate.typeCheck();
		condAtom.getYoyooType();
		return new PrimitiveType.YoyooVoid(unit, node);
	}

}
