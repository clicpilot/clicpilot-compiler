package org.yoyoo.core.engine.compile;

public enum CompileErrorMessage {

	DuplicateDefined(1000, "<1> is defined!"), Undefined(1001,
			"<1> is not defined!"), CannotEval(1002,
			"The expression cannot be evaluated!"), TypeMismatch(1003,
			"Type mismatch: <1> ocurred, while <2> expected!"), WrongOperatorArguments(
			1004, "Operator cannot accept that number of arguments!"), DuplicateDefaultCase(
			1005, "Default label has been defined in the switch statement!"), LabelNotDefined(
			1006, "Label <1> is not defined!"), DuplicateLabel(1007,
			"Label <1> is defined!"), BreakNotInLoop(1008,
			"Break is ot in loop statement!"), ContinueNotInLoop(1009,
			"Continue is ot in loop statement!"), InvaildAccessMethod(1010,
			"Invaild access to a method!"), CannotInvokeMethod(1011,
			"Cannot invoke method <1>!"), CannotBreak(1012,
			"Cannot execute the break or continue statement!");
	private String msg;

	private int code;

	private CompileErrorMessage(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
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

	public static void main(String[] args) {
		System.out.println(DuplicateDefined
				.getMsg(new String[] { "abc", "def" }));
	}
}
