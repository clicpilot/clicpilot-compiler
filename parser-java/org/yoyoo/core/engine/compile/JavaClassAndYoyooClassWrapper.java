package org.yoyoo.core.engine.compile;

import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.yoyoo.lang.IYoyooObject;


public class JavaClassAndYoyooClassWrapper {
	private Class<? extends IYoyooObject>  cls;

	private YoyooTypeDefineClass ycls;

	public JavaClassAndYoyooClassWrapper(YoyooTypeDefineClass ycls) {
		super();
		this.ycls = ycls;
		this.cls = this.ycls.getTypeClass();
	}

	public JavaClassAndYoyooClassWrapper(Class<? extends IYoyooObject>  cls) {
		super();
		this.cls = cls;
	}

	public boolean isYoyooClass() {
		return ycls == null;
	}

	public Class<? extends IYoyooObject>  getCls() {
		return cls;
	}

	public YoyooClass getYcls() {
		return ycls;
	}
}
