package org.yoyoo.core.engine.compile.encode;

import java.io.IOException;
import java.util.List;

import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.NullAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.atom.ValueAtom;
import org.yoyoo.core.engine.compile.declaration.IDeclaration;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.compile.exp.AllocationExpr;
import org.yoyoo.core.engine.compile.exp.ArgumentsExpr;
import org.yoyoo.core.engine.compile.exp.ArrayIndexExpr;
import org.yoyoo.core.engine.compile.exp.ArrayInitExpr;
import org.yoyoo.core.engine.compile.exp.BinOpExpr;
import org.yoyoo.core.engine.compile.exp.BracketExpr;
import org.yoyoo.core.engine.compile.exp.CastExpr;
import org.yoyoo.core.engine.compile.exp.CondExpr;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.PostfixExpr;
import org.yoyoo.core.engine.compile.exp.PrimaryExpr;
import org.yoyoo.core.engine.compile.exp.ReferenceNameExpr;
import org.yoyoo.core.engine.compile.opt.BracketOperator;
import org.yoyoo.core.engine.compile.stm.Block;
import org.yoyoo.core.engine.compile.stm.BreakStm;
import org.yoyoo.core.engine.compile.stm.CatchStatement;
import org.yoyoo.core.engine.compile.stm.ConstructorStm;
import org.yoyoo.core.engine.compile.stm.ContinueStm;
import org.yoyoo.core.engine.compile.stm.DoStm;
import org.yoyoo.core.engine.compile.stm.EmptyStm;
import org.yoyoo.core.engine.compile.stm.ForStm;
import org.yoyoo.core.engine.compile.stm.IfStm;
import org.yoyoo.core.engine.compile.stm.LabeledStm;
import org.yoyoo.core.engine.compile.stm.ReturnStm;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.stm.StmExpr;
import org.yoyoo.core.engine.compile.stm.StmList;
import org.yoyoo.core.engine.compile.stm.StopStm;
import org.yoyoo.core.engine.compile.stm.SwitchStm;
import org.yoyoo.core.engine.compile.stm.SwitchStm.CaseStm;
import org.yoyoo.core.engine.compile.stm.SynchronizedStatement;
import org.yoyoo.core.engine.compile.stm.ThrowStatement;
import org.yoyoo.core.engine.compile.stm.TryStatement;
import org.yoyoo.core.engine.compile.stm.WhileStm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;



public class DefaultCodeDecorator extends AbstractCodeDecorator {

	
	public DefaultCodeDecorator() {
		super();
		this.indent = 0;
	}


	
	
	@Override
	public void encode(YoyooMethod decl) {

		
		appendLineBreak();
		appendTab(indent);
		appendModifier(decl);
		if(decl.isStaticMethod()) {
			appendKeyWord(KeyWord.KeyStatic);
			appendSpace();
		}
		

		
		appendReturnType(decl.getReturnType());
		appendSpace();
		append(decl.getName());		
		appendParamParenthesesStart();
		
		SymbolTable<FormalParameter>  params = decl.getParameters();
		int paramCount = params.getSymbols().size();
		for(FormalParameter param : params.getSymbols().values()) {
			appendParamType(param.getType());
			appendSpace();
			append(param.getName());
			if(paramCount>1) {
				appendParamSep();
				appendSpace();
			}
			paramCount--;
		}
		
		appendParamParenthesesEnd();
		
//		if(decl.getThrowsList()!=null && !decl.getThrowsList().isEmpty()) {
//			appendKeyWord(KeyWord.KeyThrows);
//			appendSpace();
//			List<ReferenceNameType> exceptionTypes = decl.getThrowsList();
//			int exceptionTypesCount = exceptionTypes.size();
//			for(ReferenceNameType exceptionType : exceptionTypes) {
//				appendTypeName(exceptionType);
//				if(exceptionTypesCount>1) {
//					appendParamSep();
//					appendSpace();
//				}
//				exceptionTypesCount--;
//				
//			}
//		}
		
		appendMethodThrows(decl);
		
		if(!decl.isAbstract()) {
		
			appendStatementBracketStart();		
			appendLineBreak();
			
			appendMethodBody(decl);
			
			appendLineBreak();
			appendTab(indent);
			appendStatementBracketEnd();
		} else {
			appendStatementEnd();
		}
		appendLineBreak();
		

	}

	protected void appendMethodBody(YoyooMethod decl) {
		indent++;
		String methodName = decl.getName();
		if(methodName.startsWith("object2json")) {
			int i=0;
		}
		if(decl.getStms()!=null) {
			for(Stm stm : decl.getStms()) {
				appendTab(indent);
				encode(stm);
				if(stm instanceof YoyooVariable) {
					appendStatementEnd();
				} else if(stm instanceof StmExpr) {
					appendStatementEnd();
				}
				appendLineBreak();
			}
		}
		indent--;
		
	}
	
	protected void appendMethodBody(YoyooClassConstructor decl) {
		indent++;
		if(decl.getStms()!=null) {
			for(Stm stm : decl.getStms()) {
				appendTab(indent);
				encode(stm);
				if(stm instanceof YoyooVariable) {
					appendStatementEnd();
				} else if(stm instanceof StmExpr) {
					appendStatementEnd();
				}
				appendLineBreak();
			}
		}
		indent--;
		
	}
	
	

	@Override
	public void encode(YoyooClassConstructor decl) {		

		appendTab();
		appendModifier(decl);
		if(decl.isStaticMethod()) {
			appendKeyWord(KeyWord.KeyStatic);
		}
		appendSpace();
		append(decl.getName());		
		appendParamParenthesesStart();
		
		SymbolTable<FormalParameter>  params = decl.getParameters();
		int paramCount = params.getSymbols().size();
		for(FormalParameter param : params.getSymbols().values()) {
			appendParamType(param.getType());
			appendSpace();
			append(param.getName());
			if(paramCount>1) {
				appendParamSep();
				appendSpace();
			}
			paramCount--;
		}
		
		appendParamParenthesesEnd();
		
		appendMethodThrows(decl);
		
		appendStatementBracketStart();		
		appendLineBreak();
		appendMethodBody(decl);
		appendLineBreak();
		appendTab();
		appendStatementBracketEnd();
		appendLineBreak();

	}
	
	

	@Override
	public void encode(YoyooTypeDefineClass decl) {

		this.unit = decl.getCompilationUnit();
		encodeDecl(decl);	
		appendKeyWord(KeyWord.KeyPackage);
		appendSpace();
		append(this.unit.getPackageName());
		appendStatementEnd();
		appendLineBreak();
		appendLineBreak();
		
		appendImports();		
		
		appendModifier(decl);
		if(decl.isAbstract()) {
			appendKeyWord(KeyWord.KeyAbstract);
			appendSpace();
		}
		
		append(decl.getTypeName());
		appendSpace();
		append(decl.getName());
		
		if(decl.getExtendsReferenceType()!=null) {
			String extendsName = decl.getExtendsReferenceType().getName();
			if(extendsName!=null) {
				appendSpace();
				appendKeyWord(KeyWord.KeyExtends);
				appendSpace();
				appendType(decl.getExtendsReferenceType());
				
			}
		}
		
		if(decl.getImlpmentsReferenceNames()!=null) {
			appendSpace();
			appendKeyWord(KeyWord.KeyImplements);
			appendSpace();
			List<ReferenceNameType> implTypes = decl.getImlpmentsReferenceNames();
			int i=implTypes.size();
			for(ReferenceNameType implType : implTypes) {
				append(implType.getName());
				if(i>1)
					appendParamSep();
				i--;
			}
		}
		appendSpace();
		appendStatementBracketStart();		
		appendLineBreak();	
		
		appendLineBreak();
		indent = 1;
		SymbolTable<YoyooField> fields = decl.getFields();
		for(YoyooField field : fields.getSymbols().values()) {
			appendTab(indent);
			encode((IDeclaration)field);
			appendLineBreak();
		}

		
		SymbolTable<YoyooMethod> methods = decl.getMethods();
		for(YoyooMethod method : methods.getSymbols().values()) {
			if(method!=null)
				encode((IDeclaration)method);
		}
		
		SymbolTable<YoyooClassConstructor> constructors = decl.getConstructors();
		for(YoyooClassConstructor constructor : constructors.getSymbols().values()) {
			if(constructor!=null)
				encode((IDeclaration)constructor);
		}
		
		appendLineBreak();	
		
		appendStatementBracketEnd();
		appendLineBreak();
	}


	@Override
	public void encode(ReferenceAtom atom) {
		append(atom.getName());
	}


	@Override
	public void encode(ValueAtom atom) {
		switch(atom.getAtomType()) {
			case Value:
				if(atom instanceof NullAtom)
					appendNull();
				else
					encode(atom.getValue());
				break;
			default:
				append(atom.toString());
				break;
		}
		
		
	}

	@Override
	public void encode(AllocationExpr expr) {
		appendKeyWord(KeyWord.KeyNew);
		appendSpace();
		appendType(expr.getType());
		if(expr.isArray()) {
			
			List<Expr> arguments = expr.getArrayDimExprs();
			if(arguments!=null) {
				for(int i=0;i<arguments.size();i++) {
					appendArrayBracketStart();
					encode(arguments.get(i));	
					appendArrayBracketEnd();
				}
			} else {
				for(int i=0;i<expr.getType().getArrayDim();i++) {
					appendArrayBracketStart();
					appendArrayBracketEnd();
				}
			}
			
			ArrayInitExpr initExpr = expr.getArrayInitExpr();
			if(initExpr!=null) {
				encode(initExpr);
			}
		} else {
			appendParamParenthesesStart();
			List<Expr> arguments = expr.getArgumentExprs();
			if(arguments!=null) {
				for(int i=0;i<arguments.size();i++) {
					if(i>0) {
						appendParamSep();

					}
					encode(arguments.get(i));					
				}
			}
			appendParamParenthesesEnd();
		}
		//append(expr.toString());

	}


	@Override
	public void encode(ArgumentsExpr expr) {

		appendParamParenthesesStart();
		List<Expr> arguments = expr.getArgumentExprs();
		if(arguments!=null) {
			for(int i=0;i<arguments.size();i++) {
				if(i>0)
					appendParamSep();
				encode(arguments.get(i));					
			}
		}
		appendParamParenthesesEnd();

	}


	@Override
	public void encode(BinOpExpr expr) {
		encode(expr.getLeftExpr());
		appendSpace();
		appendOperatorMark(expr.getOpt());
		
		if(expr.getRightExpr()!=null) {
			appendSpace();
			encode(expr.getRightExpr());
		}
		else if(expr.getInstanceofType()!=null) {
			appendSpace();
			encode(expr.getInstanceofType());
		}

	}


	@Override
	public void encode(CondExpr expr) {
		encode(expr.getCondExpr());
		appendOperatorMark(new OperatorMark(OperatorMark.MARK.HOOK));
		encode(expr.getTrueExpr());
		appendOperatorMark(new OperatorMark(OperatorMark.MARK.COLON));
		encode(expr.getFalseExpr());

	}


	@Override
	public void encode(PrimaryExpr expr) {
		if(expr.getPrefixOptStack()!=null) {
			for(OperatorMark mark :  expr.getPrefixOptStack()) {
				appendOperatorMark(mark);
			}
		}
		
		Expr prefixExpr = expr.getPrefixExpr();
		
		if(prefixExpr!=null) {
			encode(prefixExpr);
		} else {
			encode(expr.getPrefixAtom());
		}
		
		if(expr.getPostfixOptStack()!=null) {
			for(OperatorMark mark :  expr.getPostfixOptStack()) {
				appendOperatorMark(mark);
			}
		}
		
		
		
		if(expr.getPostfixExprs()!=null 
				&& expr.getPostfixExprs().size()>0) {
			if(expr.getPrefixAtom()!=null 
					&& (expr.getPrefixAtom() instanceof ReferenceAtom ||
							expr.getPrefixAtom() instanceof BracketOperator
					)
					&& expr.getPostfixExprs().get(0) instanceof ReferenceNameExpr) {
				appendDotMark();
			}
			
			int i=0;
			for(Expr postfixExpr :  expr.getPostfixExprs()) {				
				if(i>0 && postfixExpr instanceof ReferenceNameExpr){
					appendDotMark();
				}
				i++;
				encode(postfixExpr);
			}
		}


	}



	@Override
	public void encode(BracketExpr expr) {
		appendParamParenthesesStart();
		encode(expr.getExpr());
		appendParamParenthesesEnd();
		
	}
	
	@Override
	public void encode(CastExpr expr) {
		appendParamParenthesesStart();
		encode(expr.getCastType());
		appendParamParenthesesEnd();
		encode(expr.getCastExpr());
		
	}

	@Override
	public void encode(PostfixExpr expr) {
		encode(expr.getLeftExpr());
		if(expr.getOptMark()!=null)
			appendOperatorMark(expr.getOptMark());
		
	}

	@Override
	public void encode(ReferenceNameExpr expr) {
		append(expr.getReferenceName());		
	}


	@Override
	public void encode(ArrayIndexExpr expr) {
		appendArrayBracketStart();
		encode(expr.getExpr());
		appendArrayBracketEnd();
		
	}

	@Override
	public void encode(ArrayInitExpr expr) {
		appendStatementBracketStart();
		List<Expr> initExprs = expr.getInitExprs();
		if(initExprs!=null) {
			indent++;
			for(int i=0;i<initExprs.size();i++) {
				if(i>0) {
					appendParamSep();
					appendLineBreak();
					appendTab(indent);
				}
				encode(initExprs.get(i));					
			}
			indent--;
		}
		appendStatementBracketEnd();
		
	}


	@Override
	public void encode(Block stm) {
		
		indent++;
		appendStatementBracketStart();
		appendLineBreak();
		List<Stm> stmList = stm.getStatements();	
		if(stmList!=null) {
			for(Stm stmItem : stmList) {
				appendTab(indent);
				encode(stmItem);
				if(isStatementEndNeed(stmItem))
					appendStatementEnd();
				appendLineBreak();
			}
		}
		
		indent--;
		appendTab(indent);
		appendStatementBracketEnd();
		appendLineBreak();	

	}


	@Override
	public void encode(EmptyStm stm) {

//		appendStatementEnd();
//		appendLineBreak();

	}


	@Override
	public void encode(ForStm stm) {

		appendKeyWord(KeyWord.KeyFor);
		appendStmParenthesesStart();
		
		encode(stm.getInitStms());	
		appendStatementEnd();
		encode(stm.getForCondExpr());
		appendStatementEnd();
		if(stm.getUpdateStms()!=null)
			encode(stm.getUpdateStms());
		appendStmParenthesesEnd();


		encode(stm.getBodyStm());

	}
	
	@Override
	public void encode(WhileStm stm) {

		appendKeyWord(KeyWord.KeyWhile);
		
		appendStmParenthesesStart();
		encode(stm.getForCondExpr());
		appendStmParenthesesEnd();
		encode(stm.getBodyStm());



	}

	@Override
	public void encode(DoStm stm) {

		appendKeyWord(KeyWord.KeyDo);
		encode(stm.getBodyStm());
		appendTab(indent);
		appendKeyWord(KeyWord.KeyWhile);
		appendStmParenthesesStart();
		encode(stm.getForCondExpr());		
		appendStmParenthesesEnd();
		appendStatementEnd();


	}


	@Override
	public void encode(IfStm stm) {

		appendKeyWord(KeyWord.KeyIf);
		appendStmParenthesesStart();
		encodeCondExpr(stm.getCondExpr());
		appendStmParenthesesEnd();
		Stm trueStm = stm.getTrueStm();
		encode(trueStm);	
		if(isStatementEndNeed(trueStm)) {
			appendStatementEnd();
		}
		appendSpace();
		Stm falseStm = stm.getFalseStm();
		if(falseStm != null) {
			appendTab(indent);
			appendKeyWord(KeyWord.KeyElse);
			appendSpace();
			if(falseStm instanceof IfStm)
				appendSpace();
			encode(falseStm);
			if(isStatementEndNeed(falseStm)) {
				appendStatementEnd();
			}
		}
		appendTab(indent);
		appendKeyWord(KeyWord.KeyEndIf);

	}

	protected void encodeCondExpr(Expr expr) {
		encode(expr);
	}

	@Override
	public void encode(LabeledStm stm) {

		append(stm.getLabel());
		appendLabelMark();
		encode(stm.getStm());
		appendLineBreak();

	}



	@Override
	public void encode(ReturnStm stm) {

		appendKeyWord(KeyWord.KeyReturn);
		appendSpace();
		if(stm.getReturnExpr()!=null)
			encode(stm.getReturnExpr());
		appendStatementEnd();
		//appendLineBreak();

	}


	@Override
	public void encode(StmExpr stm) {

		if(stm.getPreOptMark()!=null)
			append(stm.getPreOptMark().getOpt().getStr());
		
		encode(stm.getExpr());
		if(stm.getPostOptMark()!=null)
			append(stm.getPostOptMark().getOpt().getStr());
		if(stm.getAssignOptMark()!=null)
			appendOperatorMark(stm.getAssignOptMark());
		if(stm.getAssignExpr()!=null)
			encode(stm.getAssignExpr());
		

	}


	@Override
	public void encode(StmList stm) {

		
		List<Stm> stmList = stm.getList();
		for(Stm stmItem : stmList)
			encode(stmItem);
		

	}


	@Override
	public void encode(SwitchStm stm) {

		appendKeyWord(KeyWord.KeySwitch);
		appendStmParenthesesStart();
		encode(stm.getSwitchKeyExpr());
		appendStmParenthesesEnd();
		appendStatementBracketStart();
		appendLineBreak();
		indent++;
		List<CaseStm> caseStms = stm.getCaseStms();
		for(CaseStm caseStm : caseStms) {
			List<Expr> caseKeyExprs = caseStm.getCaseKeyExpr();
			if(caseStm.isDefaultCase()) {
				appendTab(indent);
				appendKeyWord(KeyWord.KeyDefault);
				
				appendLabelMark();
				appendLineBreak();
			} 
			if(caseKeyExprs!=null) {
				for(Expr expr : caseKeyExprs) {
					appendTab(indent);
					appendKeyWord(KeyWord.KeyCase);
					appendSpace();
					encode(expr);
					appendLabelMark();
					appendLineBreak();
				}
			}
			
			indent++;
			
			StmList stmList = caseStm.getStm();
			for(Stm stmListItem : stmList.getList()) {
				appendTab(indent);
				encode(stmListItem);	
				if(isStatementEndNeed(stmListItem))
					appendStatementEnd();
				appendLineBreak();
			}
			appendLineBreak();
			indent--;
		}
		indent--;
		appendLineBreak();
		appendTab(indent);
		appendStatementBracketEnd();
	}



	@Override
	public void encode(BreakStm stm) {
		appendKeyWord(KeyWord.KeyBreak);
	}


	@Override
	public void encode(ContinueStm stm) {
		appendKeyWord(KeyWord.KeyContinue);		
	}

	@Override
	public void encode(TryStatement stm) {
		appendKeyWord(KeyWord.KeyTry);
		encode(stm.getTryBlock());
		
		List<CatchStatement> catchStms = stm.getCatchBlock();
		for(CatchStatement catchStm : catchStms) {
			appendTab(indent);
			appendKeyWord(KeyWord.KeyCatch);			
			catchStm.getException();
			FormalParameter exception = catchStm.getException();
			appendStmParenthesesStart();
			appendParamType(exception.getType());
			appendSpace();
			append(exception.getName());
			appendStmParenthesesEnd();
			encode(catchStm.getBody());			
		}
		appendTab(indent);
		if(stm.getFinallyBlock()!=null) {
			appendKeyWord(KeyWord.KeyFinally);
			encode(stm.getFinallyBlock());
		}

	}

	@Override
	public void encode(ConstructorStm stm) {
		if(stm.isThisExplicitConstructor()) {
			appendKeyWord(KeyWord.KeyThis);
		} else if(stm.isSuperExplicitConstructor()) {
			appendKeyWord(KeyWord.KeySuper);
		}
		encode(stm.getExpr());
		appendStatementEnd();
	}


	@Override
	public void encode(ThrowStatement stm) {
		appendKeyWord(KeyWord.KeyThrow);
		appendSpace();
		encode(stm.getThrowExpr());
		
	}


	@Override
	public void encode(YoyooField decl) {


		
		Modifier modifier = decl.getModifier();
		if(decl.getModifier()!=null) {
			String modifierName = getModifier(modifier);
			append(modifierName);
			if(modifierName.length()>0) {
				appendSpace();
			}
		}
		if(decl.isStaticField()) {
			appendKeyWord(KeyWord.KeyStatic);
			appendSpace();
		}
		if(decl.isFinalField()) {
			appendKeyWord(KeyWord.KeyFinal);
			appendSpace();
		}
		IType type = decl.getType();
		encode(type);
		appendSpace();
		append(decl.getName());
		Expr expr = decl.getAssignExpr();
		if(expr!=null) {
			appendSpace();
			appendAllocationMark();
			appendSpace();
			encode(expr);
		}
		appendStatementEnd();
		appendLineBreak();

	}


	@Override
	public void encode(YoyooVariable decl) {
		Modifier modifier = decl.getModifier();
		if(decl.getModifier()!=null) {
			String modifierName = getModifier(modifier);
			append(modifierName);
			if(modifierName.length()>0) {
				appendSpace();
			}
		}
		IType type = decl.getType();
		encode(type);
		
		appendSpace();
		append(decl.getName());
		Expr expr = decl.getAssignExpr();
		if(expr!=null) {
			appendAllocationMark();
			encode(expr);
		}


	}


	
	private boolean isStatementEndNeed(Stm stm) {
		return (stm instanceof StmExpr || 
				stm instanceof StopStm || 
				stm instanceof YoyooVariable ||
				stm instanceof ThrowStatement);
	}




	@Override
	public void encodeDecl(YoyooMethod declt) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void encodeDecl(YoyooClassConstructor decl) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void encodeDecl(YoyooTypeDefineClass decl) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void encodeDecl(YoyooField decl) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void encode(SynchronizedStatement stm) {
		
		appendKeyWord(KeyWord.KeySynchronized);	
		appendStmParenthesesStart();
		encode(stm.getSynExpr());
		appendStmParenthesesEnd();
		encode(stm.getSynStm());
		
	}
























	
	

}
