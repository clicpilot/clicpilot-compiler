package org.yoyoo.core.engine.compile.encode;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.IValueAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.atom.ValueAtom;
import org.yoyoo.core.engine.compile.declaration.AbstractDeclaration;
import org.yoyoo.core.engine.compile.declaration.IDeclaration;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
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
import org.yoyoo.core.engine.compile.stm.Block;
import org.yoyoo.core.engine.compile.stm.BreakStm;
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
import org.yoyoo.core.engine.compile.stm.SwitchStm;
import org.yoyoo.core.engine.compile.stm.SynchronizedStatement;
import org.yoyoo.core.engine.compile.stm.ThrowStatement;
import org.yoyoo.core.engine.compile.stm.TryStatement;
import org.yoyoo.core.engine.compile.stm.WhileStm;
import org.yoyoo.core.engine.compile.type.ArrayBasedType;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.encode.exception.EncodeException;
import org.yoyoo.core.engine.encode.exception.UnsupportAtomException;
import org.yoyoo.core.engine.encode.exception.UnsupportDeclException;
import org.yoyoo.core.engine.encode.exception.UnsupportExprException;
import org.yoyoo.core.engine.encode.exception.UnsupportStmException;
import org.yoyoo.core.engine.yoyoo.lang.INumber;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public abstract class AbstractCodeDecorator implements ICodeDecorator {

	protected CompilationUnit unit;
	
	protected StringBuffer buffer = new StringBuffer();
	
	protected int indent;

	public AbstractCodeDecorator() {
		super();
	}
	

	@Override
	public void start(CompilationUnit unit) throws EncodeException {
		this.unit = unit;
		SymbolTable<YoyooTypeDefineClass> yclasses = unit.getDecl();
		for(YoyooTypeDefineClass yclass : yclasses.getSymbols().values()) {
					
			encode(yclass);
		}
		
	}
	
	@Override
	public void output(Writer out) throws IOException {
		out.write(this.buffer.toString());		
	}

	@Override
	public boolean output(Writer out, long checksum) throws IOException {
		Checksum cs = new CRC32();
		StringReader str = new StringReader(this.buffer.toString());
		char[] c= new char[128];
		int i=0;
		
		
		while((i=str.read(c))>0) {
			out.write(c, 0, i);
			out.flush();
			for(int j=0;j<i;j++) {
				cs.update(c[j]);
			}
			
		}
		
		return checksum != cs.getValue();
			
		
			
	}
	

	@Override
	public boolean output(StringBuffer out, long checksum) throws IOException {
		Checksum cs = new CRC32();
		StringReader str = new StringReader(this.buffer.toString());
		char[] c= new char[128];
		int i=0;
		
		
		while((i=str.read(c))>0) {
			out.append(c, 0, i);
			for(int j=0;j<i;j++) {
				cs.update(c[j]);
			}
			
		}
		
		return checksum != cs.getValue();
		
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CompilationUnit) {	
			if(((CompilationUnit)arg).isCompiled())
				start((CompilationUnit)arg);
		} else {
			throw new IllegalArgumentException();
		}
		
		
	}

	@Override
	public void encode(IValueAtom atom)  throws EncodeException{
		
		if(atom instanceof ReferenceAtom) {
			encode((ReferenceAtom)atom);
		} else if(atom instanceof ValueAtom) {
			encode((ValueAtom)atom);
		} else {
			throw new UnsupportAtomException(atom);
		}		
	}


	
	@Override
	public void encode(Expr expr)  throws EncodeException{

		
		
		if(expr instanceof AllocationExpr) {
			encode((AllocationExpr)expr);
		} else if(expr instanceof ArgumentsExpr) {
			encode((ArgumentsExpr)expr);
		} else if(expr instanceof BinOpExpr) {
			encode((BinOpExpr)expr);
		} else if(expr instanceof CondExpr) {
			encode((CondExpr)expr);
		} else if(expr instanceof PrimaryExpr) {
			encode((PrimaryExpr)expr);
		} else if(expr instanceof BracketExpr) {
			encode((BracketExpr)expr);			
		} else if(expr instanceof ReferenceNameExpr) {
			encode((ReferenceNameExpr)expr);			
		} else if(expr instanceof ArrayIndexExpr) {
			encode((ArrayIndexExpr)expr);			
		} else if(expr instanceof ArrayInitExpr) {
			encode((ArrayInitExpr)expr);			
		} else if(expr instanceof CastExpr) {
			encode((CastExpr)expr);			
		} else if(expr instanceof PostfixExpr) {
			encode((PostfixExpr)expr);			
		} else {
			throw new UnsupportExprException(expr);
		}
		

	}

	@Override
	public void encode(Stm stm)  throws EncodeException{

		
		if(stm instanceof Block) {
			encode((Block)stm);
		} else if(stm instanceof EmptyStm) {
			encode((EmptyStm)stm);
		} else if(stm instanceof ForStm) {
			encode((ForStm)stm);
		} else if(stm instanceof IfStm) {
			encode((IfStm)stm);
		} else if(stm instanceof WhileStm) {
			encode((WhileStm)stm);
		} else if(stm instanceof DoStm) {
			encode((DoStm)stm);
		} else if(stm instanceof LabeledStm) {
			encode((LabeledStm)stm);
		} else if(stm instanceof ReturnStm) {
			encode((ReturnStm)stm);
		} else if(stm instanceof StmExpr) {
			encode((StmExpr)stm);
		} else if(stm instanceof StmList) {
			encode((StmList)stm);
		} else if(stm instanceof SwitchStm) {
			encode((SwitchStm)stm);
		} else if(stm instanceof YoyooField) {
			encode((YoyooField)stm);
		} else if(stm instanceof YoyooVariable) {
			encode((YoyooVariable)stm);
		} else if(stm instanceof BreakStm) {
			encode((BreakStm)stm);
		} else if(stm instanceof ContinueStm) {
			encode((ContinueStm)stm);
		} else if(stm instanceof TryStatement) {
			encode((TryStatement)stm);
		} else if(stm instanceof ConstructorStm) {
			encode((ConstructorStm)stm);
		} else if(stm instanceof ThrowStatement) {
			encode((ThrowStatement)stm);
		} else if(stm instanceof SynchronizedStatement) {
			encode((SynchronizedStatement)stm);
		} else {
			System.err.println(stm);
			throw new UnsupportStmException(stm);
		}

		
	}
	
	

	@Override
	public void encode(IDeclaration decl)  throws EncodeException{

		
		if(decl instanceof YoyooMethod) {
			encode((YoyooMethod)decl);
		} else if(decl instanceof YoyooClassConstructor) {
			encode((YoyooClassConstructor)decl);
		} else if(decl instanceof YoyooTypeDefineClass) {
			encode((YoyooTypeDefineClass)decl);		
		} else if(decl instanceof YoyooField) {
			encode((YoyooField)decl);		
		} else {
			throw new UnsupportDeclException(decl);
		}

	}
	
	@Override
	public void encodeDecl(IDeclaration decl)  throws EncodeException{

		
		if(decl instanceof YoyooMethod) {
			encodeDecl((YoyooMethod)decl);
		} else if(decl instanceof YoyooClassConstructor) {
			encodeDecl((YoyooClassConstructor)decl);
		} else if(decl instanceof YoyooTypeDefineClass) {
			encodeDecl((YoyooTypeDefineClass)decl);		
		} else if(decl instanceof YoyooField) {
			encodeDecl((YoyooField)decl);		
		} else {
			throw new UnsupportDeclException(decl);
		}

	}
	


	protected void encode(YoyooObject value) {
		if(value instanceof YoyooTypeDefine) {
			append(value.toString());
		} else {
			if(value instanceof YoyooLong) {
				append(value.toString()+"L");
			} else if(value instanceof YoyooDouble) {
				append(value.toString()+"D");
			} else if(value instanceof YoyooFloat) {
				append(value.toString()+"F");
			} else if(value instanceof YoyooShort) {
				append(value.toString()+"S");
			} else if(value instanceof INumber) {
				append(value.toString());
			} else if(value instanceof YoyooString) {
				appendQuotedString(value.toString());
			} else if(value instanceof YoyooBoolean) {
				append(value.toString());
			}
		}
		
	}

	protected void encode(IAtom atom) {
		if(atom instanceof Expr) {
			encode((Expr)atom);
		} else if(atom instanceof IValueAtom) {
			encode((IValueAtom)atom);
		} else {
			append(atom.toString());
		}
	}
	
	public void encode(IType type) {
		appendType(type);
		
		if(type.isArray()) {
			for(int i=0;i<type.getArrayDim();i++) {
				appendArrayBracketStart();
				appendArrayBracketEnd();
			}
		}
		
		
	}
	
	
	protected void appendImports() {
		for(String importPackage : unit.getImports()) {
			appendKeyWord(KeyWord.KeyImport);
			appendSpace();
			append(importPackage);
			appendStatementEnd();
			appendLineBreak();			
		}
	}
	
	public abstract void encode(ReferenceAtom atom);
	public abstract void encode(ValueAtom atom);

	
	public abstract void encode(AllocationExpr expr);
	public abstract void encode(ArgumentsExpr expr);
	public abstract void encode(BinOpExpr expr);
	public abstract void encode(CondExpr expr);
	public abstract void encode(PrimaryExpr expr);
	public abstract void encode(BracketExpr expr);
	public abstract void encode(ReferenceNameExpr expr);
	public abstract void encode(ArrayIndexExpr expr);
	public abstract void encode(ArrayInitExpr expr);
	public abstract void encode(CastExpr expr);
	public abstract void encode(PostfixExpr expr);	
	
	public abstract void encode(Block stm);
	public abstract void encode(EmptyStm stm);
	public abstract void encode(ForStm stm);
	public abstract void encode(WhileStm stm);
	public abstract void encode(DoStm stm);
	public abstract void encode(IfStm stm);
	public abstract void encode(LabeledStm stm);
	public abstract void encode(ReturnStm stm);
	public abstract void encode(StmExpr stm);
	public abstract void encode(StmList stm);
	public abstract void encode(SwitchStm stm);
	public abstract void encode(BreakStm stm);
	public abstract void encode(ContinueStm stm);
	public abstract void encode(TryStatement stm);
	public abstract void encode(ConstructorStm stm);
	public abstract void encode(ThrowStatement stm);
	public abstract void encode(SynchronizedStatement stm);
	
	public abstract void encode(YoyooMethod decl);
	public abstract void encode(YoyooClassConstructor decl);
	public abstract void encode(YoyooTypeDefineClass decl);
	public abstract void encode(YoyooField decl);
	public abstract void encode(YoyooVariable decl);

	public abstract void encodeDecl(YoyooMethod declt);
	public abstract void encodeDecl(YoyooClassConstructor decl);
	public abstract void encodeDecl(YoyooTypeDefineClass decl);
	public abstract void encodeDecl(YoyooField decl);


	
	
	protected enum KeyWord {
		KeyImport, KeyPackage, KeyAbstract, KeyClass, KeyExtends, KeyImplements, KeyStatic, KeyFor, KeyIf, KeyEndIf, KeyElse, KeyReturn, KeySwitch, KeyDefault, KeyCase, KeyWhile, KeyDo, KeyNew, KeyBreak, KeyContinue, KeyTry, KeyCatch, KeyFinally, KeyThis, KeySuper, KeyThrow, KeyFinal, KeyThrows, KeySynchronized,

	}

	
	public String getKeyWord(KeyWord name) {
		String keyword = new String();
		switch(name) {
			case KeyImport:
				keyword = "import";
				break;
			case KeyPackage:
				keyword = "package";
				break;
			case KeyAbstract:
				keyword = "abstract";
				break;	
			case KeyClass:
				keyword = "class";
				break;
			case KeyExtends:
				keyword = "extends";
				break;	
			case KeyImplements:
				keyword = "implements";
				break;
			case KeyStatic:
				keyword = "static";
				break;
			case KeyFor:
				keyword = "for";
				break;
			case KeyIf:
				keyword = "if";
				break;
			case KeyElse:
				keyword = "else";
				break;
			case KeyReturn:
				keyword = "return";
				break;
			case KeySwitch:
				keyword = "switch";
				break;
			case KeyDefault:
				keyword = "default";
				break;
			case KeyCase:
				keyword = "case";
				break;	
			case KeyWhile:
				keyword = "while";
				break;	
			case KeyDo:
				keyword = "do";
				break;	
			case KeyNew:
				keyword = "new";
				break;	
			case KeyBreak:
				keyword = "break";
				break;	
			case KeyContinue:
				keyword = "continue";
				break;
			case KeyTry:
				keyword = "try";
				break;
			case KeyCatch:
				keyword = "catch";
				break;	
			case KeyFinally:
				keyword = "finally";
				break;
			case KeyFinal:
				keyword = "final";
				break;	
			case KeyThis:
				keyword = "this";
				break;
			case KeySuper:
				keyword = "super";
				break;	
			case KeyThrow:
				keyword = "throw";
				break;
			case KeyThrows:
				keyword = "throws";
				break;
			case KeySynchronized:
				keyword = "synchronized";
				break;	
					
		}
		return keyword;
	}
	

	public String getModifier(Modifier name) {
		String keyword = new String();
		switch(name) {
			default:
			case DEFAULT:
				keyword = "";
				break;
			case PRIVATE:
				keyword = "private";
				break;	
			case PROTECTED:
				keyword = "protected";
				break;	
			case PUBLIC:
				keyword = "public";
				break;	
		}
		return keyword;
	}

	
	protected void append(String str) {
		buffer.append(str);
	}
	
	protected void append(char ch) {
		buffer.append(ch);
	}
	protected void appendNull() {
		buffer.append("null");
	}
	
	protected void appendLineBreak() {
		buffer.append("\r\n");
	}
	
	protected void appendStatementEnd() {
		buffer.append(";");
	}
	
	protected void appendSpace() {
		buffer.append(" ");
	}
	
	protected void appendTab() {
		buffer.append("\t");
	}
	
	protected void appendTab(int num) {
		for(int i=0;i<num;i++)
			buffer.append("\t");
	}
	

	
	protected void appendParamSep() {
		buffer.append(",");
	}
	
	protected void appendLabelMark() {
		buffer.append(":");
	}
	
	
	
	protected void appendStatementBracketStart() {
		buffer.append("{");
	}
	
	protected void appendStatementBracketEnd() {
		buffer.append("}");
	}
	
	protected void appendParamParenthesesStart() {
		buffer.append("(");
	}
	
	protected void appendParamParenthesesEnd() {
		buffer.append(")");
	}
	
	
	protected void appendStmParenthesesStart() {
		buffer.append("(");
	}
	
	protected void appendStmParenthesesEnd() {
		buffer.append(")");
	}
	
	protected void appendArrayBracketStart() {
		buffer.append("[");
	}
	
	protected void appendArrayBracketEnd() {
		buffer.append("]");
	}
	
	protected void appendTypeArgumentBracketStart() {
		buffer.append("<");
	}
	
	protected void appendTypeArgumentBracketEnd() {
		buffer.append(">");
	}
	
	protected void appendAllocationMark() {
		buffer.append("=");
	}
	
	
	protected void appendDotMark() {
		buffer.append(".");
		
	}
	
	protected void appendKeyWord(KeyWord keyword) {
		buffer.append(getKeyWord(keyword));
	}
	
	protected void appendReturnType(IType type) {
		appendType(type);
	}
	
	protected void appendParamType(IType type) {
		appendType(type);
	}
	
	protected void appendType(IType type) {
		
		appendTypeName(type);
		
		if(type instanceof ArrayBasedType) {
			int arrayDim = ((ArrayBasedType)type).getArrayDim();
			for(int i=0;i<arrayDim;i++) {
				appendArrayBracketStart();
				appendArrayBracketEnd();
			}
		}
			
		if(type instanceof ReferenceNameType && ((ReferenceNameType)type).getTypeArguments()!=null) {
			
			
			
			List<IType> typeArguments = ((ReferenceNameType)type).getTypeArguments(); 
			if(typeArguments.size()>0) {
				appendTypeArgumentBracketStart();
				int i=1;
				for(IType typeArgument : typeArguments) {					
					appendType(typeArgument);
					if(i!=typeArguments.size()) {
						appendParamSep();
					}
					i++;
				}
				appendTypeArgumentBracketEnd();
			}
			
			
		}
	}
	
	protected void appendMethodThrows(YoyooBaseMethod decl) {
		if(decl.getThrowsList()!=null && !decl.getThrowsList().isEmpty()) {
			appendKeyWord(KeyWord.KeyThrows);
			appendSpace();
			List<ReferenceNameType> exceptionTypes = decl.getThrowsList();
			int exceptionTypesCount = exceptionTypes.size();
			for(ReferenceNameType exceptionType : exceptionTypes) {
				appendTypeName(exceptionType);
				if(exceptionTypesCount>1) {
					appendParamSep();
					appendSpace();
				}
				exceptionTypesCount--;
				
			}
		}
	}
	
	protected void appendTypeName(IType type) {
		if(type instanceof ReferenceNameType && ((ReferenceNameType)type).getPkgname()!=null) {
			append(((ReferenceNameType)type).getPkgname());
			appendDotMark();
		}
		append(type.getName());
		if(type.isArray()) {
			appendArrayBracketStart();
			appendArrayBracketEnd();
		}
	}

	protected void appendOperatorMark(OperatorMark mark) {
		buffer.append(mark.getOpt().getStr());
	}
	
	protected void appendQuotedString(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("\"", "\\\"");		
		buffer.append("\""+str+"\"");
	}
	
	protected void appendModifier(AbstractDeclaration decl) {
		Modifier modifier = decl.getModifier();
		if(decl.getModifier()!=null) {
			String modifierName = getModifier(modifier);
			append(modifierName);
			if(modifierName.length()>0) {
				appendSpace();
			}
		}
	}


	
}
