package org.yoyoo.core.engine.runtime;

import java.util.Collection;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.declaration.YoyooClass;
import org.yoyoo.core.engine.compile.opt.FuncCallOperator;
import org.yoyoo.core.engine.parser.SimpleNode;

@SuppressWarnings("serial")
public class YoyooRTException extends Exception {

	private SimpleNode node;

	private CompilationUnit unit;

	private static int count = 1000;
	
	private Collection<FuncCallOperator> functionCallStack;


	public YoyooRTException(String string, SimpleNode node, CompilationUnit unit, RuntimeContext ctx) {
		super(string + ((node == null || unit == null) ? "" : " ("
			+ node.first_token.beginLine + ", "
			+ node.first_token.beginColumn + ")")+ " "+unit.getSource().getFile().getPath());
		this.node = node;
		this.unit= unit;
		
		this.functionCallStack = ctx.getFunctionCallStack();
	}
	
	public YoyooRTException(ErrorMessage string, RuntimeContext ctx) {
		super(string.getMsg());
		this.functionCallStack = ctx.getFunctionCallStack();
	}

	public YoyooRTException(String[] replacements, ErrorMessage string, RuntimeContext ctx) {
		super(string.getMsg(replacements));
		this.functionCallStack = ctx.getFunctionCallStack();
	}

	public YoyooRTException(ErrorMessage string, SimpleNode node,
			CompilationUnit unit, RuntimeContext ctx) {
		super(string.getMsg()
				+ ((node == null || unit == null) ? "" : " ("
						+ node.first_token.beginLine + ", "
						+ node.first_token.beginColumn + ")"));
		this.unit = unit;
		this.node = node;
		this.functionCallStack = ctx.getFunctionCallStack();
	}

	public YoyooRTException(String[] replacements, ErrorMessage string,
			SimpleNode node, CompilationUnit unit, RuntimeContext ctx) {
		super(string.getMsg(replacements)
				+ ((node == null || unit == null) ? "" : " ("
						+ node.first_token.beginLine + ", "
						+ node.first_token.beginColumn + ")")+ " "+unit.getSource().getFile().getPath());
		this.unit = unit;
		this.node = node;
		this.functionCallStack = ctx.getFunctionCallStack();
	}

	private static final long serialVersionUID = -6568098327139377578L;

	private enum ErrorMessage {
		CannotInvokeMethod("Cannot invoke method <1>!"),
		CannotEval("The expression cannot be evaluated!"),
		VariableExist("Variable <1> already exists in runtime enviroment!"),
		VariableCannotFound("Variable <1> cannot be found in runtime enviroment!"),
		OutOfBound("<1> out of array bound (0 - <2>)!"),
		ObjectIsNull("<1> is null!"),
		VariableIsNull("Variable <1> is null!"),
		TypeMismatch("Type mismatch: <1> ocurred, while <2> expected!"),
		ObjectCannotbeCloned("The object cannot be cloned!"),
		NullPoint("The object is null!");
		
		public String msg;

		public int code;

		private ErrorMessage(String msg) {
			this.code = count++;
			this.msg = msg;
		}

		/**
		 * @return the msg
		 */
		public String getMsg(String[] msg) {
			String errorMsg = this.msg;
			if (msg != null && msg.length > 0)
				for (int i = 0; i < msg.length; i++) {
					errorMsg = errorMsg.replaceFirst("<\\d*>", msg[i]);
				}
			return errorMsg;
		}

		/**
		 * @return the msg
		 */
		public String getMsg() {
			String errorMsg = this.msg;
			return errorMsg;
		}
	}

	public final static class CannotEval extends YoyooRTException {
		public CannotEval(SimpleNode node, CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] {}, ErrorMessage.CannotEval, node, unit, ctx);
		}
	}

	public final static class CannotInvokeMethod extends YoyooRTException {
		public CannotInvokeMethod(String name, SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] { name }, ErrorMessage.CannotInvokeMethod, node,
					unit, ctx);
		}
	}

	public final static class VariableExist extends YoyooRTException {
		public VariableExist(String name, SimpleNode node, CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] { name }, ErrorMessage.VariableExist, node, unit, ctx);
		}
	}

	public final static class VariableCannotFound extends YoyooRTException {
		public VariableCannotFound(String name, SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] { name }, ErrorMessage.VariableCannotFound,
					node, unit, ctx);
		}
	}
	
	public final static class OutOfBound extends YoyooRTException {
		public OutOfBound(int b1, int b2, SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] { String.valueOf(b1), String.valueOf(b2) }, ErrorMessage.OutOfBound,
					node, unit, ctx);
		}
	}
	
	public final static class ObjectIsNull extends YoyooRTException {
		public ObjectIsNull(String name, SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] { name }, ErrorMessage.ObjectIsNull,
					node, unit, ctx);
		}
	}
	
	
	public final static class VariableIsNull extends YoyooRTException {
		public VariableIsNull(String v, SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {
			super(new String[] {v}, ErrorMessage.VariableIsNull,
					node, unit, ctx);
		}
	}
	
	public final static class TypeMismatch extends YoyooRTException {
		public TypeMismatch(YoyooClass t1, YoyooClass t2, SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {

			super(new String[] { t1.getName(), t2.getName() },
					ErrorMessage.TypeMismatch, node, unit, ctx);
		}
	}
	
	public final static class ObjectCannotbeCloned extends YoyooRTException {
		public ObjectCannotbeCloned(SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {

			super(new String[] {},
					ErrorMessage.TypeMismatch, node, unit, ctx);
		}
	}
	
	public final static class NullPoint extends YoyooRTException {
		public NullPoint(SimpleNode node,
				CompilationUnit unit, RuntimeContext ctx) {

			super(new String[] {},
					ErrorMessage.NullPoint, node, unit, ctx);
		}
	}
	
	public SimpleNode getNode() {
		return node;
	}

	public CompilationUnit getUnit() {
		return unit;
	}

	public void setNode(SimpleNode node) {
		this.node = node;
	}

	public void setUnit(CompilationUnit unit) {
		this.unit = unit;
	}

	
	public Collection<FuncCallOperator> getFunctionCallStack() {
		return functionCallStack;
	}

	public void setFunctionCallStack(Collection<FuncCallOperator> functionCallStack) {
		this.functionCallStack = functionCallStack;
	}
}
