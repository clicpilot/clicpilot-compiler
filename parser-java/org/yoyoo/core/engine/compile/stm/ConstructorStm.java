package org.yoyoo.core.engine.compile.stm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.exp.ArgumentsExpr;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooArguments;
import org.yoyoo.core.engine.parser.YoyooSuperExplicitConstructor;
import org.yoyoo.core.engine.parser.YoyooThisExplicitConstructor;
import org.yoyoo.core.engine.runtime.RuntimeContext;

public class ConstructorStm extends AbstractStm {

	private boolean thisExplicitConstructor;

	private boolean superExplicitConstructor;
	
	private ArgumentsExpr expr;
	
	public ConstructorStm(YoyooClassConstructor method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
	}

	@Override
	protected Operator convert2Operator() throws CompileException {
		IAtom paramAtom = expr.convert2Atom(expr.getNode());
		ReferenceAtom atom = null;
		if(thisExplicitConstructor)
			atom = new ReferenceAtom(this, RuntimeContext.THIS, node ,unit);
		else if(superExplicitConstructor)
			atom = new ReferenceAtom(this, RuntimeContext.SUPER, node ,unit);
		
		FuncCallOperator opt = new FuncCallOperator(node, unit, ycls, atom);
		opt.installArguments(new IAtom[]{paramAtom});
		
		if(this.getMethod()!=null && this.getMethod().getThrowsList()!=null) {
			List<IType> methodThrownExceptionTypes = new ArrayList<IType>();
			Iterator<ReferenceNameType> referenceNameTypeIterator = this.getMethod().getThrowsList().iterator();
			while(referenceNameTypeIterator.hasNext()) {
				methodThrownExceptionTypes.add(referenceNameTypeIterator.next());
			}
			opt.setMethodThrownExceptionTypes(methodThrownExceptionTypes);
		}
		
		return opt;
	}
	
	@Override
	public Object visit(YoyooArguments node, Object data) {
		expr = new ArgumentsExpr(unit, node, this);
		node.childrenAccept(expr, node);
		
		return null;
	}

	@Override
	public Object visit(YoyooSuperExplicitConstructor node, Object data) {
		superExplicitConstructor = true;
		return null;
	}

	@Override
	public Object visit(YoyooThisExplicitConstructor node, Object data) {
		thisExplicitConstructor = true;
		return null;
	}

	public boolean isThisExplicitConstructor() {
		return thisExplicitConstructor;
	}

	public boolean isSuperExplicitConstructor() {
		return superExplicitConstructor;
	}

	public ArgumentsExpr getExpr() {
		return expr;
	}


	

}

