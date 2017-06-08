package org.yoyoo.core.engine.compile.encode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yoyoo.core.engine.compile.type.IType;




public class Java2YoyooCodeDecorator extends DefaultCodeDecorator {

	protected static Map<String, String> packageMappings;
	
	protected static List<String> uselessJavaPackages;
	
	public static List<String> requiredYoyooPackages;
	
	public static Map<String, String> classesMappingTable = new HashMap<String, String>();
	
	static {
		uselessJavaPackages = new ArrayList<String>();
		uselessJavaPackages.add("java.util");
		
		requiredYoyooPackages = new ArrayList<String>();
		requiredYoyooPackages.add("yoyoo.lang");
		
		classesMappingTable.put("String", "string");
		classesMappingTable.put("Integer", "int");
		classesMappingTable.put("Short", "short");
		classesMappingTable.put("Long", "long");
		classesMappingTable.put("Float", "float");
		classesMappingTable.put("Double", "double");
		classesMappingTable.put("Boolean", "boolean");
		
		packageMappings = new HashMap<String, String>();
		packageMappings.put("org.w3c.dom", "yoyoo.lang.xml");
		
	}
	
	
	@Override
	protected void appendImports() {
		Set<String> importedPackages = new HashSet<String>();
		for(String importPackage : unit.getImports()) {
			String javaPackage = getPackageName(importPackage);
			if(uselessJavaPackages.contains(javaPackage)) {
				continue;
			} else if(packageMappings.containsKey(javaPackage) && !importedPackages.contains(packageMappings.get(javaPackage))){
				appendImport(packageMappings.get(javaPackage));
				importedPackages.add(packageMappings.get(javaPackage));
			}
			else if(!importedPackages.contains(javaPackage)) {
				appendImport(javaPackage);
				importedPackages.add(javaPackage);
			}
				
		}
		for(String importPackage : requiredYoyooPackages) {
			appendImport(importPackage);
		}
		
		
	}
	
	private void appendImport(String importPackage) {
		appendKeyWord(KeyWord.KeyImport);
		appendSpace();
		append(importPackage);
		appendStatementEnd();
		appendLineBreak();		
	}
	
	private String getPackageName(String pkg) {
		int sepIndex = pkg.lastIndexOf(".");
		if(sepIndex==-1) {
			System.err.println(pkg);
			return pkg;
		}
		return pkg.substring(0, sepIndex);
	}
	
	@Override
	protected void appendTypeName(IType type) {
		if(classesMappingTable.containsKey(type.getName())) {
			append(classesMappingTable.get(type.getName()));
		} else {
			append(type.getName());
		}
	}
	
	
	
//	private String getClassName(String pkg) {
//		int sepIndex = pkg.lastIndexOf(".");
//		return pkg.substring(sepIndex+1);
//	}

	

}
