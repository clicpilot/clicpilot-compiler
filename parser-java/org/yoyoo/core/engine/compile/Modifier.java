package org.yoyoo.core.engine.compile;

public enum Modifier {
	PUBLIC, PRIVATE, PROTECTED, DEFAULT;
	public static Modifier modifier(String modifier) {
		if (modifier.equals("public"))
			return PUBLIC;
		else if (modifier.equals("private"))
			return PRIVATE;
		else
			return PROTECTED;

	}

}
