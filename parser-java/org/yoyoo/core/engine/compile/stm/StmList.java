package org.yoyoo.core.engine.compile.stm;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.compile.opt.AtomOperator;
import org.yoyoo.core.engine.compile.opt.OperatorList;
import org.yoyoo.core.engine.parser.SimpleNode;

public class StmList extends AbstractStm {

	public StmList(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// TODO Auto-generated constructor stub
	}

	private List<Stm> list;

	public void add(Stm stm) {
		if (list == null)
			list = new ArrayList<Stm>();
		list.add(stm);
	}

	/**
	 * @return the list
	 */
	public List<Stm> getList() {
		return list;
	}

	public boolean isEmpty() {
		return list == null || list.isEmpty();
	}

	public Stm first() {
		return isEmpty() ? null : list.get(0);
	}

	public Stm last() {
		return isEmpty() ? null : list.get(list.size() - 1);
	}

	@Override
	protected Operator convert2Operator() throws CompileException {
		OperatorList opt = new OperatorList(node, unit, ycls);
		pushOperator(opt);
		for (Stm stm : list) {

				opt.addOperator(new AtomOperator(node, unit, ycls, stm
						.getOperator()));
		}
		return popOperator();
	}

}
