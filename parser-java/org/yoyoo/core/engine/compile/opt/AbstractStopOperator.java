package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.RuntimeContext;

public abstract class AbstractStopOperator extends AbstractOperator implements
		IStop {

	protected AbstractStopOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	private boolean stop;

	private boolean ctu;

	private boolean marked;

	private boolean loop;

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop, RuntimeContext ctx) {
		this.stop = stop;
	}

	public boolean isContinue() {
		return ctu;
	}

	public void setContinue(boolean ctu) {
		this.ctu = ctu;
	}

	public void resetStop() {
		this.stop = false;
		this.ctu = false;
	}

	public boolean isMark() {
		return marked;
	}

	public void mark() {
		marked = true;

	}

	public boolean isLoop() {
		return loop;
	}

	public void loop() {
		loop = true;

	}
}
