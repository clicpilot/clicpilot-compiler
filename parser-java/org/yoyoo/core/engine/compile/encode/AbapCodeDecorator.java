package org.yoyoo.core.engine.compile.encode;

import java.util.HashMap;
import java.util.Map;

import org.yoyoo.core.engine.compile.FormalParameter;
import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.OperatorMark;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.IDeclaration;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.declaration.YoyooVariable;
import org.yoyoo.core.engine.compile.exp.AllocationExpr;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.PrimaryExpr;
import org.yoyoo.core.engine.compile.exp.ReferenceNameExpr;
import org.yoyoo.core.engine.compile.stm.ReturnStm;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;



public class AbapCodeDecorator extends DefaultCodeDecorator {
	
	public static Map<String, String> classesMappingTable = new HashMap<String, String>();
	static {
		
		classesMappingTable.put("String", "string");
		classesMappingTable.put("Integer", "I");
		classesMappingTable.put("Short", "I");
		classesMappingTable.put("Long", "I");		
		classesMappingTable.put("long", "I");
		classesMappingTable.put("Float", "F");
		classesMappingTable.put("Double", "F");
		classesMappingTable.put("Boolean", "c");
		classesMappingTable.put("boolean", "c");
		classesMappingTable.put("List", "STANDARD TABLE OF ");

		
	}
	public AbapCodeDecorator() {
		super();
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
				keyword = "ABSTRACT";
				break;	
			case KeyClass:
				keyword = "class";
				break;
			case KeyExtends:
				keyword = "INHERITING FROM";
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
				keyword = "IF";
				break;
			case KeyEndIf:
				keyword = "ENDIF.";
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
				keyword = "CREATE OBJECT";
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
				keyword = "me";
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

		}
		return keyword;
	}

	


	protected void encodeInheritDecl(YoyooTypeDefineClass decl) {
		if(decl.getExtendsReferenceType()!=null) {
			String extendsName = decl.getExtendsReferenceType().getName();
			if(extendsName!=null) {
				appendSpace();
				appendKeyWord(KeyWord.KeyExtends);
				appendSpace();
				append(decl.getExtendsReferenceType().getName());
			}
		}
	}


	
	@Override
	public void encodeDecl(YoyooTypeDefineClass decl) {
		append(StringUtil.upper(decl.getTypeName()));
		appendSpace();
		append("zcl_"+toABAPStyleName(decl.getName()));
		appendSpace();
		appendDefinition();
		
		if(decl.isAbstract()) {
			appendKeyWord(KeyWord.KeyAbstract);
			appendSpace();
		}
		
		encodeInheritDecl(decl);
		
		appendLineEnd();			

		
		indent = 1;

		appendPublicSection();
		encodeDecl(decl, Modifier.PUBLIC);
		appendProtectedSection();
		encodeDecl(decl, Modifier.PROTECTED);
		appendPrivateSection();
		encodeDecl(decl, Modifier.PRIVATE);
		append("END"+StringUtil.upper(decl.getTypeName()));
		appendLineEnd();
	}
	
	@Override
	public void encode(YoyooTypeDefineClass decl) {

		this.unit = decl.getCompilationUnit();
		encodeDecl(decl);	
		append(StringUtil.upper(decl.getTypeName()));
		appendSpace();
		append("zcl_"+toABAPStyleName(decl.getName()));
		appendSpace();
		appendImplementation();
		appendLineEnd();	
		
		
		SymbolTable<YoyooMethod> methods = decl.getMethods();
		Modifier modifier = Modifier.PUBLIC;
		if(hasMethodDecl(decl, modifier )) {
			
			for(YoyooMethod method : methods.getSymbols().values()) {
				if(method!=null && method.getModifier()==modifier) {
					encode((IDeclaration)method);
				}
			}
			
		}
		
		append("END"+StringUtil.upper(decl.getTypeName()));
		appendLineEnd();
	}
	
	private boolean checkComma() {
		return buffer.substring(buffer.length()-3, buffer.length()).endsWith(",\r\n");
	}
	private void cutComma() {
		buffer.delete(buffer.length()-3, buffer.length());
	}
	protected boolean hasMethodDecl(YoyooTypeDefineClass decl, Modifier modifier) {
		
		SymbolTable<YoyooMethod> methods = decl.getMethods();
		if(!methods.isEmpty()) {
			for(YoyooMethod method : methods.getSymbols().values()) {
				if(method!=null && method.getModifier()==modifier) {
					return true;
				}
			}
		}

		return false;
	}
	
	protected boolean hasFieldDecl(YoyooTypeDefineClass decl, Modifier modifier) {
		
		SymbolTable<YoyooField> fields = decl.getFields();
		if(!fields.isEmpty()) {
			for(YoyooField field : fields.getSymbols().values()) {
				if(field!=null && field.getModifier()==modifier) {
					return true;
				}
			}
		}

		return false;
	}
	private void encodeDecl(YoyooTypeDefineClass decl, Modifier modifier) {
		SymbolTable<YoyooField> fields = decl.getFields();
		SymbolTable<YoyooMethod> methods = decl.getMethods();
		if(hasMethodDecl(decl, modifier)) {
			appendMethods();
			for(YoyooMethod method : methods.getSymbols().values()) {
				if(method!=null && method.getModifier()==modifier) {
					encodeDecl((IDeclaration)method);
					if(!checkComma()) {
						appendComma();
						appendLineBreak();
					}
				}
			}
			if(checkComma()) {
				cutComma();
			}
			
			appendLineEnd();
		}
		
		SymbolTable<YoyooClassConstructor> constructors = decl.getConstructors();
		for(YoyooClassConstructor constructor : constructors.getSymbols().values()) {
			if(constructor!=null && constructor.getModifier()==modifier) {
				encodeDecl((IDeclaration)constructor);
			}
		}
		if(hasFieldDecl(decl, modifier)) {
			appendData();
			for(YoyooField field : fields.getSymbols().values()) {
				if(field.getModifier()==modifier) {
					encodeDecl((IDeclaration)field);
					if(!checkComma()) {
						appendComma();
						appendLineBreak();
					}
				}
			}
			if(checkComma()) {
				cutComma();
			}
			
			appendLineEnd();
		}
	}

	
	protected String toABAPStyleName(String name) {
		StringBuffer methodNameBuf = new StringBuffer();
		boolean last =false;
		for(int i=0;i<name.length();i++) {
			char charAt = name.charAt(i);			
			if(Character.isUpperCase(charAt)) {
				if(i!=0 && !last)
					methodNameBuf.append("_");
				methodNameBuf.append(Character.toLowerCase(charAt));
				last = true;
			} else {
				methodNameBuf.append(charAt);
				last = false;
			}
		}
		return methodNameBuf.toString();
	}
	
	@Override
	public void encodeDecl(YoyooMethod decl) {
		
		appendSpace();
		String methodName = decl.getName();
		
		append(toABAPStyleName(methodName));
		appendSpace();

		SymbolTable<FormalParameter>  params = decl.getParameters();
		int paramCount = params.getSymbols().size();
		if(paramCount>0) {
			appendImporting();
			appendSpace();
			for(FormalParameter param : params.getSymbols().values()) {
				append(toABAPStyleName(param.getName()));
				appendSpace();
				appendType();
				appendSpace();
				appendParamType(param.getType());
				paramCount--;
			}
		}
		
		appendMethodReturningDecl(decl, paramCount);

	}

	protected void appendMethodReturningDecl(YoyooMethod decl, int paramCount){
		String methodName = decl.getName();
		
		IType returnType = decl.getReturnType();
		if(!PrimitiveType.isVoid(returnType)) {
			if(paramCount>0) {
				appendComma();
				appendSpace();
			} else {
				appendSpace();
			}
				
			String abapStyleMethodName = toABAPStyleName(methodName);
			int indexOf = abapStyleMethodName.indexOf("_")+1;
			if(indexOf==abapStyleMethodName.length())
				indexOf = 0;
			appendReturningValue("r_"+abapStyleMethodName.substring(indexOf));
			appendSpace();
			appendType();
			appendSpace();
			appendParamType(returnType);
		} 
		
	}



	
	
	@Override
	public void encode(YoyooMethod decl) {


		if(!decl.isAbstract()) {
			appendMethod();
			appendSpace();
			append(toABAPStyleName(decl.getName()));
			appendLineEnd();
			appendMethodBody(decl);
			appendEndMethod();
			appendLineEnd();
			
		}
	}


	@Override
	public void encodeDecl(YoyooClassConstructor decl) {
		
	}

	@Override
	public void encodeDecl(YoyooField decl) {
		
		appendSpace();
		append(toABAPStyleName(decl.getName()));
		appendSpace();
		appendType();
		appendSpace();
		IType type = decl.getType();
		encode(type);
		
	}

	
	@Override
	public void encode(ReturnStm stm) {

		String methodName = stm.getMethod().getName();
		
		if(stm.getReturnExpr()!=null) {
			Expr returnExpr = stm.getReturnExpr();
			if(returnExpr instanceof PrimaryExpr) {
				IAtom prefixAtom = ((PrimaryExpr) returnExpr).getPrefixAtom();
				if(prefixAtom instanceof ReferenceAtom) {					
					String abapStyleMethodName = toABAPStyleName(methodName);
					int indexOf = abapStyleMethodName.indexOf("_")+1;
					if(indexOf==abapStyleMethodName.length())
						indexOf = 0;
					append("r_"+abapStyleMethodName.substring(indexOf));
					append(" = ");
					append(toABAPStyleName(((ReferenceAtom) prefixAtom).getName()));
					appendLineEnd();
					return;
				}
			} 
			
		}

		

	}
	
	@Override
	public void encode(YoyooVariable decl) {
		appendVarData();
		appendSpace();
		append(toABAPStyleName(decl.getName()));
		appendSpace();
		appendType();
		appendSpace();
		IType type = decl.getType();
		encode(type);
		appendLineEnd();
		Expr expr = decl.getAssignExpr();
		if(expr!=null) {
//			appendAllocationMark();
			encode(expr);
		}
		

	}
	


	
	@Override
	public void encode(AllocationExpr expr) {
		appendTab(indent);
		appendKeyWord(KeyWord.KeyNew);
		appendSpace();
		Stm stm = expr.getStm();
		if(stm instanceof YoyooVariable) {
			append(toABAPStyleName(((YoyooVariable)stm).getName()));
		}

	}
	
	@Override
	public void encode(ReferenceAtom atom) {
		if(atom.getName().equals("this"))
			appendKeyWord(KeyWord.KeyThis);
		else {	
			String name = atom.getName();
			name = name.replace(".", "->");
			append(name);
		}
	}
	

	
	@Override
	public void encode(ReferenceNameExpr expr) {
		append(toABAPStyleName(expr.getReferenceName()));		
	}
	@Override
	protected void appendDotMark() {
		buffer.append("->");
		
	}
	@Override
	protected void appendStatementEnd() {
		buffer.append(".");
	}
	@Override
	protected void appendAllocationMark() {
		buffer.append(" = ");
	}
	@Override
	protected void appendOperatorMark(OperatorMark mark) {
		buffer.append(" "+mark.getOpt().getStr()+" ");
	}
	@Override
	protected void appendStatementBracketStart() {
		buffer.append("");
	}
	@Override
	protected void appendStatementBracketEnd() {
		buffer.append("");
	}
	@Override
	protected void appendParamParenthesesStart() {
		buffer.append("( ");
	}
	@Override
	protected void appendParamParenthesesEnd() {
		buffer.append(" )");
	}

	protected void appendLineEnd() {
		buffer.append(".\r\n");
	}

	protected void appendComma() {
		buffer.append(",");
	}
	@Override
	protected void appendStmParenthesesStart() {
		buffer.append(" ");
	}
	@Override
	protected void appendStmParenthesesEnd() {
		buffer.append(" .");
	}
	
	@Override
	protected void appendQuotedString(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("\'", "\\\'");
		buffer.append("'"+str+"'");
	}
	
	protected void appendTypeArgumentBracketStart() {
		buffer.append("REF TO ");
	}
	
	protected void appendTypeArgumentBracketEnd() {
		buffer.append("");
	}
	
	protected void appendDefinition() {
		buffer.append("DEFINITION");
	}
	
	protected void appendImplementation() {
		buffer.append("IMPLEMENTATION");
	}

	protected void appendPublicSection() {
		buffer.append("PUBLIC SECTION");
		appendLineEnd();
	}
	
	protected void appendProtectedSection() {
		buffer.append("PROTECTED SECTION");
		appendLineEnd();
	}
	
	protected void appendPrivateSection() {
		buffer.append("PRIVATE SECTION");
		appendLineEnd();
	}
	
	protected void appendMethods() {
		buffer.append("METHODS:");
		appendLineBreak();
	}
	
	protected void appendMethod() {
		buffer.append("METHOD");

	}
	
	protected void appendEndMethod() {
		buffer.append("ENDMETHOD");

	}
	
	protected void appendData() {
		buffer.append("DATA:");
		appendLineBreak();
	}
	protected void appendVarData() {
		buffer.append("DATA");
	}

	
	protected void appendImporting() {
		buffer.append("IMPORTING");

	}
	

	protected void appendReturningValue(String name) {
		buffer.append("RETURNING value("+name+")");

		
	}
	
	protected void appendType() {
		buffer.append("TYPE");

	}
	
	@Override
	protected void appendTypeName(IType type) {
		if(classesMappingTable.containsKey(type.getName())) {
			append(classesMappingTable.get(type.getName()));
		} else {
			append("zcl_"+toABAPStyleName(type.getName()));
		}
	}

}
