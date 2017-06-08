package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.parser.SimpleNode;

public interface ILocalVariableStm {
	public void openLocalVariableStack();

	public void registerNewLocalVariable(YoyooVariable decl, SimpleNode node);

	public void closeLocalVariableStack();
}
