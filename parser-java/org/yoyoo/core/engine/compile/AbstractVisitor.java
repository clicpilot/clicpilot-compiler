package org.yoyoo.core.engine.compile;

import org.yoyoo.core.engine.parser.SimpleNode;

public abstract class AbstractVisitor extends YoyooParserVisitorAdapter {

	protected CompilationUnit unit;
	protected SimpleNode node;

	public AbstractVisitor(CompilationUnit unit, SimpleNode node) {
		super();
		this.unit = unit;
		this.node = node;
	}

//	public Object visit(YoyooPrimitiveType node, Object data) {
//		return PrimitiveType.TYPE.YoyooVoid.type(node.first_token.image);
//	}
//
//	public Object visit(YoyooReferenceNameType node, Object data) {
//		return TypeParserHelper.parseYoyooReferenceNameType(unit, node);
//
//	}

}
