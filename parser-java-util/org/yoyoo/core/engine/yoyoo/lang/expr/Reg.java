package org.yoyoo.core.engine.yoyoo.lang.expr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class Reg extends AbstractSpecialExpr implements ISpecialExprProvider{
	
	protected Reg(){};
	
	

	private Reg(SimpleNode node, CompilationUnit unit, String body, YoyooTypeDefine parent) {
		super(node, unit, body, parent);
	}


	private final static String NAME = "Reg";
	

	
	public void syntaxCheck() throws CompileException {
		try{
			Pattern.compile(body);
		} catch(Exception e) {
			throw new CompileException.SpecialExprNotCompiled(NAME, e.getMessage(), node, unit);
		}
	}

	public ISpecialExpr createExpr(SimpleNode node, CompilationUnit unit, String body, YoyooTypeDefine parent) {
		return new Reg(node, unit, body, parent);
	}
	



	public void execute() throws YoyooRTException {
		
	}



	@Override
	public void execute(YoyooObject params,
			YoyooRef<YoyooObject> returnRef, RuntimeContext ctx) throws YoyooRTException {
		if(! (params instanceof YoyooString) || params == null) {
			throw new YoyooRTException.CannotEval(node, unit, ctx);
		}
		Pattern p = Pattern.compile(body);
		Matcher m = p.matcher(((YoyooString)params).getVal());
		if(returnRef!=null)
			returnRef.setValue(new YoyooBoolean(m.matches()));
		
	}





	
	
	
}
