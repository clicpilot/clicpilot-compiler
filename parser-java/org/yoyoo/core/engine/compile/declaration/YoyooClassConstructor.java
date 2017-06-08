package org.yoyoo.core.engine.compile.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.stm.ConstructorStm;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooConstructorBody;
import org.yoyoo.core.engine.parser.YoyooConstructorName;
import org.yoyoo.core.engine.parser.YoyooExplicitConstructor;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class YoyooClassConstructor extends YoyooBaseMethod {

	private ConstructorStm constructorStm;
	
	public YoyooClassConstructor(YoyooTypeDefineClass unit, SimpleNode node) {
		super(unit, node);
		this.isAbstract = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooConstructorName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooConstructorName node, Object data) {
		this.name = (String) super.visit(node, data);
		return null;
	}

	@Override
	public Object visit(YoyooConstructorBody node, Object data) {
		node.childrenAccept(this, node);
		return null;
	}
	
	@Override
	public Object visit(YoyooExplicitConstructor node, Object data) {
		constructorStm = new ConstructorStm(this, null, node);
		node.childrenAccept(constructorStm, node);
		try {
			
			Operator operator = constructorStm.getOperator();
			addOperator(operator, constructorStm);

		} catch (CompileException e) {
			unit.getCompilationUnit().addError(e);
		}
		return null;
	}
	/*
	 * @Override public Object visit(YoyooBlockStatementBodyStm node, Object
	 * data) { Operator operator = null; StmVisitor v = new StmVisitor(this);
	 * node.childrenAccept(v, node); try { Stm stm = v.getStatement(); operator =
	 * v.getStatement().getOperator(node); addOperator(operator, stm);
	 *  } catch (CompileException e) { unit.getCompilationUnit().addError(e); }
	 * return operator; }
	 */

	@Override
	public void statmentCheck() {
		boolean terminated = false;
		if(stms!=null)
		{
			for(Stm stm : stms.keySet())
			{
				if(terminated)
				{
					this.unit.getCompilationUnit().addError(new CompileException.UnreachableCode( stm.getNode(), unit.getCompilationUnit()));
				}
				else if(stm.isTerminatedByReturnOrThrowStm())
				{
					terminated = true;
				}
			}
		}
		
	}

	@Override
	public void check() {
		
		super.check();
		if(!unit.getName().equals(this.getName())) {
			this.unit.getCompilationUnit().addError(new CompileException.InvalidConstructor( this.getName(), this.node, unit.getCompilationUnit()));
		} else if(this.isStaticMethod() && !this.getParameters().isEmpty()) {
			this.unit.getCompilationUnit().addError(new CompileException.StaticConstructorCannotHaveParam( this.getName(), this.node, unit.getCompilationUnit()));
		} else {
			if(this.staticMethod)
				this.getUnit().setStaticClassConstructor(this);
		}
	}

	
	public void execute(RuntimeContext ctx, IRuntimeValueAtom ref,
			IRuntimeValueAtom[] params, FuncCallOperator funcCaller) throws YoyooRTException {
		//boolean parentConstructorInvoked = false;
		
		boolean parentConstructorUsed = false;
		if(stms!=null) {
			Collection<Operator> opts = stms.values();
			int c = 0;			
			if(opts!=null && !opts.isEmpty()) {
				for (Operator opt : opts) {
					if(opt instanceof FuncCallOperator && ((FuncCallOperator)opt).isCallSuperConstructor() && c==0) {
						parentConstructorUsed = true;
					}
					break;
				}
			}
		}
		if(!parentConstructorUsed) {
			YoyooTypeDefineClass parentYClass = ((YoyooTypeDefineClass)unit.getParentYClass());
			if(unit.getParentYClass()!=null && (funcCaller!=null && !funcCaller.isCallSuperConstructor() && !funcCaller.isCallThisConstructor() )) {
				boolean constructorFound = false;
				Collection<YoyooClassConstructor> pconstructors = parentYClass.getConstructors().getSymbols().values();				
				YoyooClassConstructor pdefaultconstructor = null;
				for(YoyooClassConstructor pconstructor : pconstructors) {
					if(this.getParametersString().equals(pconstructor.getParametersString())) {
						constructorFound = true;
						pdefaultconstructor = pconstructor;
						break;
					}
					if(pconstructor.parameters.isEmpty()) {
						if(!constructorFound)
							pdefaultconstructor = pconstructor;
					}
				}
				if(!pconstructors.isEmpty() && pdefaultconstructor!=null) {
					pdefaultconstructor.execute(ctx, ref, params, funcCaller);
					//parentConstructorInvoked = true;
					
				}
			}
		}
		ctx.pushStackFrame(false);
		try{
			ctx.registerThis(ref, node, unit.getCompilationUnit());
			Set<String> paramNames = getParameters().getSymbols()
					.keySet();
			int j = 0;
			for (Iterator<String> i = paramNames.iterator(); i.hasNext();) {
				String paramName = i.next();
//					String mName = null;
//					IRuntimeValueAtom vAtom = null;
				switch (params[j].getRuntimeAtomType()) {
				case Ref:
					throw new YoyooRTException.CannotEval(node, unit.getCompilationUnit(), ctx);

				case Value:
					ctx.registerVariable(paramName, params[j], node, unit.getCompilationUnit());
					break;
				
				default:
					throw new YoyooRTException.CannotEval(node, unit.getCompilationUnit(), ctx);
				}
				j++;

			}
			super.execute(ctx, ref, params, funcCaller);
			
//			ctx.functionInvoked(funcCaller);
//			if (stms != null && !stms.isEmpty()) {
//				Collection<Operator> opts = stms.values();
//				OperatorList list = new OperatorList(node, unit
//						.getCompilationUnit(), unit);
//				for (Operator opt : opts) {
//					if(opt instanceof FuncCallOperator && ((FuncCallOperator)opt).isCallSuperConstructor() && parentConstructorInvoked)
//						continue;
//					list.addOperator(opt);
//				}
//				ctx.evaluateOperator(list);
//			}
//			ctx.functionReturned(funcCaller);
			
		} catch (Exception e){}
		finally {
			ctx.popStackFrame();
		}
		

	}
	
//	private Map<String, IRuntimeValueAtom> getArguments(RuntimeContext ctx, Set<String> src, Set<String> tar) throws YoyooRTException {
//		Map<String, IRuntimeValueAtom> map = new HashMap<String, IRuntimeValueAtom>();
//		for(String paramName : src) {
//			map.put(tar. value)p ctx.lookupAtom(paramName, node, unit.getCompilationUnit());
//		}
//	}
	
	public void execute(RuntimeContext ctx, YoyooTypeDefine typeInstance) throws YoyooRTException {
		
		if (stms != null && !stms.isEmpty()) {
			
			Collection<Operator> opts = stms.values();
			OperatorList list = new OperatorList(node, unit
					.getCompilationUnit(), unit);
			for (Operator opt : opts) {
				list.addOperator(opt);
			}
			
			ctx.pushStackFrame(false);
			ctx.registerThis(new RuntimeValueAtom(typeInstance, node, unit.getCompilationUnit()), node, unit.getCompilationUnit());
			ctx.evaluateOperator(list);
			ctx.popStackFrame();
		}

		
	}
	
	
	
	

}
