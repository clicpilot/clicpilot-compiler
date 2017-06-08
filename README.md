# clicpilot-compiler

This is a language parser and runtime implementation, named yoyoo.
It uses JavaCC to generate parser, all the language runtime environment is by Java.

The syntax is similar to Java, and the relationship between Java and yoyoo is like C/C++ and Java.

### org.yoyoo.core.engine.parser

 this package is language parser
 
### org.yoyoo.core.engine.compile

 this package is the compiler implementation
 
### org.yoyoo.core.engine.runtime

 this package is language runtime.



Here is an simple example of yoyoo, for more example please check **arser-test**

  
    package org.yoyoo.example;

      public class hello {
	
	    public static void main() {
		    println("hello");
	    }
      
    }
    


**run it**
java -cp clicpilot-compiler.jar org.yoyoo.core.engine.compile.Compiler


Here is the main method of **org.yoyoo.core.engine.compile.Compiler**



    public static void main(String[] args) {
      YoyooEnvironment env = YoyooEnvironment.getDefault();
      env.clearError();
      env.addYoyooPath("./");
      RuntimeContext cctx = null;
      cctx = RuntimeContext.getCurrentContext();
      cctx.run("yoyoo", "app", "main");
    }
  

The Compiler will find the app.yoyoo file from current the path '{currentpath}/yoyoo/app.yoyoo'.

If you are interested in language compiler, it is an example from parser to runtime.
