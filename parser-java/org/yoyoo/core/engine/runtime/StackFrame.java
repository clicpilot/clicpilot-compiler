package org.yoyoo.core.engine.runtime;

import java.util.HashMap;
import java.util.Map;
//import java.util.StringTokenizer;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.opt.ArrayIndexOperator;
import org.yoyoo.core.engine.compile.opt.DotOperator;
import org.yoyoo.core.engine.compile.opt.StopOperator;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooRef;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class StackFrame {
	// private Stack<Operator> operatorStack;
	private Map<String, IRuntimeValueAtom> variables;

	private RuntimeContext ctx;

	private StopOperator skipTo;

	public StackFrame(RuntimeContext ctx) {
		super();
		this.ctx = ctx;
	}

	public StackFrame(RuntimeContext ctx, StackFrame frame)
			throws YoyooRTException {
		super();
		this.ctx = ctx;
		variables = new HashMap<String, IRuntimeValueAtom>();
		for (String name : frame.variables.keySet()) {
			variables.put(name, (IRuntimeValueAtom) frame.variables.get(name));
		}
	}

	// public void pushOperator(Operator opt)
	// {
	// if(operatorStack==null)
	// operatorStack = new Stack<Operator> ();
	// operatorStack.push(opt);
	// }

	// public void execute() throws YoyooRuntimeException
	// {
	// if(operatorStack!=null)
	// {
	// while(!operatorStack.isEmpty())
	// {
	// operatorStack.pop().eval(ctx);
	// }
	// }
	//		
	// }

	public void registerVariable(String name, IRuntimeValueAtom atom, SimpleNode node, CompilationUnit unit)
			throws YoyooRTException {
		if (variables == null)
			variables = new HashMap<String, IRuntimeValueAtom>();
		if (variables.containsKey(name)) {
			System.err.println("Variable " + name + " has existed! at "+unit.getSource().getFile().getPath()+" ("+node.first_token.beginLine+","+node.first_token.beginColumn+")");
			
		} else {
			variables.put(name, atom);
		}
	}

	public void updateVariable(IAtom atom, IRuntimeValueAtom runtimeAtom,
			SimpleNode node, CompilationUnit unit) throws YoyooRTException {
		if (variables == null)
			variables = new HashMap<String, IRuntimeValueAtom>();
		switch (atom.getAtomType()) {
		case Ref:
			String name = ((ReferenceAtom) atom).getName();
			if (variables.containsKey(name)) {
				variables.get(name).setValue(runtimeAtom.getValue());
			} else {
				IRuntimeValueAtom returnAtom = variables
						.get(RuntimeContext.THIS);
				if (returnAtom != null) {
					YoyooObject obj = returnAtom.getValue();
					if (obj instanceof YoyooTypeDefine) {
//						((YoyooTypeDefine) obj).setFieldValue(name, runtimeAtom
//								.getValue());
						setValue((YoyooTypeDefine) obj, name, runtimeAtom.getValue(), node, unit);
						return;
					}
				}
				throw new YoyooRTException.VariableExist(name, node, unit, ctx);
			}
			break;
		case Operator:
			if (atom instanceof DotOperator) {
				((DotOperator) atom).updateValue(ctx, runtimeAtom);
			}else if (atom instanceof ArrayIndexOperator) {
				((ArrayIndexOperator) atom).updateValue(ctx, runtimeAtom);
			}else {
				throw new YoyooRTException.CannotEval(node, unit, ctx);
			}
			break;
		case Value:
			throw new YoyooRTException.CannotEval(node, unit, ctx);

		}

	}
	
	private void setValue(YoyooTypeDefine obj, 
			String name, YoyooObject val, 
			SimpleNode node, 
			CompilationUnit unit) throws YoyooRTException {
		
		int firstDotIndex = name.indexOf(".");
		if(firstDotIndex==-1) {
			if(obj.containsField(name)) {
				
				//YoyooObject fieldValue = obj.getFieldValue(name);
				
				obj.setFieldValue(name, val);
			}
			else {
				System.out.println("cannot set value to field " + name +" in "+obj.getUnit().getName());
			}
		}
		
		else {
			String subname = name.substring(0, firstDotIndex);
			if(subname.equals(RuntimeContext.SUPER)) {
				subname = name.substring(firstDotIndex+1);
				if(obj.containsField(subname))
				{
					setValue(((YoyooTypeDefine)obj), 
							subname, val, 
							node, unit);
				}
			}
			
			else if(variables.containsKey(subname)){
				YoyooObject nextObj = variables.get(subname).getValue();
				if(nextObj!=null)
				{
					if(nextObj instanceof YoyooTypeDefine) {
						setValue(((YoyooTypeDefine)nextObj), 
								name.substring(firstDotIndex+1), val, 
								node, unit);
					} else if(nextObj instanceof YoyooNull)	{
						throw new YoyooRTException.ObjectIsNull(subname, node, unit, ctx);
					}
				}
				else {
					System.out.println("cannot set value to field " + name +" in "+obj.getUnit().getName());
				}
			}
			else if(obj.containsField(subname))
			{
				YoyooObject nextObj = obj.getFieldValue(subname);
				if(nextObj!=null)
				{
					if(nextObj instanceof YoyooTypeDefine) {
						setValue(((YoyooTypeDefine)nextObj), 
								name.substring(firstDotIndex+1), val, 
								node, unit);
					} else if(nextObj instanceof YoyooNull)	{
						throw new YoyooRTException.ObjectIsNull(subname, node, unit, ctx);
					}
				}
				else {
					System.out.println("cannot set value to field " + name +" in "+obj.getUnit().getName());
				}
			} else {
				System.out.println("cannot set value to field " + name +" in "+obj.getUnit().getName());
			}
		}
	}

	public IRuntimeValueAtom lookupVariable(String name, SimpleNode node,
			CompilationUnit unit) throws YoyooRTException {
		if (variables != null) {
			IRuntimeValueAtom returnAtom = null;
			if (name.indexOf(".") != -1) {
				StringTokenizer stk = new StringTokenizer(name, ".");
				boolean first = true;
				YoyooObject lastObj = null;
				while (stk.hasMoreElements()) {
					String subname = (String) stk.nextElement();
					if(subname.equals(RuntimeContext.SUPER)) {
						subname = RuntimeContext.THIS;
					}
					if (first) {
						first = false;
						lastObj = findObjectMember(subname, node, unit);
						if(lastObj==null)
						{
							IRuntimeValueAtom var = variables.get(subname);
							if (var != null) {
								lastObj = var.getValue();
							}
							else {
								if(unit.isStaticReference(subname)) {
									returnAtom = ((YoyooTypeDefineClass)(unit.getStaticReferenceYoyooObject(subname))).createInstance(ctx);									
									lastObj = returnAtom.getValue();
								} else {
									System.err.println("Variable " + subname + " cannot be found!");
									printErrorCallStack();
								}
							}
						}
						
					} else {
						if(lastObj instanceof YoyooArray) {
							lastObj = YoyooArray.fieldValue(((YoyooArray)lastObj), subname, ctx);
						} else if(lastObj instanceof YoyooRef) {
							lastObj = findObjectMember((YoyooTypeDefine)((YoyooRef<?>) lastObj).getValue(),
									subname);
						} else {
							if(lastObj instanceof YoyooNull) {
								YoyooRTException variableIsNull = new YoyooRTException.VariableIsNull(lastObj.getInstanceName(), node, unit, ctx);
								
								throw variableIsNull;
							} else {
								lastObj = findObjectMember((YoyooTypeDefine) lastObj,
										subname);
							}
						}
						if(lastObj==null)
						{
							
							throw new YoyooRTException.VariableIsNull(subname, node, unit, ctx);
						}
						
					}
				}
				if (lastObj != null)
					returnAtom = new RuntimeValueAtom(lastObj, node, unit);
			} else {
				returnAtom = variables.get(name);
				if (returnAtom == null) {
					YoyooObject val = findObjectMember(name, node, unit);
					if (val != null)
						returnAtom = new RuntimeValueAtom(val, node, unit);
					
				}

			}
			if (returnAtom == null) {
				if(unit.isStaticReference(name)) {					
					//returnAtom = new RuntimeValueAtom(unit.getStaticReferenceYoyooObject(name).instance(ctx), node, unit);
					returnAtom = ((YoyooTypeDefineClass)(unit.getStaticReferenceYoyooObject(name))).createInstance(ctx);	
				} else {
					System.err.println("Variable " + name + " cannot be found!");	
					printErrorCallStack();
				}
			}
			return returnAtom;
			
		} else {
			System.err.println("Variable " + name + " cannot be found!");
			printErrorCallStack();
			return null;
		}
	}

	private void printErrorCallStack() {
		try{
			throw new IllegalStateException();
		} catch(Exception e) {
			ctx.printCallStack();
			e.printStackTrace();
			
		}
	}
	private YoyooObject findObjectMember(String name, SimpleNode node,
			CompilationUnit unit) throws YoyooRTException {
		IRuntimeValueAtom thisAtom = variables.get(RuntimeContext.THIS);
		if (thisAtom != null) {
			YoyooObject obj = thisAtom.getValue();
			if (obj instanceof YoyooTypeDefine) {
				YoyooObject objmember = findObjectMember((YoyooTypeDefine) obj, name);
				return objmember;
//				return findObjectMember((YoyooTypeDefine) obj, name);
				// else
				// {
				// throw new YoyooRTException("Variable "+name+" cannot
				// found!");
				// }
			}
		}
		return null;
	}

	private YoyooObject findObjectMember(YoyooTypeDefine typedef, String name) {
		YoyooObject fieldValue = typedef.getFieldValue(name);
		if(fieldValue==null) {
			if(typedef.getUnit().getStaticFieldsValue().containsKey(name))
				return typedef.getUnit().getStaticFieldsValue().get(name);
			else
				return null;
		} else {
			return fieldValue;
		}
		

	}

	public StopOperator getSkipTo() {
		return skipTo;
	}

	public void setSkipTo(StopOperator skipTo) {
		this.skipTo = skipTo;
	}

	public void cleanSkipTo() {
		this.skipTo = null;
	}

}
