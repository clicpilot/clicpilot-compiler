package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.declaration.YoyooPrimitiveClass;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class FieldAssignOperator extends VariableAssignOperator {

	private boolean staticField;
	
	public FieldAssignOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls, IType type, IAtom assignAtom, String name, boolean staticField) {
		super(node, unit, ycls, type, assignAtom, name);
		this.staticField = staticField;
	}

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		IRuntimeValueAtom thisAtom = ctx.lookupThisAtom(node, unit);		
		YoyooObject obj = null;
		YoyooObject val = thisAtom.getValue();
		
		
		if (assignAtom != null) {
			obj = assign(ctx);
			obj.setInstanceName(name);
			// obj = ((ValueAtom)assignAtom.getVal(ctx)).getValue();
		} else {
			YoyooClass myycls = type.map2YoyooClass(YoyooEnvironment.getDefault()) ;
			
			if(myycls instanceof YoyooTypeDefineClass) {
				YoyooObject innerObj = ((YoyooTypeDefineClass)myycls).createStaticInstance(ctx);
				innerObj.setInstanceName(name);
				obj = new YoyooNull(innerObj);
				obj.setInstanceName(name);
				innerObj.setFieldOwner(val);
			} else {
				YoyooObject innerObj = ((YoyooPrimitiveClass)myycls).instance(ctx);
				innerObj.setInstanceName(name);
				obj = new YoyooNull(innerObj);
				obj.setInstanceName(name);
				innerObj.setFieldOwner(val);
			}	
		}
		
		if(staticField) {
			((YoyooTypeDefine) val).getUnit().setStaticFieldsValue(name, obj);
		} else {
			((YoyooTypeDefine) val).setFieldValue(name, obj);
		}
		obj.setFieldOwner(val);
		
		
		return null;
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

	}

}
