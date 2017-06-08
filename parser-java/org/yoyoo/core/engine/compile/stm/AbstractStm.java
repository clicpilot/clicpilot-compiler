package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.parser.SimpleNode;

public abstract class AbstractStm extends AbstractVisitor implements Stm {


	protected YoyooBaseMethod method;
	
	protected CatchStatement catchStm;

	// protected Block parent;
	protected YoyooTypeDefineClass ycls;

	// protected Map<Stm, Operator> children;
	private Operator operator;

	protected abstract Operator convert2Operator() throws CompileException;
	
	protected SymbolTable<YoyooVariable> variableTable;
	
	protected boolean terminatedByReturnOrThrowStm;
	
	private boolean compiled = true;

	public boolean isCompiled() {
		return compiled;
	}

	public AbstractStm(YoyooBaseMethod method, CatchStatement catchStm, SimpleNode node) {
		super(method.getUnit().getCompilationUnit(),node);

		this.method = method;
		
		this.catchStm = catchStm;

		// if(this.parent != null && this.parent.children ==null)
		// {
		// this.parent.children = new LinkedHashMap<Stm, Operator>();
		// this.parent.children.put(this, null);
		// }
		this.ycls = method.getUnit();
		this.variableTable = this.unit.getCurrentLocalVariableTable();
		this.terminatedByReturnOrThrowStm = false;
		
	}

	// public Block parent() {
	// return parent;
	// }
	//	
	// public Set<Stm> children() {
	// // TODO Auto-generated method stub
	// return children.keySet();
	// }
	/*
	 * public Stm nextSlide() { if(parent==null) { if(method.getStms()!=null) {
	 * int i = method.getStms().indexOf(this); if(method.getStms().size()>i+1) {
	 * return method.getStms().get(i+1); } else { return null; } } else { return
	 * null; } } else { if(parent.children == null) { return null; } else { int
	 * i = parent.children.indexOf(this); if(parent.children.size()>i+1) {
	 * return parent.children.get(i+1); } else { return null; } } } }
	 */
	// public Stm prevSlide() {
	// if(parent==null)
	// {
	// if(method.getStms()!=null)
	// {
	// Set<Stm> stms = method.getStms();
	// if(stms.contains(this))
	// {
	// Stm lastStm = null;
	// for(Iterator<Stm> i = stms.iterator(); i.hasNext();)
	// {
	// Stm stm = i.next();
	// if(stm == this)
	// {
	// return lastStm;
	// }
	// else
	// {
	// lastStm = stm;
	// }
	// }
	// return null;
	// }
	// else
	// {
	// return null;
	// }
	// }
	// else
	// {
	// return null;
	// }
	// }
	// else
	// {
	// if(parent.children == null)
	// {
	// return null;
	// }
	// else
	// {
	// Set<Stm> stms = parent.children();
	// if(stms.contains(this))
	// {
	// Stm lastStm = null;
	// for(Iterator<Stm> i = stms.iterator(); i.hasNext();)
	// {
	// Stm stm = i.next();
	// if(stm == this)
	// {
	// return lastStm;
	// }
	// else
	// {
	// lastStm = stm;
	// }
	// }
	// return null;
	// }
	// else
	// {
	// return null;
	// }
	// }
	// }
	// }
	// protected Operator findNextLinkOperator(SimpleNode node) throws
	// CompileException
	// {
	// if(this.parent() == null)
	// return null;
	// else
	// return this.parent().convert2Operator(node);
	// }
	// public Map<Stm, Operator> getChildren() {
	// return children;
	// }
	//
	// public Block getParent() {
	// return parent;
	// }
	public Operator getOperator() throws CompileException {
		//method.getUnit().getCompilationUnit().setStmInCompiling(this);
		if (operator == null) {			
			operator = this.convert2Operator();	
			compiled = true;
		}
		
		//method.getUnit().getCompilationUnit().setStmInCompiling(this);
		return operator;

	}

	public SimpleNode getNode() {
		return node;
	}

	public void pushOperator(Operator opt) {
		this.unit.pushOperator(opt);
	}

	public Operator popOperator() {
		return this.unit.popOperator();
	}

	public YoyooBaseMethod getMethod() {
		return method;
	}
	
	public CatchStatement getCatchStm() {
		return this.catchStm;
	}


	public YoyooTypeDefineClass getYcls() {
		return ycls;
	}

	public SymbolTable<YoyooVariable> getVariableTable() {
		// TODO Auto-generated method stub
		return variableTable;
	}
	// protected void refillOperator(Operator opt)
	// {
	// if(this.operator instanceof GotoOperatorOperator)
	// {
	// ((GotoOperatorOperator)this.operator).setOpt(opt);
	// }
	// }

	public boolean isTerminatedByReturnOrThrowStm() {
		return terminatedByReturnOrThrowStm;
	}

	public void setTerminatedByReturnOrThrowStm(boolean terminatedByReturnOrThrowStm) {
		this.terminatedByReturnOrThrowStm = terminatedByReturnOrThrowStm;
	}

	@Override
	public void typeCheck() throws CompileException {
		
		
	}

}
