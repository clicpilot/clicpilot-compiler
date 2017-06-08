package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooParserVisitor;

public interface Expr extends YoyooParserVisitor {
	public IAtom convert2Atom(SimpleNode node) throws CompileException;
	public SimpleNode getNode();
	
}
