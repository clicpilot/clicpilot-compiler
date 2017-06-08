package org.yoyoo.core.engine.yoyoo.lang.expr;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public abstract class AbstractSpecialExpr implements ISpecialExpr {
	
	protected SimpleNode node;
	
	protected CompilationUnit unit;
	
	protected String body;
	
	protected String prefix;
	
	protected YoyooTypeDefine parent;
	
	protected AbstractSpecialExpr()
	{}
	
	protected AbstractSpecialExpr(SimpleNode node, CompilationUnit unit, String body, YoyooTypeDefine parent) {
		super();
		this.node = node;
		this.unit = unit;
		this.body = body;
		this.parent = parent;
	}

	
}
