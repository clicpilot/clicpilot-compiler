package org.yoyoo.core.engine.yoyoo.lang;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.type.SpecialExprType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.expr.ISpecialExpr;
import org.yoyoo.core.engine.yoyoo.lang.expr.SpecialExprFactory;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class YoyooSpecialExpression extends YoyooObject {
	
	private SpecialExprType exprType;
	
	private String body;
	
	private YoyooTypeDefine parent;
	
	private ISpecialExpr expr;
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0!=null && arg0 instanceof YoyooSpecialExpression)
			if(((YoyooSpecialExpression)(arg0)).getBody()!=null
					&& ((YoyooSpecialExpression)(arg0)).getBody().equals(body)
					&& ((YoyooSpecialExpression)(arg0)).getExprType()!=null
					&& ((YoyooSpecialExpression)(arg0)).getExprType().getExprTypeName().equals(exprType.getExprTypeName())
					&& ((YoyooSpecialExpression)(arg0)).getParent()==parent)
				return true;
			else 
				return false;
		else
			return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return exprType.getName()+"("+body+")";
	}

	public ISpecialExpr getExpr() {
		return expr;
	}

	public void compile(SimpleNode node, CompilationUnit unit) throws CompileException {
		expr = SpecialExprFactory.getInstance().generateExpr(exprType.getExprTypeName(), node, unit, body, parent);
		expr.syntaxCheck();
	}
	
	public void execute(YoyooObject params, YoyooRef<YoyooObject> returnRef, RuntimeContext ctx) throws YoyooRTException {
		expr.execute(params, returnRef, ctx);
	}
	
	public void execute(RuntimeContext ctx) throws YoyooRTException {
		expr.execute(null, null, ctx);
	}

	public YoyooSpecialExpression() {
		super();
	}
	
	public YoyooSpecialExpression(SpecialExprType exprType, String body, YoyooTypeDefine parent) {
		super();
		this.exprType = exprType;
		this.body = body;
		this.parent = parent;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	

	public SpecialExprType getExprType() {
		return exprType;
	}

	public YoyooTypeDefine getParent() {
		return parent;
	}

	public void setExprType(SpecialExprType exprType) {
		this.exprType = exprType;

	}
	
	@Override
	public Object cloneAtom() throws YoyooRTException {
		return this;
	}
}
