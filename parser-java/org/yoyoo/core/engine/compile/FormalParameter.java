package org.yoyoo.core.engine.compile;

import org.yoyoo.core.engine.compile.type.IType;

public class FormalParameter {
	private IType type;

	private String name;

	public FormalParameter(IType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public FormalParameter() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public IType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(IType type) {
		this.type = type;
	}

}
