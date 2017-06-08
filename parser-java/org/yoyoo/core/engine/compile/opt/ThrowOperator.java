package org.yoyoo.core.engine.compile.opt;

import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class ThrowOperator extends AbstractOperator {

	private IAtom throwAtom;



	private transient List<IType> catchExceptionTypes;
	
	private transient List<IType> methodThrownExceptionTypes;
	



	public ThrowOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IAtom throwAtom, List<IType> catchExceptionTypes, List<IType> methodThrownExceptionTypes) {
		super(node, unit, ycls);
		this.throwAtom = throwAtom;
		this.catchExceptionTypes = catchExceptionTypes;
		this.methodThrownExceptionTypes = methodThrownExceptionTypes;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		ctx.exceptionThrown(this, throwAtom.getVal(ctx));
		//System.err.println("exception throw:" + throwAtom.getVal(ctx));
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {


	}
	
	
	public IAtom getThrowAtom() {
		return throwAtom;
	}


	public IType operatorTypeCheck() throws CompileException {
		
		IType exceptionType = throwAtom.getYoyooType();
		boolean foundHandler = false;
		if(catchExceptionTypes!=null) {
			for(IType type:catchExceptionTypes) {
				if(exceptionType.equalsTo(type)) {
					foundHandler = true;
					break;
				}
				
			}
		}
		if(!foundHandler && methodThrownExceptionTypes!=null) {
			for(IType type:methodThrownExceptionTypes) {
				if(exceptionType.equalsTo(type)) {
					foundHandler = true;
					break;
				}
				
			}
		}
		if(foundHandler)
			return new PrimitiveType.YoyooVoid(unit, node);
		else
			throw new CompileException.UnhandledException(exceptionType.getName(), node, unit);
	}


	
	
}
