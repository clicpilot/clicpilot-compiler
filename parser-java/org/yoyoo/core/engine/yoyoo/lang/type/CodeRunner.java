package org.yoyoo.core.engine.yoyoo.lang.type;

import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;


public class CodeRunner extends YoyooTypeDefine {

	public void run(YoyooString typeunitPkg, YoyooString typeunitName, YoyooString functionName) {
		
		RuntimeContext.getNewContext().run(typeunitPkg.getVal(), typeunitName.getVal(), functionName.getVal());
		
	}
	
}
