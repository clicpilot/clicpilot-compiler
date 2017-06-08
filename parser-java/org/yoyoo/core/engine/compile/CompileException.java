package org.yoyoo.core.engine.compile;

import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;

@SuppressWarnings("serial")
public class CompileException extends Exception {

	private SimpleNode node;

	private CompilationUnit unit;

	private static int count = 1000;
	
	private int code;

	public CompileException(ErrorMessage string) {
		super(string.getMsg());
		
		this.code = string.code;
	}

	public CompileException(String[] replacements, ErrorMessage string) {
		super(string.getMsg(replacements));
		this.code = string.code;
	}

	public CompileException(ErrorMessage string, SimpleNode node,
			CompilationUnit unit) {
		super(string.getMsg()
				+ ((node == null || unit == null) ? "" : " "
						+ " ("
						+ node.first_token.beginLine + ", "
						+ node.first_token.beginColumn + ") "
						//+ unit.getSource().getFile().getName()+" "
						+ unit.getSource().getFile().getPath()));
		this.unit = unit;
		this.node = node;
		this.code = string.code;
	}

	public CompileException(String[] replacements, ErrorMessage string,
			SimpleNode node, CompilationUnit unit) {
		super(string.getMsg(replacements)
				+ ((node == null || unit == null) ? "" : " "
					+ " ("
					+ node.first_token.beginLine + ", "
					+ node.first_token.beginColumn + ") "
					//+ unit.getSource().getFile().getName()+" "
					+ unit.getSource().getFile().getPath()));
		this.unit = unit;
		this.node = node;
		this.code = string.code;
	}

	private static final long serialVersionUID = -6568098327139377578L;

	public enum ErrorMessage {
		DuplicateDefined("<1> is defined!"), 
		Undefined("<1> is not defined!"), 
		InvalidExpr("The expression is invalid!"), 
		TypeMismatch("Type mismatch: <1> ocurred, while <2> expected!"), 
		FieldNotFound("Field <1> cannot be found in <2>!"), 
		CannotCreateInstance("Cannot create instance of <1>, interface or abstract class!"), 
		WrongOperatorArguments("Operator cannot accept that number of arguments!"), 
		DuplicateDefaultCase("Default label has been defined in the switch statement!"),
		LabelNotDefined("Label <1> is not defined!"),
		DuplicateLabel("Label <1> is defined!"),
		BreakNotInLoop("Break is not in loop statement!"),
		ContinueNotInLoop("Continue is not in loop statement!"),
		InvaildAccessMethod("Invaild access to method <1>!"),
		CannotCallMethodInStatic("Cannot call method <1> in static constructor."),
		CannotCallNonStaticMethod("Cannot call method <1>, which is not a static method."),
		
		InvalidConstructor("The Constructor <1> is not valid!"),
		SuperConstructorUndefined("Super constructor <1>() is undefined. Must explicitly invoke another constructor"),
		ConstructorUndefined("The Constructor is not defined in <1>!"),
		FieldInStatic("Cannot access field <1> in the static method!"),
		StaticConstructorDefined("The static constructor <1> has been declared!"),
		StaticConstructorCannotHaveParam("The static constructor <1> cannot have parameter!"),
		MethodUndefine("Method <1> is not defined in <2>!"),
		MethodNotImplemented("Method <1> is not implemeted!"),
		MethodIsNotPublic("Method <1> is not public!"),
		FieldIsNotPublic("Field <1> is not public!"),
		ParentClassUndefine("Class <1> has no parent class!"),
		CannotAccessMethod("Cannot access method <1>!"),
		CannotAccessField("Cannot access field <1>!"),
		UnsupportedOperator("Unsupported operator mark <1>!"),
		UnhandledException("Unhandled exception <1>!"),
		FileCannotFound("File <1> cannot be found!"),
		FileCannotParsed("File <1> cannot be parsed!"),
		TypeAmbiguous("The type <1> is ambiguous!"),
		FieldAmbiguous("Field <1> is ambiguous!"),
		NotArray("The type of the expression must be an array type but it resolved to <1>!"),
		WrongArrayInitializer("Variable must provide either dimension expressions or an array initializer"),
		MethodNoReturn("The method must return a value of type <1>"),
		UnreachableCode("Unreachable Code!"),
		SpecialExprNotDefined("The expression prefix <1> cannot be found!"),
		SpecialExprNotCompiled("The <1>  expression has compile error(s): \n <2>");
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

	public final static class DuplicateDefined extends CompileException {
		public DuplicateDefined(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.DuplicateDefined, node,
					unit);
		}
	}

	public final static class Undefined extends CompileException {
		public Undefined(String name, SimpleNode node, CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.Undefined, node, unit);
		}
	}

	public final static class InvalidExpr extends CompileException {
		public InvalidExpr(SimpleNode node, CompilationUnit unit) {
			super(new String[] {}, ErrorMessage.InvalidExpr, node, unit);
		}
	}

	public final static class TypeMismatch extends CompileException {
		public TypeMismatch(IType t1, IType t2, SimpleNode node,
				CompilationUnit unit) {

			super(new String[] { t1.getFullDisplayName(), t2.getFullDisplayName() },
					ErrorMessage.TypeMismatch, node, unit);
		}
	}

	public final static class WrongOperatorArguments extends CompileException {
		public WrongOperatorArguments(SimpleNode node, CompilationUnit unit) {
			super(new String[] {}, ErrorMessage.WrongOperatorArguments, node,
					unit);
		}
	}

	public final static class DuplicateDefaultCase extends CompileException {
		public DuplicateDefaultCase(SimpleNode node, CompilationUnit unit) {
			super(new String[] {}, ErrorMessage.DuplicateDefaultCase, node,
					unit);
		}
	}

	public final static class LabelNotDefined extends CompileException {
		public LabelNotDefined(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.LabelNotDefined, node,
					unit);
		}
	}

	public final static class DuplicateLabel extends CompileException {
		public DuplicateLabel(String name, SimpleNode node, CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.DuplicateLabel, node,
					unit);
		}
	}

	public final static class BreakNotInLoopOrSwitch extends CompileException {
		public BreakNotInLoopOrSwitch(SimpleNode node, CompilationUnit unit) {
			super(new String[] {}, ErrorMessage.BreakNotInLoop, node, unit);
		}
	}

	public final static class ContinueNotInLoop extends CompileException {
		public ContinueNotInLoop(SimpleNode node, CompilationUnit unit) {
			super(new String[] {}, ErrorMessage.ContinueNotInLoop, node, unit);
		}
	}

	public final static class InvaildAccessMethod extends CompileException {
		public InvaildAccessMethod(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.InvaildAccessMethod,
					node, unit);
		}
	}

	public final static class CannotCallMethodInStatic extends CompileException {
		public CannotCallMethodInStatic(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.CannotCallMethodInStatic,
					node, unit);
		}
	}
	
	public final static class CannotCallNonStaticMethod extends CompileException {
		public CannotCallNonStaticMethod(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.CannotCallNonStaticMethod,
					node, unit);
		}
	}
	

	
	public final static class MethodUndefine extends CompileException {
		public MethodUndefine(String name, String clsName, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name, clsName }, ErrorMessage.MethodUndefine,
					node, unit);
		}
	}
	
	public final static class MethodNotImplemented extends CompileException {
		public MethodNotImplemented(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name, }, ErrorMessage.MethodNotImplemented,
					node, unit);
		}
	}
	
	public final static class MethodIsNotPublic extends CompileException {
		public MethodIsNotPublic(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name, }, ErrorMessage.MethodIsNotPublic,
					node, unit);
		}
	}
	
	public final static class FieldIsNotPublic extends CompileException {
		public FieldIsNotPublic(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name, }, ErrorMessage.FieldIsNotPublic,
					node, unit);
		}
	}
	
	public final static class CannotAccessMethod extends CompileException {
		public CannotAccessMethod(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name}, ErrorMessage.CannotAccessMethod,
					node, unit);
		}
	}
	
	public final static class ParentClassUndefine extends CompileException {
		public ParentClassUndefine(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name}, ErrorMessage.ParentClassUndefine,
					node, unit);
		}
	}
	
	public final static class CannotAccessField extends CompileException {
		public CannotAccessField(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name}, ErrorMessage.CannotAccessField,
					node, unit);
		}
	}
	
	
	public final static class InvalidConstructor extends CompileException {
		public InvalidConstructor(String clsName, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { clsName }, ErrorMessage.InvalidConstructor,
					node, unit);
		}
	}
	
	public final static class ConstructorUndefined extends CompileException {
		public ConstructorUndefined(String clsName, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { clsName }, ErrorMessage.ConstructorUndefined,
					node, unit);
		}
	}
	
	public final static class SuperConstructorUndefined extends CompileException {
		public SuperConstructorUndefined(String clsName, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { clsName }, ErrorMessage.SuperConstructorUndefined,
					node, unit);
		}
	}

	public final static class FieldInStatic extends CompileException {
		public FieldInStatic(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.FieldInStatic,
					node, unit);
		}
	}
	
	public final static class StaticConstructorDefined extends CompileException {
		public StaticConstructorDefined(String clsName, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { clsName }, ErrorMessage.StaticConstructorDefined,
					node, unit);
		}
	}
	
	public final static class StaticConstructorCannotHaveParam extends CompileException {
		public StaticConstructorCannotHaveParam(String clsName, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { clsName }, ErrorMessage.StaticConstructorCannotHaveParam,
					node, unit);
		}
	}
	
	public final static class UnsupportedOperator extends CompileException {
		public UnsupportedOperator(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.UnsupportedOperator,
					node, unit);
		}
	}

	public final static class UnhandledException extends CompileException {
		public UnhandledException(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.UnhandledException,
					node, unit);
		}
	}
	
	public final static class FileCannotFound extends CompileException {
		public FileCannotFound(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.FileCannotFound, node,
					unit);
		}
	}

	public final static class FileCannotParsed extends CompileException {
		public FileCannotParsed(String name, SimpleNode node,
				CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.FileCannotParsed, node,
					unit);
		}
	}

	public final static class TypeAmbiguous extends CompileException {
		public TypeAmbiguous(String name, SimpleNode node, CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.TypeAmbiguous, node, unit);
		}
	}
	
	public final static class FieldAmbiguous extends CompileException {
		public FieldAmbiguous(String name, SimpleNode node, CompilationUnit unit) {
			super(new String[] { name }, ErrorMessage.FieldAmbiguous, node, unit);
		}
	}

	public final static class FieldNotFound extends CompileException {
		public FieldNotFound(String fiedname, String clsname, SimpleNode node, CompilationUnit unit) {
			super(new String[] { fiedname, clsname }, ErrorMessage.FieldNotFound, node, unit);
		}
	}
	public final static class CannotCreateInstance extends CompileException {
		public CannotCreateInstance(IType type, SimpleNode node, CompilationUnit unit) {
			super(new String[] {type.getName() }, ErrorMessage.CannotCreateInstance, node, unit);
		}
	}
	public final static class NotArray extends CompileException {
		public NotArray(IType type, SimpleNode node, CompilationUnit unit) {
			super(new String[] {type.getName() }, ErrorMessage.NotArray, node, unit);
		}
	}
	public final static class WrongArrayInitializer extends CompileException {
		public WrongArrayInitializer(SimpleNode node, CompilationUnit unit) {
			super(new String[] { }, ErrorMessage.WrongArrayInitializer, node, unit);
		}
	}
	public final static class UnreachableCode extends CompileException {
		public UnreachableCode(SimpleNode node, CompilationUnit unit) {
			super(new String[] { }, ErrorMessage.UnreachableCode, node, unit);
		}
	}
	
	public final static class MethodNoReturn extends CompileException {
		public MethodNoReturn(IType type, SimpleNode node, CompilationUnit unit) {
			super(new String[] {type.getName() }, ErrorMessage.MethodNoReturn, node, unit);
		}
	}
	
	public final static class SpecialExprNotDefined extends CompileException {
		public SpecialExprNotDefined(String name, SimpleNode node, CompilationUnit unit) {
			super(new String[] {name}, ErrorMessage.SpecialExprNotDefined, node, unit);
		}
	}
	
	public final static class SpecialExprNotCompiled extends CompileException {
		public SpecialExprNotCompiled(String name, String msg, SimpleNode node, CompilationUnit unit) {
			super(new String[] {name, msg}, ErrorMessage.SpecialExprNotCompiled, node, unit);
		}
	}
	
	public SimpleNode getNode() {
		return node;
	}

	public CompilationUnit getUnit() {
		return unit;
	}

	public int getCode() {
		return code;
	}

//	public void setNode(SimpleNode node) {
//		this.node = node;
//	}
//
//	public void setUnit(CompilationUnit unit) {
//		this.unit = unit;
//	}

}
