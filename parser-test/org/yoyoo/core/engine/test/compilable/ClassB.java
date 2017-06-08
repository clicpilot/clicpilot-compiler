package org.yoyoo.core.engine.test.compilable;

public class ClassB extends ClassA{

	
	public static void main(String[] args) {
		new ClassB(1);
	}
	
	public ClassB(int i){
		System.out.println("ClassB"+i);
	}
	
	public ClassB(){
		System.out.println("ClassB");
	}
}
