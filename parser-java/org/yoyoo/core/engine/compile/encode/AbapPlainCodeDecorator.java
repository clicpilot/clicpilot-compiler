package org.yoyoo.core.engine.compile.encode;

import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.SymbolTable;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.NullAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.exp.BinOpExpr;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.PrimaryExpr;

public class AbapPlainCodeDecorator extends AbapCodeDecorator {

	
	@Override
	public void encodeDecl(YoyooMethod decl) {
		
		String methodName = decl.getName();
		if(methodName.startsWith("set") 
				|| methodName.startsWith("get")
				|| methodName.startsWith("object2json")) {
			super.encodeDecl(decl);
		}
	}
	
	@Override
	public void encode(YoyooMethod decl) {
		
		String methodName = decl.getName();
		if(methodName.startsWith("set") 
				|| methodName.startsWith("get")
				|| methodName.startsWith("object2json")) {
			super.encode(decl);
		}
	}

//	@Override
//	protected void appendMethodReturningDecl(YoyooMethod decl, int paramCount) {
//		String methodName = decl.getName();
//		if(methodName.startsWith("get")) {
//			IType returnType = decl.getReturnType();
//			if(!PrimitiveType.isVoid(returnType)) {
//				if(paramCount>0) {
//					appendComma();
//					appendSpace();
//				}
//				String abapStyleMethodName = toABAPStyleName(methodName);
//				int indexOf = abapStyleMethodName.indexOf("_");
//				appendReturningValue("r_"+abapStyleMethodName.substring(indexOf));
//				appendSpace();
//				appendType();
//				appendSpace();
//				appendParamType(returnType);
//			} 
//		}
//		else {
//			super.appendMethodReturningDecl(decl, paramCount);
//		}
//			
//	}
	
	@Override
	protected boolean hasMethodDecl(YoyooTypeDefineClass decl, Modifier modifier) {
		
		SymbolTable<YoyooMethod> methods = decl.getMethods();
		
		if(!methods.isEmpty()) {
			for(YoyooMethod method : methods.getSymbols().values()) {
				if(method!=null && method.getModifier()==modifier) {
					String methodName = method.getName();
					if(methodName.startsWith("set") 
							|| methodName.startsWith("get")
							|| methodName.startsWith("object2json")) {
						return true;
					}
				}
			}
		}

		return false;
	}
	
	@Override
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
	
	protected void encodeInheritDecl(YoyooTypeDefineClass decl) {
		if(decl.getExtendsReferenceType()!=null) {
			String extendsName = decl.getExtendsReferenceType().getName();
			if(extendsName!=null && !StringUtil.equals("AbstractEntityPlainObject", extendsName)) {
				appendSpace();
				appendKeyWord(KeyWord.KeyExtends);
				appendSpace();
				append(decl.getExtendsReferenceType().getName());
			}
		}
	}
	
	@Override
	protected void encodeCondExpr(Expr expr) {
		if(expr instanceof PrimaryExpr) {
			IAtom prefixAtom = ((PrimaryExpr) expr).getPrefixAtom();
			if(prefixAtom instanceof ReferenceAtom) {
				String name = ((ReferenceAtom) prefixAtom).getName();
				if(name.equals("includeReference")) {
					append("'X' == includeReference");
				}
			}
		} else if(expr instanceof BinOpExpr) {
			Expr rightExpr = ((BinOpExpr) expr).getRightExpr();
			if(rightExpr instanceof PrimaryExpr) {
				IAtom prefixAtom = ((PrimaryExpr) rightExpr).getPrefixAtom();
				if(prefixAtom instanceof NullAtom) {
					encode(((BinOpExpr) expr).getLeftExpr());
					append(" IS NOT INITIAL");
				}
			}
		} else {
			super.encodeCondExpr(expr);
		}
		
	}
}
