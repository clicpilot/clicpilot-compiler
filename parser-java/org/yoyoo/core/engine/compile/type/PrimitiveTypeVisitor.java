package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.type.PrimitiveType.TYPE;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooPrimitiveTypeName;


public class PrimitiveTypeVisitor extends AbstractVisitor {

	public PrimitiveTypeVisitor(CompilationUnit unit, SimpleNode node) {
		super(unit, node);
		// TODO Auto-generated constructor stub
	}
	private TYPE type;
	
	private IType valueType;
	
	public IType getValueType() {
		return valueType;
	}
	@Override
	public Object visit(YoyooPrimitiveTypeName node, Object data) {
		String typeName = node.first_token.image;
		if (typeName.equals("boolean")) {
			type = TYPE.YoyooBoolean;
		} else if (typeName.equals("string")) {
			type = TYPE.YoyooString;
		} else if (typeName.equals("short")) {
			type = TYPE.YoyooShort;
		} else if (typeName.equals("int")) {
			type = TYPE.YoyooInteger;
		} else if (typeName.equals("float")) {
			type = TYPE.YoyooFloat;
		} else if (typeName.equals("long")) {
			type = TYPE.YoyooLong;
		} else if (typeName.equals("double")) {
			type = TYPE.YoyooDouble;
		} else if (typeName.equals("char")) {
			type = TYPE.YoyooChar;
		} else if (typeName.equals("void")) {
			type = TYPE.YoyooVoid;
		}
		return null;
	}
	public TYPE getType() {
		return type;
	}

	
}
