package org.yoyoo.core.engine.compile.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.stm.ILocalVariableStm;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.stm.StmVisitor;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooBlockStatementBodyStm;
import org.yoyoo.core.engine.parser.YoyooFormalParameter;
import org.yoyoo.core.engine.parser.YoyooFormalParameterName;
import org.yoyoo.core.engine.parser.YoyooFormalParameterType;
import org.yoyoo.core.engine.parser.YoyooFormalParameters;
import org.yoyoo.core.engine.parser.YoyooLocalVariableDeclaration;
import org.yoyoo.core.engine.parser.YoyooStatic;
import org.yoyoo.core.engine.parser.YoyooThrowName;
import org.yoyoo.core.engine.parser.YoyooThrowsNameList;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public abstract class YoyooBaseMethod extends AbstractDeclaration implements
		ILocalVariableStm {

	protected SymbolTable<FormalParameter> parameters;

	private FormalParameter currentParameter;

	// private SymbolTable<VariableDeclaration> localVariables;

	// protected OperatorList opt;

	// protected Operator prevOpt;

	protected Map<Stm, Operator> stms;

	protected boolean staticMethod;
	
	protected List<ReferenceNameType> throwsList; 

	protected boolean isAbstract = true;


	protected Stack<Stm> stmCompileStack = new Stack<Stm>();


	// public void addLocalVariableDeclaration(VariableDeclaration decl,
	// SimpleNode node)
	// {
	// if(localVariables == null)
	// localVariables = new SymbolTable<VariableDeclaration>();
	// try {
	// localVariables.putSymbol(decl.name, decl, node,
	// unit.getCompilationUnit());
	// } catch (CompileException e) {
	// unit.getCompilationUnit().addError(e);
	// }
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooLocalVariableDeclaration,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooLocalVariableDeclaration node, Object data) {
		YoyooVariable decl = new YoyooVariable(unit, node);
		node.childrenAccept(decl, node);
		// addLocalVariableDeclaration(decl, node);
		registerNewLocalVariable(decl, node);
		if(this.parameters.contains(decl.getName())) {
			unit.getCompilationUnit().addError(new CompileException.DuplicateDefined(decl.getName(), node, unit.getCompilationUnit()));
		}
		
		try {
			Operator operator = decl.convert2Operator(node);
			addOperator(operator, decl);
		} catch (CompileException e) {
			unit.getCompilationUnit().addError(e);
		}
		return decl;
	}

	public Object visit(YoyooBlockStatementBodyStm node, Object data) {
		Operator operator = null;
		StmVisitor v = new StmVisitor(this, null, node);
		node.childrenAccept(v, node);
		Stm stm = v.getStatement();

		try {			
			operator = v.getStatement().getOperator();
			addOperator(operator, stm);

		} catch (CompileException e) {
			addOperator(null, stm);
			unit.getCompilationUnit().addError(e);
		}
		return operator;
	}

	protected void addOperator(Operator operator, Stm stm) {
		if (stms == null)
			stms = new LinkedHashMap<Stm, Operator>();
		stms.put(stm, operator);
		// if(opt==null)
		// {
		// opt = new OperatorList(node, unit.getCompilationUnit());
		// }
		// opt.addOperator(operator);
	}

	public YoyooBaseMethod(YoyooTypeDefineClass unit, SimpleNode node) {
		super(unit, node);
		this.parameters = new SymbolTable<FormalParameter>();
		this.throwsList = new ArrayList<ReferenceNameType>();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooFormalParameterName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooFormalParameterName node, Object data) {
		if (data instanceof YoyooFormalParameter) {
			String name = (String) super.visit(node, data);
			try {
				currentParameter.setName(name);
				parameters.putSymbol(name, currentParameter, node, unit
						.getCompilationUnit());
			} catch (CompileException e) {
				unit.getCompilationUnit().addError(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooFormalParameters,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooFormalParameters node, Object data) {
		node.childrenAccept(this, node);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.parser.YoyooParserVisitor#visit(org.yoyoo.core.engine.parser.YoyooFormalParameter,
	 *      java.lang.Object)
	 */
	public Object visit(YoyooFormalParameter node, Object data) {
		if (data instanceof YoyooFormalParameters) {
			currentParameter = new FormalParameter();
			node.childrenAccept(this, node);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooFormalParameterType,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooFormalParameterType node, Object data) {
		if (data instanceof YoyooFormalParameter) {
			TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
			node.childrenAccept(v, data);
			currentParameter.setType(v.getType());
		}
			
		return null;
	}
/*

	@Override
	public Object visit(YoyooPrimitiveType node, Object data) {
		if (data instanceof YoyooFormalParameterType) {
			IType type = (IType) super.visit(node, data);
			currentParameter.setType(type);
		}
		return super.visit(node, data);
	}

	@Override
	public Object visit(YoyooReferenceNameType node, Object data) {
		if (data instanceof YoyooFormalParameterType) {
			IType type = (IType) super.visit(node, data);
			currentParameter.setType(type);
		}
		return super.visit(node, data);
	}
*/

	@Override
	public Object visit(YoyooThrowName node, Object data) {
		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		v.visit(node, data);
		throwsList.add((ReferenceNameType)v.getType());
		//ReferenceNameType type = new ReferenceNameType(unit.getCompilationUnit(), node);
		//node.jjtAccept(type, data);
		//throwsList.add(type);
		return null;
	}

	@Override
	public Object visit(YoyooThrowsNameList node, Object data) {
		return node.childrenAccept(this, node);
	}
	
	@Override
	public Object visit(YoyooStatic node, Object data) {
		staticMethod = true;
		return null;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
		// Map<String, FormalParameter> map = this.parameters.getSymbols();
		// StringBuffer sb = new StringBuffer(name);
		// sb.append("(");
		// boolean first = true;
		// for(Iterator<FormalParameter> i =
		// map.values().iterator();i.hasNext();)
		// {
		// if(first)
		// {
		// first = false;
		// }
		// else
		// {
		// sb.append(", ");
		// }
		//			
		// sb.append(i.next().getType().getName());
		// }
		// sb.append(")");
		// return sb.toString();
	}
	
	public String getFullName() {
		 //Map<String, FormalParameter> map = this.parameters.getSymbols();
		 StringBuffer sb = new StringBuffer(name);
		 sb.append( getParametersString());
		 return sb.toString();
		
	}
	
	public String getParametersString() {
		 Map<String, FormalParameter> map = this.parameters.getSymbols();
		 StringBuffer sb = new StringBuffer();
		 sb.append("(");
		 boolean first = true;
		 for(Iterator<FormalParameter> i = map.values().iterator();i.hasNext();)
		 {
			 if(first) {
				 first = false;
			 } else
			 {
				 sb.append(", ");
			 }
			 FormalParameter param = i.next();
			 sb.append(param.getType().getName());
			 if(param.getType().isArray()) {
				 sb.append("[]");
			 }
		 }
		 sb.append(")");
		 return sb.toString();
		
	}

	/**
	 * @return the parameters
	 */
	public SymbolTable<FormalParameter> getParameters() {
		return parameters;
	}

	public FormalParameter getParameter(String name) {
		return parameters.getSymbol(name);
	}

	/**
	 * @return the modifier
	 */
	public Modifier getModifier() {
		return modifier;
	}

	/**
	 * @return the unit
	 */
	public YoyooTypeDefineClass getUnit() {
		return unit;
	}

	// public SymbolTable<VariableDeclaration> getLocalVariables() {
	// return localVariables;
	// }
	public Set<Stm> getStms() {
		return stms == null ? null : stms.keySet();
	}

	public Operator getStmOperator(Stm stm) {
		return stms == null ? null : stms.get(stm);
	}

	public void execute(RuntimeContext ctx, IRuntimeValueAtom ref,
			IRuntimeValueAtom[] params, FuncCallOperator funcCaller) throws YoyooRTException {
		ctx.functionInvoked(funcCaller);
		if (stms != null && !stms.isEmpty()) {
			Collection<Operator> opts = stms.values();
			OperatorList list = new OperatorList(node, unit
					.getCompilationUnit(), unit);
			for (Operator opt : opts) {
				list.addOperator(opt);
			}
			ctx.evaluateOperator(list);
		}
		ctx.functionReturned(funcCaller);
		
	}

	// void refillStopOperator()
	// {
	// if(helper.gotoStms!=null)
	// for(StopStm stopStm : helper.gotoStms)
	// {
	// stopStm.refillOperator();
	// }
	// }

	public void closeLocalVariableStack() {
		this.unit.getCompilationUnit().popLocalVariableStack();

	}

	public void openLocalVariableStack() {
		this.unit.getCompilationUnit().pushNewLocalVariableStack();

	}

	public void registerNewLocalVariable(YoyooVariable decl, SimpleNode node) {
		try {
			this.unit.getCompilationUnit().registerNewLocalVariable(decl, node);
		} catch (CompileException e) {
			this.unit.getCompilationUnit().addError(e);
		}

	}
	
	public abstract void statmentCheck();

	public void check() {
		
		if(!isAbstract) {
			
			if (stms != null && !stms.keySet().isEmpty()) {
				Set<Stm> stmSet = stms.keySet();
				for(Stm stm : stmSet) {					
					Operator opt = stms.get(stm);
					try {
						if(opt!=null) {
							opt.typeCheck();
						} else {							
							System.out.println("opt==null:"+this.name+stm);
						}
					} catch (CompileException e) {
						this.unit.getCompilationUnit().addError(e);
					}					
				}
				statmentCheck();
			}
		}
		
	}

	public boolean isStaticMethod() {
		return staticMethod;
	}
	
	public void pushStmCompilingStack(Stm stm) {

		this.stmCompileStack.push(stm);
	}
	
	public Stm popStmCompilingStack() {
		Stm stm = this.stmCompileStack.pop();
		
		return stm;
	}

	public Stack<Stm> getStmCompileStack() {
		return stmCompileStack;
	}
	
	
	public List<ReferenceNameType> getThrowsList() {
		return throwsList;
	}
	

	public boolean isAbstract() {
		return isAbstract;
	}

}
