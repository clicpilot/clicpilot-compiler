package org.yoyoo.core.engine.yoyoo.lang.expr;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public interface ISpecialExprProvider {
	public ISpecialExpr createExpr(SimpleNode node, CompilationUnit unit, String body, YoyooTypeDefine parent);
}
