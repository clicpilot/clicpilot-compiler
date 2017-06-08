package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.stm.EmptyStm;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooBlock;
import org.yoyoo.core.engine.parser.YoyooMedthodDeclBody;
import org.yoyoo.core.engine.parser.YoyooMethodName;
import org.yoyoo.core.engine.parser.YoyooResultType;

public class YoyooMethod extends YoyooBaseMethod {





	public YoyooMethod(YoyooTypeDefineClass unit, SimpleNode node) {
		super(unit, node);
	}

	private IType returnType;
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooResultType,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooResultType node, Object data) {
		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		node.childrenAccept(v, data);
		this.returnType = v.getType();
		return super.visit(node, data);
	}

/*
	@Override
	public Object visit(YoyooPrimitiveType node, Object data) {
		if (data instanceof YoyooResultType) {
			this.returnType = (IType) super.visit(node, data);
		} else {
			super.visit(node, data);
		}
		return null;
	}


	@Override
	public Object visit(YoyooReferenceNameType node, Object data) {
		if (data instanceof YoyooResultType) {
			this.returnType = (IType) super.visit(node, data);
		} else {
			super.visit(node, data);
		}
		return null;
	}


	@Override
	public Object visit(YoyooVoidType node, Object data) {
		if (data instanceof YoyooResultType) {
			this.returnType = (IType) super.visit(node, data);
		} else {
			super.visit(node, data);
		}
		return null;
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.stm.AbstractStm#visit(org.yoyoo.core.engine.parser.YoyooMethodName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooMethodName node, Object data) {
		name = (String) super.visit(node, data);
		return null;
	}

	/**
	 * @return the returnType
	 */
	public IType getReturnType() {
		return returnType;
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
	public Object visit(YoyooBlock node, Object data) {
		node.childrenAccept(this, node);
		return null;
	}

	@Override
	public Object visit(YoyooMedthodDeclBody node, Object data) {
		node.childrenAccept(this, node);
		this.isAbstract = false;
		return null;
	}

	@Override
	public void statmentCheck() {
		boolean terminated = false;
		if(stms!=null)
		{
			for(Stm stm : stms.keySet())
			{
				
				if(terminated && !(stm instanceof EmptyStm)) {
					this.unit.getCompilationUnit().addError(new CompileException.UnreachableCode( stm.getNode(), unit.getCompilationUnit()));
				} else if(stm.isTerminatedByReturnOrThrowStm()) {
					terminated = true;
				}
			}
		}
		if(!terminated && !PrimitiveType.isVoid(returnType))
		{
			this.unit.getCompilationUnit().addError(new CompileException.MethodNoReturn(returnType, node, unit.getCompilationUnit()));
		}
		
	}
	
	public boolean accessibilityCheck(YoyooTypeDefineClass yclass) throws CompileException {
		switch(this.modifier) {			
			case PRIVATE:
				if(yclass==this.unit)
					return true;
				else
					throw new CompileException.CannotAccessMethod(this.getName(), node, yclass.getCompilationUnit());
			case PROTECTED:
				if(this.unit.isDescendant(yclass) || yclass.isDescendant(this.unit) || this.unit.getCompilationUnit().getPackageName().equals(yclass.getCompilationUnit().getPackageName()))
					return true;
				else
					throw new CompileException.CannotAccessMethod(this.getName(), node, yclass.getCompilationUnit());
			case PUBLIC:
				return true;
			default:
			case DEFAULT:
				if(this.unit.getCompilationUnit().getPackageName().equals(yclass.getCompilationUnit().getPackageName()))
					return true;
				else	
					throw new CompileException.CannotAccessMethod(this.getName(), node, yclass.getCompilationUnit());
				
		}
	}

}
