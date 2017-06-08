package org.yoyoo.core.engine.compile.atom;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public interface IAtom {
	public IRuntimeValueAtom getVal(RuntimeContext ctx) throws YoyooRTException;

	public IType getYoyooType() throws CompileException;


	public AtomType getAtomType();
	
	public SimpleNode getNode();

	public CompilationUnit getCompilationUnit();
}
