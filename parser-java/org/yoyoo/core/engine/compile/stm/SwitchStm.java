package org.yoyoo.core.engine.compile.stm;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.atom.ValueAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.exp.ExprVisitor;
import org.yoyoo.core.engine.compile.opt.ConditionLinkOperator;
import org.yoyoo.core.engine.compile.opt.OperatorFactory;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.compile.type.PrimitiveType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooCaseBlockStm;
import org.yoyoo.core.engine.parser.YoyooCaseExpr;
import org.yoyoo.core.engine.parser.YoyooCaseStm;
import org.yoyoo.core.engine.parser.YoyooSwitchCase;
import org.yoyoo.core.engine.parser.YoyooSwitchDefault;
import org.yoyoo.core.engine.parser.YoyooSwitchKey;
import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;


public class SwitchStm extends AbstractStm {

	public SwitchStm(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private IAtom switchKey;
	
	private Expr switchKeyExpr;


	private List<CaseStm> caseStms;

	private boolean hasDefault;
	
	



	@Override
	public Object visit(YoyooCaseStm node, Object data) {
		CaseStm stm = new CaseStm(method, catchStm, node);
		node.childrenAccept(stm, node);
		if (caseStms == null)
			caseStms = new ArrayList<CaseStm>();
		if (hasDefault && stm.isDefaultCase())
			unit.addError(new CompileException.DuplicateDefaultCase(node, ycls
					.getCompilationUnit()));
		caseStms.add(stm);
		hasDefault = stm.isDefaultCase();
		return null;
	}

	@Override
	public Object visit(YoyooSwitchKey node, Object data) {
		ExprVisitor v = new ExprVisitor(unit, node, this);
		node.childrenAccept(v, node);
		switchKeyExpr = v.getExprList().get(0);
		
		return super.visit(node, data);
	}

	/**
	 * @return the caseStms
	 */
	public List<CaseStm> getCaseStms() {
		return caseStms;
	}

	/**
	 * @return the switchKey
	 */
	public IAtom getSwitchKey() {
		return switchKey;
	}
	

	public Expr getSwitchKeyExpr() {
		return switchKeyExpr;
	}

	protected Operator convert2Operator() throws CompileException {
		switchKey = switchKeyExpr.convert2Atom(node);
		OperatorList switchList = new OperatorList(node, ycls
				.getCompilationUnit(), ycls);
		switchList.mark();
		pushOperator(switchList);
		// ConditionLinkOperator entryOpt = null;
		// ConditionLinkOperator linkOpt1 = null;
		// ConditionLinkOperator linkOpt2 = null;
		IAtom condAtom = null;
		IAtom lastCondAtom = null;
		// Operator finishOpt = findNextLinkOperator(node);
		boolean firstCaseStm = true;
		for (CaseStm caseStm : caseStms) {
			caseStm.convert2Operator();
			if(caseStm.isDefaultCase()) {
				condAtom = new ValueAtom(new PrimitiveType.YoyooBoolean(unit, node), new YoyooBoolean(true), node,	unit);
			}
			
			if(((CaseStm)caseStm).getCaseKey()!=null) {
				for (IAtom atom : ((CaseStm)caseStm).getCaseKey()) {
					Operator opt = OperatorFactory.createOperator("==",
							new IAtom[] { switchKey, atom }, node, ycls
									.getCompilationUnit(), ycls);
					if (condAtom == null) {
						condAtom = opt;
						lastCondAtom = opt;
					} else {
						condAtom = OperatorFactory.createOperator("||",
								new IAtom[] { lastCondAtom, opt }, node, ycls
										.getCompilationUnit(), ycls);
						lastCondAtom = condAtom;
					}
	
				}
			}
			
			if(firstCaseStm) {
				firstCaseStm = false;
				this.terminatedByReturnOrThrowStm = ((CaseStm)caseStm).getStm().isTerminatedByReturnOrThrowStm();
			}
			else {
				this.terminatedByReturnOrThrowStm = terminatedByReturnOrThrowStm && ((CaseStm)caseStm).getStm().isTerminatedByReturnOrThrowStm();
			}
			
			Operator stmLink = ((CaseStm)caseStm).getStm().getOperator();
			switchList.addOperator(new ConditionLinkOperator(node, unit, ycls,
					stmLink, null, condAtom));

			// if(linkOpt2!=null)
			// linkOpt2.setFalseLink(linkOpt1);
			// linkOpt2 = linkOpt1;
			// if(entryOpt==null)
			// entryOpt = linkOpt1;
		}

		// linkOpt2.setFalseLink(finishOpt);
		// OperatorList list = new OperatorList(node,
		// unit.getCompilationUnit());
		// list.addOperator(entryOpt);
		// entryOpt.setBreakable(true);

		return popOperator();
	}

	public static class CaseStm extends AbstractStm {

		public CaseStm(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
			super(method, catchStm, node);
			// TODO Auto-generated constructor stub
		}

		private List<IAtom> caseKey;
		
		private List<Expr> caseKeyExpr; 


		private StmList stm;

		private boolean defaultCase;

		@Override
		public Object visit(YoyooCaseBlockStm node, Object data) {
			StmVisitor v = new StmVisitor(method, catchStm, node);
			node.childrenAccept(v, node);
			Stm statement = v.getStatement();
			if (stm == null)
				stm = new StmList(method, catchStm, node);
			stm.add(statement);
			return null;
		}

		@Override
		public Object visit(YoyooCaseExpr node, Object data) {
			ExprVisitor v = new ExprVisitor(unit, node, this);
			node.childrenAccept(v, node);
			Expr expr = null;
			expr = v.getExprList().get(0);
			if (caseKeyExpr == null)
				caseKeyExpr = new ArrayList<Expr>();
			caseKeyExpr.add(expr);
			return null;
		}
		
		@Override
		public Object visit(YoyooSwitchCase node, Object data) {
			node.childrenAccept(this, data);
			return null;
		}

		@Override
		public Object visit(YoyooSwitchDefault node, Object data) {
			defaultCase = true;
			return null;
		}

		/**
		 * @return the caseKey
		 */
		public List<IAtom> getCaseKey() {
			return caseKey;
		}

		/**
		 * @return the stm
		 */
		public StmList getStm() {
			return stm;
		}

		/**
		 * @return the defaultCase
		 */
		public boolean isDefaultCase() {
			return defaultCase;
		}

		public Operator convert2Operator() throws CompileException {
			if(caseKeyExpr!=null) {
				if(this.caseKey == null) {
					this.caseKey = new ArrayList<IAtom>(this.caseKeyExpr.size());
				}
				for(Expr expr :  this.caseKeyExpr) {
					this.caseKey.add(expr.convert2Atom(expr.getNode()));
				}
			}
			return null;
		}
		

		public List<Expr> getCaseKeyExpr() {
			return caseKeyExpr;
		}

	}

}
