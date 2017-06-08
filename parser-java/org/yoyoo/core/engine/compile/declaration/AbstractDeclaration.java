package org.yoyoo.core.engine.compile.declaration;

import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.TypeVisitor;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooConstructorName;
import org.yoyoo.core.engine.parser.YoyooDeclarationName;
import org.yoyoo.core.engine.parser.YoyooExtendsName;
import org.yoyoo.core.engine.parser.YoyooFormalParameterName;
import org.yoyoo.core.engine.parser.YoyooMethodName;
import org.yoyoo.core.engine.parser.YoyooModifier;
import org.yoyoo.core.engine.parser.YoyooPrimitiveType;
import org.yoyoo.core.engine.parser.YoyooRef;
import org.yoyoo.core.engine.parser.YoyooReferenceType;
import org.yoyoo.core.engine.parser.YoyooSpecialExprType;
import org.yoyoo.core.engine.parser.YoyooTypeArgumentType;
import org.yoyoo.core.engine.parser.YoyooVariableDeclaratorId;
import org.yoyoo.core.engine.parser.YoyooVoidType;


public abstract class AbstractDeclaration extends YoyooParserVisitorAdapter implements IDeclaration {

	protected Modifier modifier;

	protected String name;

	protected YoyooTypeDefineClass unit;

	protected SimpleNode node;
	
	protected IType type;	


	public AbstractDeclaration(YoyooTypeDefineClass unit, SimpleNode node) {
		this.unit = unit;
		this.node = node;
	}

	public AbstractDeclaration(SimpleNode node) {
		this.node = node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooMethodName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooMethodName node, Object data) {
		String name = node.first_token.image;
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooDeclarationName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooDeclarationName node, Object data) {
		String name = node.first_token.image;
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooFormalParameterName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooFormalParameterName node, Object data) {
		String name = node.first_token.image;
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.stm.AbstractStm#visit(org.yoyoo.core.engine.parser.YoyooModifier,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooModifier node, Object data) {
		modifier = Modifier.modifier(node.first_token.image);
		return null;
	}


	@Override
	public Object visit(YoyooPrimitiveType node, Object data) {

		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		node.jjtAccept(v, data);
		type = v.getType();
		return type;
	}


	@Override
	public Object visit(YoyooReferenceType node, Object data) {
		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		node.jjtAccept(v, data);
		type = v.getType();
		return type;
	}

	@Override
	public Object visit(YoyooSpecialExprType node, Object data) {
		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		node.jjtAccept(v, data);
		type = v.getType();
		return type;
	}
	
	@Override
	public Object visit(YoyooRef node, Object data) {
		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		node.jjtAccept(v, data);
		type = v.getType();
		return type;
	}

	@Override
	public Object visit(YoyooTypeArgumentType node, Object data) {
		TypeVisitor v = new TypeVisitor(unit.getCompilationUnit(), node);
		node.childrenAccept(v, data);
		type = v.getType();
		return type;
	}

	@Override
	public Object visit(YoyooVoidType node, Object data) {
		return new PrimitiveType.YoyooVoid(unit.getCompilationUnit(), node);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooVariableDeclaratorId,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooVariableDeclaratorId node, Object data) {
		return node.first_token.image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooConstructorName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooConstructorName node, Object data) {
		return node.first_token.image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.yoyoo.core.engine.compile.YoyooParserVisitorAdapter#visit(org.yoyoo.core.engine.parser.YoyooExtendsName,
	 *      java.lang.Object)
	 */
	@Override
	public Object visit(YoyooExtendsName node, Object data) {
		return node.first_token.image;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the modifier
	 */
	public Modifier getModifier() {
		return modifier;
	}

	/**
	 * @return the unit
	 */
	public YoyooTypeDefineClass getUnit() {
		return unit;
	}

	
	
	public SimpleNode getNode() {
		return node;
	}
	




}
