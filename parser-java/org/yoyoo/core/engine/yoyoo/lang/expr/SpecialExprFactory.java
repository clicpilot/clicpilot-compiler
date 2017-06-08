package org.yoyoo.core.engine.yoyoo.lang.expr;

import java.util.HashMap;
import java.util.Map;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;



public class SpecialExprFactory {
	
	private final static Map<String, ISpecialExprProvider> map = new HashMap<String, ISpecialExprProvider>();
	
	private static SpecialExprFactory factory;
	
	static {
		SpecialExprFactory.getInstance().registerExpr("Reg", new Reg());
		//SpecialExprFactory.getInstance().registerExpr("Yql");		
	}
	
	private SpecialExprFactory() {
		
	}
	
	public static SpecialExprFactory getInstance() {
		if(factory == null) {
			factory = new SpecialExprFactory();
		}
		return factory;
	}
	
	public void registerExpr(String name, ISpecialExprProvider p) {
		
		
//		try {
			if(p != null) {
//				map.put(name, cls.newInstance());
				map.put(name, p);
			}
				
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void unregisterExpr(String name) {
		map.remove(name);
	}
	
	public ISpecialExpr generateExpr(String name, SimpleNode node, CompilationUnit unit, String body, YoyooTypeDefine parent) throws CompileException{
		if(map.containsKey(name)) {
			return map.get(name).createExpr(node, unit, body, parent);
		} else {
			throw new CompileException.SpecialExprNotDefined(name, node, unit);
		}
	}
	
}
