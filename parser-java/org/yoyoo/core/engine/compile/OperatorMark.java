package org.yoyoo.core.engine.compile;

public class OperatorMark {
	private MARK opt;
	
	public OperatorMark(MARK opt) {
		this.opt = opt;
	}

	public OperatorMark(String opt) {
		super();
		if (opt.equals(MARK.ASSIGN.str))
			this.opt = MARK.ASSIGN;
		else if (opt.equals(MARK.GT.str))
			this.opt = MARK.GT;
		else if (opt.equals(MARK.LT.str))
			this.opt = MARK.LT;
		else if (opt.equals(MARK.BANG.str))
			this.opt = MARK.BANG;
		else if (opt.equals(MARK.HOOK.str))
			this.opt = MARK.HOOK;
		else if (opt.equals(MARK.COLON.str))
			this.opt = MARK.COLON;
		else if (opt.equals(MARK.EQ.str))
			this.opt = MARK.EQ;
		else if (opt.equals(MARK.LE.str))
			this.opt = MARK.LE;
		else if (opt.equals(MARK.GE.str))
			this.opt = MARK.GE;
		else if (opt.equals(MARK.NE.str))
			this.opt = MARK.NE;
		else if (opt.equals(MARK.SC_OR.str))
			this.opt = MARK.SC_OR;
		else if (opt.equals(MARK.SC_AND.str))
			this.opt = MARK.SC_AND;
		else if (opt.equals(MARK.INCR.str))
			this.opt = MARK.INCR;
		else if (opt.equals(MARK.DECR.str))
			this.opt = MARK.DECR;
		else if (opt.equals(MARK.PLUS.str))
			this.opt = MARK.PLUS;
		else if (opt.equals(MARK.MINUS.str))
			this.opt = MARK.MINUS;
		else if (opt.equals(MARK.STAR.str))
			this.opt = MARK.STAR;
		else if (opt.equals(MARK.SLASH.str))
			this.opt = MARK.SLASH;
		else if (opt.equals(MARK.REM.str))
			this.opt = MARK.REM;
		else if (opt.equals(MARK.PLUSASSIGN.str))
			this.opt = MARK.PLUSASSIGN;
		else if (opt.equals(MARK.MINUSASSIGN.str))
			this.opt = MARK.MINUSASSIGN;
		else if (opt.equals(MARK.STARASSIGN.str))
			this.opt = MARK.STARASSIGN;
		else if (opt.equals(MARK.SLASHASSIGN.str))
			this.opt = MARK.SLASHASSIGN;
		else if (opt.equals(MARK.REMASSIGN.str))
			this.opt = MARK.REMASSIGN;
		else if (opt.equals(MARK.INSTANCEOF.str))
			this.opt = MARK.INSTANCEOF;

	}

	public static enum MARK {
		ASSIGN("="), GT(">"), LT("<"), BANG("!"), HOOK("?"), COLON(":"), EQ(
				"=="), LE("<="), GE(">="), NE("!="), SC_OR("||"), SC_AND("&&"), INCR(
				"++"), DECR("--"), PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), REM(
				"%"), PLUSASSIGN("+="), MINUSASSIGN("-="), STARASSIGN("*="), SLASHASSIGN(
				"/="), REMASSIGN("%="), INSTANCEOF("instanceof");
		private String str;

		private MARK(String str) {
			this.str = str;
		}

		/**
		 * @return the str
		 */
		public String getStr() {
			return str;
		}
	}

	/**
	 * @return the opt
	 */
	public MARK getOpt() {
		return opt;
	}

}
