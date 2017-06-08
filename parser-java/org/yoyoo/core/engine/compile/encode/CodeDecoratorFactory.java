package org.yoyoo.core.engine.compile.encode;

import org.yoyoo.core.engine.compile.Constants;

public class CodeDecoratorFactory {
	public enum CodeDecoratorType{
		DEFAULT(Constants.FILE_EXT_YOYOO), 
		PREPROCESSOR(Constants.FILE_EXT_YOYOO), 
		JAVA("java"), 
		JSP("jsp"), 
		CSHARP("cs"), 
		PHP("php"), 
		JAVA2YOYOO(Constants.FILE_EXT_YOYOO), 
		JAVATEMPLATE("java"),
		ABAP("abap"),
		ABAPPLAIN("abap"),
		CPListenerCode("CPListenerCode"), 
		JSPCLASS("jspclass");
		public static CodeDecoratorType getCodeDecoratorType(String typeName) {
			for(CodeDecoratorType type : CodeDecoratorType.values()) {
				if(type.name().equalsIgnoreCase(typeName)) {
					return type;
				}
			}
			return null;
			
		}
		private String ext;
		private CodeDecoratorType(String ext){
			this.ext = ext;
		}
		public String getExt() {
			return ext;
		}
	
	};
	private static CodeDecoratorFactory instance;
	
	private CodeDecoratorFactory () {		
		
	}
	
	public static CodeDecoratorFactory getInstance() {
		if(instance == null) {
			instance = new CodeDecoratorFactory();
		}
		return instance;
	}
	
	public ICodeDecorator getCodeDecorator(CodeDecoratorType type) {
		switch(type) {
			default:
			case DEFAULT:
			case CSHARP:
			case PHP:
				return new DefaultCodeDecorator();
			case JAVA:
				return new JavaCodeDecorator();
			case JSP:
				return new JspCodeDecorator();
			case JSPCLASS:
				return new JspClassCodeDecorator();
			case JAVATEMPLATE:
				return new JavaTemplateCodeDecorator();
			case PREPROCESSOR:
				return new PreProcessorCodeDecorator();
			case JAVA2YOYOO:
				return new Java2YoyooCodeDecorator();
			case ABAP:
				return new AbapCodeDecorator();
			case ABAPPLAIN:
				return new AbapPlainCodeDecorator();
			case CPListenerCode:
				return new ClicPilotListenerCodeCodeDecorator();	
		} 
	}
	
	
}
