package org.yoyoo.core.engine.compile.encode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;




public class JavaCodeDecorator extends DefaultCodeDecorator {

	protected static Map<String, String> packageMappings;
	
	protected static List<String> uselessYoyooPackages;
	
	public static List<String> requiredJavaPackages;
	
	public static Map<String, String> classesMappingTable = new HashMap<String, String>();
	
	static {
		uselessYoyooPackages = new ArrayList<String>();
		uselessYoyooPackages.add("yoyoo.lang");
		
		requiredJavaPackages = new ArrayList<String>();
		requiredJavaPackages.add("java.util");
		
		packageMappings = new HashMap<String, String>();
		packageMappings.put("yoyoo.lang.xml", "org.w3c.dom");
		
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
		append(importPackage+".*");
		appendStatementEnd();
		appendLineBreak();		
	}
	
//	private String getPackageName(String pkg) {
//		int sepIndex = pkg.lastIndexOf(".");
//		return pkg.substring(0, sepIndex);
//	}
	
	@Override
	protected void appendTypeName(IType type) {
		if(type instanceof ReferenceNameType && ((ReferenceNameType)type).getPkgname()!=null) {
			String pkgname = ((ReferenceNameType)type).getPkgname();
			boolean imported = false;
			for(String importPackage : unit.getImports()) {
				if(importPackage.equals(pkgname)) {
					imported = true;
					break;
				}
			}
			if(!imported) {
				append(((ReferenceNameType)type).getPkgname());
				appendDotMark();
			}
		}
		
		if(classesMappingTable.containsKey(type.getName())) {
			append(classesMappingTable.get(type.getName()));
		} else {
			append(type.getName());
		}
		
//		if(type.isArray()) {
//			appendArrayBracketStart();
//			appendArrayBracketEnd();
//		}
	}
	
	@Override
	protected void appendQuotedString(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("\"", "\\\"");
		str = str.replace("\r", "");
		str = str.replace("\n", "\\n\"+\n\"");
		buffer.append("\""+str+"\"");
	}
	
	
//	private String getClassName(String pkg) {
//		int sepIndex = pkg.lastIndexOf(".");
//		return pkg.substring(sepIndex+1);
//	}

	

}
