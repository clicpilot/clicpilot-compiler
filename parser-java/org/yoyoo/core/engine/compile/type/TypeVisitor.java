package org.yoyoo.core.engine.compile.type;

import org.yoyoo.core.engine.compile.AbstractVisitor;
import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooAllocationName;
import org.yoyoo.core.engine.parser.YoyooExtendsName;
import org.yoyoo.core.engine.parser.YoyooImplementsName;
import org.yoyoo.core.engine.parser.YoyooPrimitiveType;
import org.yoyoo.core.engine.parser.YoyooRef;
import org.yoyoo.core.engine.parser.YoyooReferenceType;
import org.yoyoo.core.engine.parser.YoyooSpecialExprType;
import org.yoyoo.core.engine.parser.YoyooThrowName;
import org.yoyoo.core.engine.parser.YoyooVoidType;

public class TypeVisitor extends AbstractVisitor{

	private IType type;
	@Override
	public Object visit(YoyooPrimitiveType node, Object data) {
		PrimitiveType type = new PrimitiveType(unit, node);
		node.childrenAccept(type, data);
		this.type = type;
		return null;
	}

	@Override
	public Object visit(YoyooReferenceType node, Object data) {
		ReferenceNameType type = new ReferenceNameType(unit, node, unit.getClassInCompiling());
		node.childrenAccept(type, data);
		this.type = type;
		unit.addRefernece(type);
		return null;
	}

	@Override
	public Object visit(YoyooSpecialExprType node, Object data) {
		SpecialExprType type = new SpecialExprType(unit, node);
		node.childrenAccept(type, data);
		this.type = type;
		return null;
	}
	
	

	@Override
	public Object visit(YoyooRef node, Object data) {
		RefType type = new RefType(unit, node);
		node.childrenAccept(type, data);
		this.type = type;
		return null;
	}

	
	
	@Override
	public Object visit(YoyooThrowName node, Object data) {
		ReferenceNameType type = new ReferenceNameType(unit, node, unit.getClassInCompiling());
		node.jjtAccept(type, data);
		this.type = type;
		unit.addRefernece(type);
		return null;
	}
	
	@Override
	public Object visit(YoyooExtendsName node, Object data) {
		ReferenceNameType type = new ReferenceNameType(unit, node, unit.getClassInCompiling());
		node.childrenAccept(type, data);
		this.type = type;
		unit.addRefernece(type);
		return null;
	}
	
	@Override
	public Object visit(YoyooImplementsName node, Object data) {
		ReferenceNameType type = new ReferenceNameType(unit, node, unit.getClassInCompiling());
		node.childrenAccept(type, data);
		this.type = type;
		unit.addRefernece(type);
		return null;
	}
	
	@Override
	public Object visit(YoyooAllocationName node, Object data) {
		ReferenceNameType type = new ReferenceNameType(unit, node, unit.getClassInCompiling());
		node.childrenAccept(type, data);
		this.type = type;
		unit.addRefernece(type);
		return null;
	}
	
	@Override
	public Object visit(YoyooVoidType node, Object data) {
		this.type = new PrimitiveType.YoyooVoid(unit, node);
		return null;
	}

	public TypeVisitor(CompilationUnit unit, SimpleNode node) {
		super(unit, node);
	}

	public IType getType() {
		return type;
	}

}
