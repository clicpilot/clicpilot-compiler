package org.yoyoo.core.engine.compile.opt;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooException;


public class TryOperator extends OperatorList {

	private List<CatchOperator> catchOpt;

	private OperatorList finallyOpt;

	
	private transient List<IAtom> throwAtoms;

	private transient List<ReferenceNameType> exceptionTypes;
	
	private transient List<FuncCallOperator> funcOpts;
	
	private transient List<ThrowOperator> throwOpts;
	
	public TryOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, OperatorList bodyOpt, List<CatchOperator> catchOpt, OperatorList finallyOpt, List<ThrowOperator> throwOpts, List<FuncCallOperator> funcOpts) {
		super(node, unit, ycls);
		this.operators = bodyOpt.operators;
		this.catchOpt = catchOpt;
		this.finallyOpt = finallyOpt;
		this.throwOpts = throwOpts;
		this.funcOpts = funcOpts;
		setBefore(new PushStackFrameOperator(node, ycls
				.getCompilationUnit(), ycls, true));
		if(finallyOpt!=null) {
			OperatorList optList = new OperatorList(node, unit, ycls);
			optList.addOperator(finallyOpt);
			optList.addOperator(new PopStackFrameOperator(node, ycls
					.getCompilationUnit(), ycls));
			setAfter(optList);
		} else {
			setAfter(new PopStackFrameOperator(node, ycls
					.getCompilationUnit(), ycls));
		}
	}

	@Override
	public IType operatorTypeCheck() throws CompileException {
		super.operatorTypeCheck();
		if(finallyOpt!=null) {
			((AbstractOperator)finallyOpt).typeCheck();
		}
		for(CatchOperator opt : catchOpt) {
			opt.typeCheck();
		}
		
		if(funcOpts!=null) {
			for(FuncCallOperator funcOpt : funcOpts) {
				if(funcOpt.getThrowList()!=null) {
					if(exceptionTypes==null) {
						exceptionTypes = new ArrayList<ReferenceNameType>();
					}
					exceptionTypes.addAll(funcOpt.getThrowList());
				}
			}
		}
		if(throwOpts!=null) {
			for(ThrowOperator throwOpt : throwOpts) {
				if(throwAtoms==null) {
					throwAtoms = new ArrayList<IAtom>();
				}
				throwAtoms.add(throwOpt.getThrowAtom());
			}
		}
		
		cc:for(CatchOperator catchOperator : catchOpt) {			

			IType type = catchOperator.getException().getType();
			if(throwAtoms!=null) {
				for(IAtom atom : throwAtoms) {
					if(atom.getYoyooType().equalsTo(type)) {
						continue cc;
					}
				}
			}
			if(exceptionTypes!=null) {
				for(IType exceptionType : exceptionTypes) {
					if(exceptionType.equalsTo(type)) {
						continue cc;
					}
				}
			}
			throw new CompileException.UnreachableCode(catchOperator.getNode(), unit);
		}

		
		return new PrimitiveType.YoyooVoid(unit, node);
	}
	
	public boolean exceptionCaught(YoyooException exception, YoyooEnvironment env) {
		for(CatchOperator catchOperator : catchOpt) {
			if(exception.getYoyooClass() == catchOperator.getException().getType().map2YoyooClass(env)) {
				OperatorList optList = new OperatorList(node, unit, (YoyooTypeDefineClass)ycls);
				optList.addOperator(catchOperator);
				if(finallyOpt!=null) 
					optList.addOperator(finallyOpt);
				optList.addOperator(new PopStackFrameOperator(node, ((YoyooTypeDefineClass)ycls)
						.getCompilationUnit(), (YoyooTypeDefineClass)ycls));
				setAfter(optList);
				return true;
			}
		}
		return false;
	}

	@Override
	public void installArguments(IAtom[] arguments) throws CompileException {

		
	}

	@Override
	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
//		System.err.println("enter try:" + toSourceLocationString());
		return super.eval(ctx);
	}


	public List<CatchOperator> getCatchOpt() {
		return catchOpt;
	}

	public Operator getFinallyOpt() {
		return finallyOpt;
	}

	
	public void setThrowAtoms(List<IAtom> throwAtoms) {
		this.throwAtoms = throwAtoms;
	}

	public void setExceptionTypes(List<ReferenceNameType> exceptionTypes) {
		this.exceptionTypes = exceptionTypes;
	}


	

}
