package org.yoyoo.core.engine.compile.encode;




public class CodeDecoratorObservableDelegate extends Observable {
	
	
	public CodeDecoratorObservableDelegate() {
		super();
		
	}

	public void codeChanged() {
		super.setChanged();
	}

	
}
