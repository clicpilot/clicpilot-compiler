package org.yoyoo.core.engine.compile.stm;

import org.yoyoo.core.engine.compile.atom.Operator;
import org.yoyoo.core.engine.compile.declaration.YoyooBaseMethod;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.YoyooBreakLabel;
import org.yoyoo.core.engine.parser.YoyooContinueLabel;

public abstract class StopStm extends AbstractStm {
	protected Operator stopAt;

	// public abstract void refillOperator();
	public StopStm(YoyooBaseMethod method,  CatchStatement catchStm, SimpleNode node) {
		super(method, catchStm, node);
		// method.getHelper().addStopStm(this);

		// this.loopWithoutBlock = false;
		// if(this.prevSlide() instanceof LoopStm)
		// {
		// loopStm = (LoopStm)this.prevSlide();
		// loopWithoutBlock = true;
		// }
		// else
		// {
		//			
		// Block p = this.parent;
		//			
		// while(p!=null)
		// {
		// Stm stm = p.prevSlide();
		// if(stm != null)
		// {
		// if(stm instanceof LoopStm )
		// {
		// loopStm = (LoopStm)stm;
		// break;
		// }
		// else if ((stm instanceof LabeledStm) && (((LabeledStm)stm).getStm()
		// instanceof LoopStm))
		// {
		// loopStm = (LoopStm)((LabeledStm)stm).getStm();
		// break;
		// }
		// }
		// p = p.parent();
		// }
		// }
		// if(loopStm==null)
		// {
		// unit.addError(new
		// CompileException(CompileErrorMessage.BreakNotInLoop.getMsg(), node,
		// unit));
		// }
	}

	protected String label;

	// protected LabeledStm labelStm;
	// protected LoopStm loopStm;
	// protected Operator opt;
	// protected boolean loopWithoutBlock;
	public String getLabel() {
		return label;
	}

	@Override
	public Object visit(YoyooBreakLabel node, Object data) {
		label = node.first_token.image;
		// Block p = this.parent;
		//
		// while(p!=null)
		// {
		// Stm stm = p.prevSlide();
		// if(stm instanceof LabeledStm)
		// {
		// if(label == ((LabeledStm)stm).getLabel())
		// {
		// labelStm = (LabeledStm)stm;
		// break;
		// }
		// }
		// p = p.parent();
		// }
		// if(labelStm==null)
		// unit.addError(new CompileException(new String[]{label},
		// CompileErrorMessage.LabelNotDefined, node, unit));
		return null;
	}

	@Override
	public Object visit(YoyooContinueLabel node, Object data) {
		label = node.first_token.image;
		// Block p = this.parent;
		//
		// while(p!=null)
		// {
		// Stm stm = p.prevSlide();
		// if(stm instanceof LabeledStm)
		// {
		// if(label == ((LabeledStm)stm).getLabel())
		// {
		//					
		// labelStm = (LabeledStm)stm;
		// break ;
		// }
		// }
		// p = p.parent();
		// }
		// if(labelStm==null)
		// unit.addError(new CompileException(new String[]{label},
		// CompileErrorMessage.LabelNotDefined, node, unit));
		return null;
	}

}
