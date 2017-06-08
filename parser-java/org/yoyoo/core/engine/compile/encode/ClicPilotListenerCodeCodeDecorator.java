package org.yoyoo.core.engine.compile.encode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.atom.ValueAtom;
import org.yoyoo.core.engine.compile.declaration.IDeclaration;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.encode.AbstractCodeDecorator.KeyWord;
import org.yoyoo.core.engine.compile.exp.ArgumentsExpr;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.PrimaryExpr;
import org.yoyoo.core.engine.compile.exp.ReferenceNameExpr;
import org.yoyoo.core.engine.compile.opt.BracketOperator;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.stm.StmExpr;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;



public class ClicPilotListenerCodeCodeDecorator extends JavaCodeDecorator {

	
	private Map<String, String[]> actions;

	
	public Map<String, String[]> getActions() {
		return actions;
	}

	public void setActions(Map<String, String[]> actions) {
		this.actions = actions;
	}

	@Override
	public void encode(YoyooTypeDefineClass decl) {

		this.unit = decl.getCompilationUnit();
		

		
		SymbolTable<YoyooMethod> methods = decl.getMethods();
		for(YoyooMethod method : methods.getSymbols().values()) {
			if(method.getName().equals("onStart"))
				encode((IDeclaration)method);
		}
		
		
	}
	
	@Override
	public void encode(YoyooMethod decl) {

		
		appendMethodBody(decl);
		

	}
	
	@Override
	public void encode(StmExpr stm) {
		IAtom atom = stm.getAtom(); 
		if(atom!=null && (atom instanceof FuncCallOperator)) {
			 String methodName = ((FuncCallOperator)atom).getMethodName();
			 IAtom[] args = ((FuncCallOperator)atom).getArgs();
			 if(("invokeAction".equals(methodName)||"invokeQuery".equals(methodName) ) && args.length==2 && (args[0] instanceof ValueAtom) && (args[1] instanceof ValueAtom)) {
				 ValueAtom arg1 = (ValueAtom)args[0];
				 ValueAtom arg2 = (ValueAtom)args[1];
				 YoyooObject obj1 = arg1.getValue();
				 YoyooObject obj2 = arg2.getValue();
				 if((obj1 instanceof YoyooString) && (obj2 instanceof YoyooString)) {
					 String mdl = ((YoyooString)obj1).getVal();
					 String action = ((YoyooString)obj2).getVal();
					 append("execute_");
					 append(mdl+"_"+action);
					 appendParamParenthesesStart();
					 append("ctx");
					 appendParamParenthesesEnd();
					 appendStatementEnd();
					 appendLineBreak();
					 if(actions!=null)
						 actions.put(mdl+"_"+action, new String[]{mdl, action, "invokeAction".equals(methodName)?"Action":"Query"});
					 return;
					 
				 }
			 }
			 
		}
		
		super.encode(stm);
	}
	
	
	
	
}
