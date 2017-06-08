package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.opt.FieldAssignOperator;
import org.yoyoo.core.engine.compile.opt.IOverrideAssignOpt;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooFinal;
import org.yoyoo.core.engine.parser.YoyooStatic;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class YoyooField extends YoyooVariable {

	private boolean staticField; 
	
	private boolean finalField; 




	private FieldAssignOperator fieldAssignOperator;
	
	public void eval(RuntimeContext ctx) throws YoyooRTException {
		//if(!staticAndInitialized && fieldAssignOperator!=null) {

		ctx.evaluateOperator(fieldAssignOperator);
			
		//}
	}

	@Override
	public Object visit(YoyooStatic node, Object data) {
		staticField = true;
		return null;
	}
	
	@Override
	public Object visit(YoyooFinal node, Object data) {
		finalField = true;
		return null;
	}
	
	@Override
	public Operator convert2Operator(SimpleNode node) throws CompileException {

		if(this.assignExpr!=null)
			assignAtom = this.assignExpr.convert2Atom(assignExpr.getNode());
		fieldAssignOperator = new FieldAssignOperator(node, unit.getCompilationUnit(), unit,
				type, assignAtom, name, this.staticField);
		return fieldAssignOperator;
	}

	public YoyooField(YoyooTypeDefineClass unit, SimpleNode node) {
		super(unit, node);
		this.method = null;
		
	}

	public void typeCheck() {
		
		IType assginType;
		try {
			convert2Operator(node);
			if (assignAtom != null) {
			
				if(assignAtom instanceof IOverrideAssignOpt){
					((IOverrideAssignOpt)assignAtom).convert(type);
				}
				
				assginType = assignAtom.getYoyooType();
				if (!type.equalsTo(assginType)) {
					this.unit.getCompilationUnit().addError(
							new CompileException.TypeMismatch(assginType, type,
									node, unit.getCompilationUnit()));
				}
			}
		} catch (CompileException e) {
			this.unit.getCompilationUnit().addError(e);
		}

	}

	public boolean isStaticField() {
		return staticField;
	}
	
	public void setStaticField() {
		this.staticField = true;
	}
	
	
	public boolean isFinalField() {
		return finalField;
	}
	



	public void setFinalField(boolean finalField) {
		this.finalField = finalField;
	}
	
	public boolean accessibilityCheck(YoyooTypeDefineClass yclass) throws CompileException {
		switch(this.modifier) {			
			case PRIVATE:
				if(yclass==this.unit)
					return true;
				else
					throw new CompileException.CannotAccessField(this.getName(), node, yclass.getCompilationUnit());
			case PROTECTED:
				if(yclass.isDescendant(this.unit) || this.unit.getCompilationUnit().getPackageName().equals(yclass.getCompilationUnit().getPackageName()))
					return true;
				else
					throw new CompileException.CannotAccessField(this.getName(), node, yclass.getCompilationUnit());
			case PUBLIC:
				return true;
			default:
			case DEFAULT:
				if(this.unit.getCompilationUnit().getPackageName().equals(yclass.getCompilationUnit().getPackageName()))
					return true;
				else	
					throw new CompileException.CannotAccessField(this.getName(), node, yclass.getCompilationUnit());
				
		}
	}
	


}
