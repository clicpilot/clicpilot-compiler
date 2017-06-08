package org.yoyoo.core.engine.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ReferenceAtom;
import org.yoyoo.core.engine.compile.atom.ValueAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.opt.AbstractOperator;
import org.yoyoo.core.engine.compile.opt.AllocationOperator;
import org.yoyoo.core.engine.compile.opt.FuncArgumentsOperator;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.compile.opt.IStop;
import org.yoyoo.core.engine.compile.opt.StopOperator;
import org.yoyoo.core.engine.compile.opt.ThrowOperator;
import org.yoyoo.core.engine.compile.opt.TryOperator;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.ParseException;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooException;


public class RuntimeContext {

	public final static String THIS = "this";

	public final static String SUPER = "super";

	private Stack<StackFrame> stack;

	private Stack<Operator> operatorsInRunning;
	
	private Stack<FuncCallOperator> functionCallStack;

//	private Map<YoyooObject, YoyooClass> objectMap;
	
	private Map<String, YoyooObject> staticInstanceMap;

	private StackFrame currentFrame;

	private YoyooEnvironment env;

	private static RuntimeContext ctx;
	
	private IRuntimeValueAtom exceptionAtom;
	
	private boolean resturnByStm = false;

	public static RuntimeContext getCurrentContext() {
		if (ctx == null) {
			ctx = new RuntimeContext();
		}
		return ctx;
	}
	
	public static RuntimeContext getNewContext() {
		
		return new RuntimeContext();
	}

	private RuntimeContext() {
		super();
		this.env = YoyooEnvironment.getDefault();
		try {
			pushStackFrame(false);
		} catch (YoyooRTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void finalize() throws Throwable {
		clear();
		super.finalize();
	}
	
	public void clear() {
		if(stack!=null)
			stack.clear();
		if(operatorsInRunning!=null)
			operatorsInRunning.clear();
		if(staticInstanceMap!=null)
			staticInstanceMap.clear();
		if(functionCallStack!=null) {
			functionCallStack.clear();
			
		}
		cleanException();
		currentFrame = null;
		try {
			pushStackFrame(false);
		} catch (YoyooRTException e) {
			e.printStackTrace();
		}
	}

	// public void pushOperator(Operator opt)
	// {
	// currentFrame.pushOperator(opt);
	// }
	//	
	public void registerVariable(String name, IRuntimeValueAtom atom, SimpleNode node,
			CompilationUnit unit)
			throws YoyooRTException {
		currentFrame.registerVariable(name, atom, node, unit);
	}

	public void registerThis(IRuntimeValueAtom atom, SimpleNode node,
			CompilationUnit unit) throws YoyooRTException {
		currentFrame.registerVariable(THIS, atom, node, unit);
	}

	public void updateVariable(IAtom atom, IRuntimeValueAtom runtimeAtom,
			SimpleNode node, CompilationUnit unit) throws YoyooRTException {
		currentFrame.updateVariable(atom, runtimeAtom, node, unit);
	}

	public IRuntimeValueAtom lookupThisAtom(SimpleNode node,
			CompilationUnit unit) throws YoyooRTException {
		return currentFrame.lookupVariable(THIS, node, unit);
	}

	public IRuntimeValueAtom lookupAtom(String name, SimpleNode node,
			CompilationUnit unit) throws YoyooRTException {
		IRuntimeValueAtom atom = currentFrame.lookupVariable(name, node, unit);
		return atom;
	}

	public void pushStackFrame(boolean copyVariables) throws YoyooRTException {

		if (stack == null)
			stack = new Stack<StackFrame>();
		StackFrame frame = null;
		if (copyVariables) {
			frame = new StackFrame(this, currentFrame);
		} else {
			frame = new StackFrame(this);
		}
		StopOperator skipTo = null;
		if (currentFrame != null) {
			stack.push(currentFrame);

			skipTo = currentFrame.getSkipTo();
			currentFrame.cleanSkipTo();
		}
		currentFrame = frame;
		if (skipTo != null)
			currentFrame.setSkipTo(skipTo);
	}

	public void popStackFrame() {
		StopOperator skipTo = currentFrame.getSkipTo();
		currentFrame = stack.pop();
		if (skipTo != null)
			currentFrame.setSkipTo(skipTo);
	}
	public IRuntimeValueAtom run(String typeunitPkg, String typeunitName, String functionName) {
		return this.run(typeunitPkg, typeunitName, functionName, new String[]{});
	}
	public IRuntimeValueAtom run(String typeunitPkg, String typeunitName, String functionName, String... args) {
		try {
			env.includePackage(typeunitPkg);
		} catch (ParseException e2) {			
			e2.printStackTrace();
			
		}
		YoyooTypeDefineClass unit = null;
		try {
			unit = env.getDeclaration(typeunitPkg, typeunitName);
			if (unit != null) {
				env.solveUnknownReferenceType(unit.getCompilationUnit());
				Collection<YoyooTypeDefineClass> decls = env.getDeclarations();
				for (YoyooTypeDefineClass cls : decls) {
					if(!cls.getCompilationUnit().containsUnknownReferenceType()) {
						//System.out.println("Class Check:"+cls.getFullName());
						cls.classCheck();
					}
				}
				for (YoyooTypeDefineClass cls : decls) {
					if(!cls.getCompilationUnit().containsUnknownReferenceType()) {
						//System.out.println("Type Check:"+cls.getFullName());
						cls.typeCheck();
					}
				}

				List<CompileException> errors = env.getErrors();
//				if(errors!=null)
//				{
//					for(CompileException error : errors)
//					{
//						env.solveUnknownReferenceType(error.getUnit());
//					}
//				}
//				errors = env.getErrors();
				boolean hasError = false;
				if (errors != null && !errors.isEmpty()) {
					for (CompileException e : errors) {
//						if(e.getUnit() == unit.getCompilationUnit() && e instanceof CompileException.Undefined)
//						{
//							System.err.println("Compile Error: " + e.getMessage());
//							e.printStackTrace();
//							hasError = true;
//						} else if(!(e instanceof CompileException.Undefined))
//						{
//							System.err.println("Compile Error: " + e.getMessage());
//							e.printStackTrace();
//							hasError = true;
//						}
						
						System.err.println("Compile Error: " + e.getMessage());
						//e.printStackTrace();
						hasError = true;
					}					
				} 
				if (!hasError && unit != null) {
					ReferenceNameType type = ReferenceNameType.createThisRefType(unit, unit.getNode());
					AllocationOperator opt = new AllocationOperator(type, null,
							null, null, null, null,null);
					YoyooTypeDefineClass ycls = (YoyooTypeDefineClass) type
							.map2YoyooClass(env);

					opt.setYcls(ycls);
					opt
							.installArguments(new IAtom[] { new FuncArgumentsOperator(
									null, null, null) });
					opt.typeCheck();
					IRuntimeValueAtom atom = this.evaluateOperator(opt);
					registerThis(atom, unit.getNode(), unit.getCompilationUnit());
					FuncCallOperator fucntion = new FuncCallOperator(null,
							null, null, new ReferenceAtom(null, functionName, 
									null, null));
					if(args==null || args.length==0) {
						fucntion.installArguments(new IAtom[] { new FuncArgumentsOperator(
									null, null, null) });
					} else {
						IAtom[] argAtoms = new IAtom[args.length];
	
						int i=0;
						for(String arg : args) {
							String val = arg;
							argAtoms[i++] = new ValueAtom(new PrimitiveType.YoyooString(null, null), new YoyooString(val), null, null);
						}
						 FuncArgumentsOperator funcArgumentsOperator = new FuncArgumentsOperator(null, null, null);
						 funcArgumentsOperator.installArguments(argAtoms);
						fucntion.installArguments(new IAtom[] {funcArgumentsOperator});
					}
					fucntion.setYcls(ycls);
					fucntion.typeCheck();
					return this.evaluateOperator(fucntion);
				}
			} else {
				System.err.println("Error: " + typeunitPkg + "." + typeunitName
						+ " does not exist!");
				
			}
		} catch (CompileException e1) {

			System.err.println("Error: " + e1.getMessage());
			e1.printStackTrace();
		} catch (YoyooRTException e1) {

			System.err.println("Error: " + e1.getMessage());
			Collection<FuncCallOperator> callStack = e1.getFunctionCallStack();
			if(callStack!=null) {
				System.err.print("\t");
				FuncCallOperator[] fopts = callStack.toArray(new FuncCallOperator[0]);
				if(fopts.length>0) {
					System.err.print(fopts[fopts.length-1].getMethod().getYmethod().getUnit().getFullName() +" "+ fopts[fopts.length-1].getMethod().getYmethod().getFullName());
					System.err.print(" ("+(e1.getNode()).first_token.beginLine + ", "+ (e1.getNode()).first_token.beginColumn +") ");
					System.err.print(" "+e1.getUnit().getSource().getFile().getPath());
					System.err.println();
					for(int i=fopts.length-2;i>=0;i--) {
						System.err.print("\t");
						System.err.print(fopts[i].getMethod().getYmethod().getUnit().getFullName() +" "+ fopts[i].getMethod().getYmethod().getFullName());
						if(fopts[i+1].getNode()!=null)
							System.err.print(" ("+((SimpleNode)fopts[i+1].getNode()).first_token.beginLine + ", "+ ((SimpleNode)fopts[i+1].getNode()).first_token.beginColumn +") ");
						if(fopts[i+1].getCompilationUnit()!=null)
							System.err.print(" "+fopts[i+1].getCompilationUnit().getSource().getFile().getPath());
						System.err.println();
					}
				}
				
				
			}
			//System.exit(1);
			//e1.printStackTrace();
			
		}
		return null;
	}
	
	public void printCallStack() {
		Collection<FuncCallOperator> callStack = this.getFunctionCallStack();
		if(callStack!=null) {
			System.err.print("\t");
			FuncCallOperator[] fopts = callStack.toArray(new FuncCallOperator[0]);
			System.err.print(fopts[fopts.length-1].getMethod().getYmethod().getUnit().getFullName() +" "+ fopts[fopts.length-1].getMethod().getYmethod().getFullName());

			System.err.println();
			for(int i=fopts.length-2;i>=0;i--) {
				System.err.print("\t");
				System.err.print(fopts[i].getMethod().getYmethod().getUnit().getFullName() +" "+ fopts[i].getMethod().getYmethod().getFullName());
				if(fopts[i+1].getNode()!=null)
					System.err.print(" ("+((SimpleNode)fopts[i+1].getNode()).first_token.beginLine + ", "+ ((SimpleNode)fopts[i+1].getNode()).first_token.beginColumn +") ");
				if(fopts[i+1].getCompilationUnit()!=null)
					System.err.print(" "+fopts[i+1].getCompilationUnit().getSource().getFile().getPath());
				System.err.println();
			}
			
			
			
		}
	}

	public YoyooEnvironment getEnv() {
		return env;
	}

	public StopOperator getSkipTo() {
		return currentFrame.getSkipTo();
	}

	public void setSkipTo(StopOperator skipTo) {
		currentFrame.setSkipTo(skipTo);
	}

	public void cleanSkipTo() {
		currentFrame.cleanSkipTo();
	}

	public IRuntimeValueAtom evaluateOperator(Operator opt) throws YoyooRTException {
		IRuntimeValueAtom val = null;
		if (this.operatorsInRunning == null)
			this.operatorsInRunning = new Stack<Operator>();
		if (opt != null) {
			this.operatorsInRunning.push(opt);

			
			val = opt.evaluate(this);
			
			
			Operator opt2 = this.operatorsInRunning.pop();
			if(opt2!=opt) {
				System.err.println("fatal error!");
				System.err.println(((AbstractOperator)opt).toSourceLocationString());
				System.err.println(((AbstractOperator)opt2).toSourceLocationString());
			}
			
		}
		return val;
	}

	public void stopLastLoopOperator(SimpleNode node, CompilationUnit unit,
			String label, boolean brk, Operator tar) throws YoyooRTException {
		Operator breakable = null;
		if (operatorsInRunning != null && !operatorsInRunning.isEmpty()) {
			for (Iterator<Operator> iterator = operatorsInRunning.iterator(); iterator
					.hasNext();) {
				Operator opt = iterator.next();
				if(opt == tar) {
					if(iterator.hasNext()) {
						breakable = iterator.next();
					}
				} 
//				if (opt instanceof IStop && ((IStop) opt).isMark()) {
//					if (label != null && opt.getLabel().equals(label)) {
//						breakable = opt;
//						break;
//					} else if (label == null) {
//						breakable = opt;
//					}
//				}

			}
			if (breakable == null) {
				System.err.println("cannot break");
				// throw new
				// YoyooRTException(CompileErrorMessage.CannotBreak.getMsg(),
				// node, unit);
			} else {
				int i = operatorsInRunning.size() - 1;
				Operator opt = operatorsInRunning.elementAt(i - 1);
				while (opt != breakable) {
					if (opt instanceof IStop)
						((IStop) opt).setStop(true, this);
					i--;
					opt = operatorsInRunning.elementAt(i-1);
				}
				if (brk)
					((IStop) opt).setStop(true, this);
				else
					((IStop) opt).setContinue(true);
			}
		}

	}

//	public void putObject(YoyooObject obj, YoyooClass ycls) {
//		if (this.objectMap == null)
//			this.objectMap = new HashMap<YoyooObject, YoyooClass>();
//		if (obj instanceof IPrimitiveObject)
//			this.objectMap.put(obj, ((IPrimitiveObject) obj)
//					.getYoyooClass());
//		else
//			this.objectMap.put(obj, ycls);
//	}
//
//	public YoyooClass getObjectClass(YoyooObject obj) {
//		return this.objectMap.get(obj);
//	}

	public void putStaticInstance(String yclsName, YoyooObject obj) {
		if (this.staticInstanceMap == null)
			this.staticInstanceMap = new HashMap<String, YoyooObject>();
		this.staticInstanceMap.put(yclsName, obj);
	}

	public YoyooObject getStaticInstance(String yclsName) {
		if(staticInstanceMap!=null)
			return staticInstanceMap.get(yclsName);
		else
			return null;
	}

	
	public void functionInvoked(FuncCallOperator funcCaller) throws YoyooRTException
	{
		if(functionCallStack==null)
		{
			functionCallStack = new Stack<FuncCallOperator>();
		}
		functionCallStack.push(funcCaller);
		
		//System.out.println("-->"+funcCaller.getMethod().getYmethod().getFullName()+":"+funcCaller);
		if(operatorsInRunning.lastElement() != funcCaller) {
			//ctx.evaluateOperator(funcCaller);
		}
			//operatorsInRunning.push(funcCaller);
	}
	
	public void functionReturned(FuncCallOperator opt)
	{
		if(resturnByStm) {
			resturnByStm = false;
		}
		else if(!functionCallStack.isEmpty() && functionCallStack.lastElement() == opt) {
			FuncCallOperator opt1 = functionCallStack.pop();
			opt1.clearReturnValueAtom();
			//System.out.println("<--1"+opt1.getMethod().getYmethod().getFullName()+":"+opt1 + " " +opt1.hasReturnValueAtom());
//			cancelOperators(opt);
			//operatorsInRunning.pop();
			//cancelOperators(funcCaller);
		} else {
			
			//System.out.println("---1"+opt.getMethod().getYmethod().getFullName()+":"+opt);
		}
	}
	
	public Collection<FuncCallOperator> getFunctionCallStack() {
		return Collections.unmodifiableCollection(functionCallStack) ;
	}

	public void functionReturned(IRuntimeValueAtom atom)
	{

		if(!functionCallStack.isEmpty()) {
			FuncCallOperator funcCaller = functionCallStack.lastElement();//functionCallStack.pop();
		
			//System.out.println("<--2"+funcCaller.getMethod().getYmethod().getFullName()+":"+funcCaller+" "+atom+" "+funcCaller.hasReturnValueAtom());
		
			((FuncCallOperator)funcCaller).setReturnValueAtom(atom);
			//cancelOperators(opt);
		}
		
	}
	
	public void allocationReturned(AllocationOperator opt) {
		if(!functionCallStack.isEmpty() && functionCallStack.lastElement() == opt) {
			functionCallStack.pop();
			//System.out.println("<--3"+opt.getMethod().getYmethod().getName()+":"+opt);
			//cancelOperators(opt);
		}

	}
	
	public void exceptionThrown(ThrowOperator throwOpt, IRuntimeValueAtom atom) throws YoyooRTException
	{
		this.exceptionAtom = atom;
		boolean exceptionCaught = false;
		if (operatorsInRunning != null && !operatorsInRunning.isEmpty()) {
			int size = operatorsInRunning.size();
			optLoop: for(int i=size-1;i>=0;i--) {
				Operator opt = operatorsInRunning.elementAt(i);
				if(opt instanceof TryOperator) {
					if(((TryOperator)opt).exceptionCaught(((YoyooException) atom.getValue()), env)) {
						exceptionCaught = true;						
					}			
				}
				if(opt instanceof IStop) {
					((IStop)opt).setStop(true, this);
				} 
				if(exceptionCaught) {
					break optLoop;
				}
			}
			
		}
		
	}
	
	public IRuntimeValueAtom getCurrentException() {
		return exceptionAtom;
	}
	
	public void cleanException() {
		this.exceptionAtom = null;
	}
	
//	private void cancelOperators(Operator atOperator) {
////		int i = operatorsInRunning.size() - 1;
////
////		Operator opt = operatorsInRunning.elementAt(i - 1);
////		while (opt != atOperator) {
////			i--;
////			opt = operatorsInRunning.elementAt(i - 1);
////		}
////		opt = operatorsInRunning.get(i+1);
////		if(opt instanceof AbstractStopOperator)	{
////			((AbstractStopOperator)opt).setStop(true, this);
////		}
//		
//		while(atOperator!=operatorsInRunning.pop());
//		operatorsInRunning.push(atOperator);

//	}
	
	public boolean isCurrentOpt(Operator opt) {
		if(!this.operatorsInRunning.isEmpty())
			return this.operatorsInRunning.get(this.operatorsInRunning.size()-1) == opt;
		else
			return false;
	} 
	

}
