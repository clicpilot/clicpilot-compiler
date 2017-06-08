package org.yoyoo.core.engine.compile.type;


public class TypeParserHelperbk {

//	public static ReferenceNameType parseYoyooReferenceNameType(
//			CompilationUnit unit, YoyooReferenceNameType node) {
//		Token token1 = node.first_token;
//		Token token2 = node.last_token;
//		if (token1 != token2) {
//			StringBuffer sb = new StringBuffer();
//			while (token1 != token2) {
//				sb.append(".");
//				sb.append(token1.image);
//				token1 = token1.next;
//			}
//			return unit.addRefernece(sb.toString() + "." + token2.image, node);
//
//		} else {
//			return unit.addRefernece(token2.image, node);
//		}
//	}
//
//	public static ReferenceNameType createReferenceNameType(String pkgName,
//			String name, SimpleNode node) {
//		return new ReferenceNameType(pkgName, name);
//
//	}
//
//	public static ReferenceNameType createReferenceNameType(String name) {
//		return new ReferenceNameType(name);
//	}
}
