package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.NullAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class FuncReturnOperator extends AbstractOperator {

	protected IAtom atom;

	protected YoyooMethod method;

	public FuncReturnOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom atom, YoyooMethod method) {
		super(node, unit, ycls);
		this.atom = atom;
		this.method = method;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		//System.out.println(method.getFullName()+" "+((ReferenceAtom)atom).getName()+" "+((ReferenceAtom)atom).getVal(ctx));
		
		
		ctx.functionReturned(atom.getVal(ctx));
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
		// TODO Auto-generated method stub

	}

	public IType operatorTypeCheck() throws CompileException {
		
		IType rtype = method.getReturnType();
		IType voidType = new PrimitiveType.YoyooVoid(this.unit, this.node);
		IType atype = atom==null?voidType:atom.getYoyooType();
		if(atom instanceof NullAtom) {
			return rtype;
		}
		else if (rtype.equalsTo(atype) || atype.isTypeOf(rtype, YoyooEnvironment.getDefault())) {
			return rtype;			
		} else {
			throw new CompileException.TypeMismatch(atype, method
					.getReturnType(), node, unit);
		}
	}

}
