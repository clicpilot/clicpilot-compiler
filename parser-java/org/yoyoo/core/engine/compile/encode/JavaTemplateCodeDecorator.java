package org.yoyoo.core.engine.compile.encode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.PrimaryExpr;
import org.yoyoo.core.engine.compile.stm.ReturnStm;
import org.yoyoo.core.engine.compile.stm.StmExpr;
import org.yoyoo.core.engine.compile.type.IType;

public class JavaTemplateCodeDecorator extends DefaultCodeDecorator {

	protected static Map<String, String> packageMappings;
	
	protected static List<String> uselessYoyooPackages;
	
	public static List<String> requiredJavaPackages;
	
	public static Map<String, String> classesMappingTable = new HashMap<String, String>();
	
	private boolean isStr = false;
	private StmExpr strStmExpr = null;
	
	static {
		uselessYoyooPackages = new ArrayList<String>();
		
		
		requiredJavaPackages = new ArrayList<String>();
		requiredJavaPackages.add("java.util.*");
		requiredJavaPackages.add("org.yoyoo.core.engine.compile.encode.*");
		
		packageMappings = new HashMap<String, String>();
		
		
		classesMappingTable.put("string", "String");
		
	}
	
	@Override
	protected void appendImports() {
		Set<String> importedPackages = new HashSet<String>();
		for(String importPackage : unit.getImports()) {
//			String yoyooPackage = getPackageName(importPackage);
			if(uselessYoyooPackages.contains(importPackage)) {
				continue;
			} else if(packageMappings.containsKey(importPackage) && !importedPackages.contains(packageMappings.get(importPackage))){
				appendImport(packageMappings.get(importPackage));
				importedPackages.add(packageMappings.get(importPackage));
			}
			else if(!importedPackages.contains(importPackage)) {
				appendImport(importPackage);
				importedPackages.add(importPackage);
			}
				
		}
		for(String importPackage : requiredJavaPackages) {
			appendImport(importPackage);
		}
		
		
	}
	
	protected void appendImport(String importPackage) {
		appendKeyWord(KeyWord.KeyImport);
		appendSpace();
		append(importPackage);
		appendStatementEnd();
		appendLineBreak();		
	}
	
	
//	private String getPackageName(String pkg) {
//		int sepIndex = pkg.lastIndexOf(".");
//		return pkg.substring(0, sepIndex);
//	}
	
	@Override
	protected void appendTypeName(IType type) {
		if(classesMappingTable.containsKey(type.getName())) {
			append(classesMappingTable.get(type.getName()));
		} else {
			append(type.getName());
		}
	}
	
	@Override
	protected void appendQuotedString(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("\"", "\\\"");
		str = str.replace("\r", "");
		str = str.replace("\n", "\\n\"+\n\"");
		buffer.append("\""+str+"\"");
	}
	
	@Override
	public void encode(YoyooMethod decl) {
		appendTab(1);
		buffer.append("StringBuffer codeBuffer = new StringBuffer();");
		appendLineBreak();
		super.encode(decl);
	}
	
	@Override
	public void encode(PrimaryExpr expr) {
		Expr prefixExpr = expr.getPrefixExpr();
		
		if(prefixExpr==null) {
			IAtom prefixAtom = expr.getPrefixAtom();
			if(prefixAtom instanceof ReferenceAtom) {
				String name = ((ReferenceAtom)prefixAtom).getName();
				if(name.equals("str")) {
					isStr = true;
					buffer.append("codeBuffer.append");
					
					return;
				}
			}
				
		}
		

		super.encode(expr);
	}
	
	@Override
	public void encode(StmExpr stm) {

		if(stm.getPreOptMark()!=null)
			append(stm.getPreOptMark().getOpt().getStr());
		encode(stm.getExpr());
		if(stm.getPostOptMark()!=null)
			append(stm.getPostOptMark().getOpt().getStr());
		if(stm.getAssignOptMark()!=null) {
			if(isStr) {
				append("(");
				strStmExpr = stm;
			} else {
				append(stm.getAssignOptMark().getOpt().getStr());
			}
		}
		if(stm.getAssignExpr()!=null)
			encode(stm.getAssignExpr());
		
		if(isStr && strStmExpr == stm) {
			append(")");
			isStr = false;
			strStmExpr = null;
		}
	}
	
	@Override
	public void encode(ReturnStm stm) {
		buffer.append("str = codeBuffer.toString();");
		buffer.append("codeBuffer = new StringBuffer();");
		appendKeyWord(KeyWord.KeyReturn);
		appendSpace();
		buffer.append("str");
		appendStatementEnd();
		//appendLineBreak();

	}
}
