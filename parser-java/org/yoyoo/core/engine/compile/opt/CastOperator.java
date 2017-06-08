package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.IValueAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class CastOperator extends AbstractOperator {

	protected IAtom atom;
	
	protected IType type;

	public CastOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IType type, IAtom atom) {
		super(node, unit, ycls);
		this.atom = atom;
		this.type = type;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		return atom.getVal(ctx);
	}

	public void installArguments(IAtom[] arguments) throws CompileException {
	}

	public IType operatorTypeCheck() throws CompileException {
		IType oldtype = atom.getYoyooType();
		if(atom instanceof IValueAtom) {
			IType assignedType = ((IValueAtom)atom).getAssignedType();
			if(assignedType!=null)
				oldtype = assignedType;
		}
		if(oldtype instanceof ReferenceNameType && (((ReferenceNameType)oldtype).isTypeParameter() || ((ReferenceNameType)oldtype).isObject(this.unit.getEnv()))) {
			return this.type;
		} else if(oldtype.isTypeOf(type, this.unit.getEnv()) || type.isTypeOf(oldtype, this.unit.getEnv())) {
			return this.type;
		} else if((oldtype instanceof ReferenceNameType && ((ReferenceNameType)oldtype).isYoyooInterface(this.unit.getEnv())) 
				|| (type instanceof ReferenceNameType && ((ReferenceNameType)type).isYoyooInterface(this.unit.getEnv())) ) {
				return this.type;
		} else if(PrimitiveType.isInteger(oldtype) && PrimitiveType.isShort(type) ) {
			return this.type;
		} else if(PrimitiveType.isShort(oldtype) && PrimitiveType.isInteger(type) ) {
			return this.type;
		} else {
			throw new CompileException.TypeMismatch(oldtype, type, node, unit);
		}
	}

}
