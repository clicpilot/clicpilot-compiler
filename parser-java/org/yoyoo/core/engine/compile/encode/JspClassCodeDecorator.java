package org.yoyoo.core.engine.compile.encode;

import java.util.List;

import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.declaration.IDeclaration;
import org.yoyoo.core.engine.compile.declaration.YoyooClassConstructor;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;

public class JspClassCodeDecorator extends JavaCodeDecorator {

	
	@Override
	public void encodeDecl(YoyooTypeDefineClass decl) {
		buffer.append("<%!");
	}
	

	
	@Override
	public void encode(YoyooTypeDefineClass decl) {

		this.unit = decl.getCompilationUnit();
		buffer.append("<%!");
		appendLineBreak();	
		appendKeyWord(KeyWord.KeyStatic);
		appendSpace();
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
		
		buffer.append("%>");
	}
	
}
