package org.yoyoo.core.engine.compile.exp;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;

public class ReferenceNameExpr extends AbstractExpr {

	private String referenceName;
	
	private IAtom atom;
	
	public ReferenceNameExpr(CompilationUnit unit, SimpleNode node, Stm stm, String referenceName) {
		super(unit, node, stm);
		this.referenceName = referenceName;
	}

	@Override
	public Object myVisit(SimpleNode node, Object data) {
		return null;
	}

	@Override
	public IAtom convert2Atom(SimpleNode node) throws CompileException {
		atom = new ReferenceAtom(stm, referenceName, node, unit);
		return atom;
	}

	public String getReferenceName() {
		return referenceName;
	}

	

}
