package org.yoyoo.core.engine.compile.encode;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.Modifier;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.encode.exception.EncodeException;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.parser.Token;
import org.yoyoo.core.engine.parser.YoyooParserConstants;



public class PreProcessorCodeDecorator extends DefaultCodeDecorator {

	public final static String TEMPLATE_CLASS = "TemplateProcessor";
	
	public final static String TEMPLATE_INTERFACE = "ITemplateProcessor";

	private  final static  Pattern pattern = Pattern.compile("\\$(\\w*)\\.(\\w*)\\((.*)\\)\\{");

	private enum Mode{
		REPLACE("if(true){str+=<1>((string)(<2>))+\" \";} else{"),
		REPLACE_ARG("if(true){str+=<1>((string)(argsMap.get(\"<2>\")))+\" \";} else{"),
		REPLACE_NONSTR("if(true){str+=<1>(<2>);} else{"),
		REMOVE("if(false){"),
		PACKAGE("if(true){str+=<1>\"package \"+((string)(argsMap.get(\"packageName\")) )+\";\";} else{"),
		CLASS("if(true){str+=<1>\"class \"+((string)(argsMap.get(\"className\")) )+ \"{\";} else{"),
		CLASSX("if(true){str+=<1>\"class \"+((string)(argsMap.get(\"className\")) )+ (StringUtil.isEmpty(\"<2>\")?\"\":\" extends <2>\") + \"{\";} else{"),
		CLASSC("if(true){str+=<1>\" public \"+((string)(argsMap.get(\"className\")) )+ \"(){\";} else{"),
		CLASSCP("if(true){str+=<1>\" public \"+((string)(argsMap.get(\"className\")) )+<2>+\"{\";} else{"),
		
		;
		
		
		private String codeBase;
		private Mode(String codeBase) {
			this.codeBase = codeBase;
		}
		public String translate(String[] replacement) {
			String code = codeBase;
			if(replacement!=null) {
				for(int i=0;i<replacement.length;i++)
					code =  code.replace("<"+(i+1)+">", replacement[i]);
			}
			return code;
		}
		
		public static Mode findMode(String modeString){
			for(Mode mode : Mode.values()) {
				if(mode.name().equalsIgnoreCase(modeString)) {
					return mode;
				}
			}
			return REPLACE;
		}
	
	};
	
	private enum StringFunction{
		PRINT(""),
		PRINTFU("StringUtil.firstUpper"),
		PRINTFL("StringUtil.firstLower"),
		PRINTU("StringUtil.upper"),
		PRINTL("StringUtil.lower"),
		PRINTQ("StringUtil.quote"),
		PRINTQNN("StringUtil.quoteNotNull"),
		
		;
		private String functionName;
		private StringFunction(String functionName) {
			this.functionName = functionName;
		}
		public String getFunctionName() {
			return functionName;
		}
		public static StringFunction findStringFunction(String stringFunctionString){
			for(StringFunction stringFunction : StringFunction.values()) {
				if(stringFunction.name().equalsIgnoreCase(stringFunctionString)) {
					return stringFunction;
				}
			}
			return PRINT;
		}
	}
	
	@Override
	public void start(CompilationUnit unit) throws EncodeException {
			
		if(unit.containsPreProcessorStm()) {
			if(unit.getDecl().size()!=1) {
				throw new EncodeException("Only one class can exsit in compile unit.");
			}
			YoyooTypeDefineClass ycls = null;
			Iterator<YoyooTypeDefineClass> iterator = unit.getDecl().getSymbols().values().iterator();
			if(iterator.hasNext())
				ycls = iterator.next();
			this.unit = unit;	
//			String fn = this.unit.getSource().getFile().getName();
//			int dotIndex = fn.lastIndexOf(".");
//			if(dotIndex!=-1) {
//				fn = fn.substring(0, dotIndex);
//			}
			
			appendKeyWord(KeyWord.KeyPackage);
			appendSpace();
			append(this.unit.getPackageName());
			appendStatementEnd();
			appendLineBreak();
			appendLineBreak();
			
			appendImports();
			append(getModifier(Modifier.PUBLIC));
			appendSpace();
			
			append("class");
			appendSpace();
			append(ycls.getName()+TEMPLATE_CLASS);
			
			
			appendSpace();
			appendKeyWord(KeyWord.KeyImplements);
			appendSpace();
			append(TEMPLATE_INTERFACE);
			
			appendSpace();
			appendStatementBracketStart();		
			appendLineBreak();	
			
			appendLineBreak();
			indent = 1;

			appendTab(indent);
			
			append(getModifier(Modifier.PUBLIC));
			appendSpace();
			append("string");
			appendSpace();		

			append("process");
			appendParamParenthesesStart();
			append("Map argsMap");
			appendParamParenthesesEnd();
			appendStatementBracketStart();
			appendLineBreak();
			
			indent = 2;
			appendTab(indent);
			append("string str = \"\";");
			appendLineBreak();
			appendTab(indent);
			append("str += \"");
			
			try {
				appendUnitBody(unit.getCompilationUnitNode());
			} catch (IOException e) {
				e.printStackTrace();
				throw new EncodeException(e.getMessage());
			}
			
			indent = 2;
			appendTab(indent);
			append("\";");
			
			
			appendLineBreak();
			
			appendTab(indent);
			appendKeyWord(KeyWord.KeyReturn);
			appendSpace();
			append("str");
			appendStatementEnd();
			appendLineBreak();
			
			indent = 1;
			appendTab(indent);
			appendStatementBracketEnd();
			
			
			appendLineBreak();	
			
			appendStatementBracketEnd();
			appendLineBreak();
		}
	}


//	@Override
//	public void encode(YoyooTypeDefineClass decl) {
//		
//		
//		appendKeyWord(KeyWord.KeyPackage);
//		appendSpace();
//		append(this.unit.getPackageName());
//		appendStatementEnd();
//		appendLineBreak();
//		appendLineBreak();
//		
//		appendImports();
//		
//		Modifier modifier = decl.getModifier();
//		if(decl.getModifier()!=null) {
//			String modifierName = getModifier(modifier);
//			append(modifierName);
//			if(modifierName.length()>0) {
//				appendSpace();
//			}
//		}
//		append(TEMPLATE_CLASS);
//		appendSpace();
//		append(decl.getName());
//		
//
//		appendSpace();
//		appendStatementBracketStart();		
//		appendLineBreak();	
//		
//		appendLineBreak();
//		indent = 1;
//
//		appendTab(indent);
//		
//		append(getModifier(Modifier.PUBLIC));
//		appendSpace();
//		appendKeyWord(KeyWord.KeyStatic);
//		appendSpace();
//		append("string");
//		appendSpace();
//		append("output");
//		appendParamParenthesesStart();
//		appendParamParenthesesEnd();
//		appendStatementBracketStart();
//		appendLineBreak();
//		
//		indent = 2;
//		appendTab(indent);
//		append("string str = \"\";");
//		appendLineBreak();
//		appendTab(indent);
//		append("str += \"");
//		
//		super.encode(decl);
//		
//		indent = 2;
//		appendTab(indent);
//		append("\";");
//		
//		
//		appendLineBreak();
//		
//		appendTab(indent);
//		appendKeyWord(KeyWord.KeyReturn);
//		appendSpace();
//		append("str");
//		appendStatementEnd();
//		appendLineBreak();
//		
//		indent = 1;
//		appendTab(indent);
//		appendStatementBracketEnd();
//		
//		
//		appendLineBreak();	
//		
//		appendStatementBracketEnd();
//		appendLineBreak();
//
//	}
//
//	public PreProcessorCodeDecorator() {
//		super();
//		this.indent = 0;
//	}
//
//	
//	@Override
//	public void encode(YoyooMethod decl) {
//		SimpleNode node = decl.getNode();
//
//		appendLineBreak();
//		indent = 2;
//		appendTab(indent);
//		append("\";");
//		appendLineBreak();
//
//
//		String preMethod = node.first_token.specialToken.image;
//		appendTab(indent);
//		append(preMethod.substring(3, preMethod.length()-3).trim());
//		appendLineBreak();
//		
//		appendTab(indent);
//		append("str += \"");
//		indent = 0;
//		
//		super.encode(decl);
//		
//		
//		indent = 2;
//		appendTab(indent);
//		append("\";");
//		appendLineBreak();
//		
//		String postMethod = node.last_token.specialToken.image;
//		appendTab(indent);
//		append(postMethod.substring(3, postMethod.length()-3).trim());		
//		appendLineBreak();
//		
//		indent = 2;
//		appendTab(indent);
//		append("str += \"");
//		appendLineBreak();
//		
//	}

	
	private void appendUnitBody(SimpleNode compilationUnitNode) throws IOException {
		RandomAccessFile file = new RandomAccessFile(this.unit.getSource().getFile(), "r");
		try{
			Token currentToken = compilationUnitNode.first_token;
			Token nextToken = currentToken.next;
			int beginPos = 0;//currentToken.beginPos;
			int endPos = nextToken.beginPos;
			Token lastSpecialToken = null;
			while(true) {				
				byte[] b = new byte[endPos-beginPos];
				file.read(b, 0, b.length);
				String code = new String(b, "utf-8");
				if(currentToken.specialToken!=null && lastSpecialToken != currentToken.specialToken) {
					lastSpecialToken = currentToken.specialToken;
					int specialTokenBeginPos = currentToken.specialToken.beginPos;
					String proprocessorCode = currentToken.specialToken.image;
					
					
					if(currentToken.specialToken.kind == YoyooParserConstants.PREPROCESSOR_BLOCK) {	
						String proprocessorCodeBody = proprocessorCode.substring(3, proprocessorCode.length()-3);
						String traslatedBody = translatePrecessScript(proprocessorCodeBody.trim());
						if(traslatedBody==null) {
							String codePrefix;
							if(specialTokenBeginPos == 0) {
								codePrefix = "";
							} else {
								codePrefix = code.substring(0, specialTokenBeginPos-beginPos);
							}
							if(codePrefix.indexOf("\"")!=-1) {
								codePrefix = codePrefix.replace("\"", "\\\"");
							}
							append(codePrefix);
							append("\";");
							appendLineBreak();
							append(proprocessorCodeBody);					
							appendLineBreak();	
							append("str+=\"");
						} else {
							String codePrefix;
							if(specialTokenBeginPos == 0) {
								codePrefix = "";
							} else {
								codePrefix = code.substring(0, specialTokenBeginPos-beginPos);
							}
							append(codePrefix);
							append("\";");
							appendLineBreak();
							append(traslatedBody);
							appendLineBreak();	
							append("str+=\"");
						}
						
					} else if(currentToken.specialToken.kind == YoyooParserConstants.PREPROCESSOR_BLOCK2) {	
						String proprocessorCodeBody = proprocessorCode.substring(6, proprocessorCode.length()-6);
						String traslatedBody = translatePrecessScript(proprocessorCodeBody.trim());
						if(traslatedBody==null) {
							String codePrefix;
							if(specialTokenBeginPos == 0) {
								codePrefix = "";
							} else {
								codePrefix = code.substring(0, specialTokenBeginPos-beginPos);
							}
							if(codePrefix.indexOf("\"")!=-1) {
								codePrefix = codePrefix.replace("\"", "\\\"");
							}
							append(codePrefix);
							append("\";");
							appendLineBreak();
							append(proprocessorCodeBody);					
							appendLineBreak();	
							append("str+=\"");
						} else {
							String codePrefix;
							if(specialTokenBeginPos == 0) {
								codePrefix = "";
							} else {
								codePrefix = code.substring(0, specialTokenBeginPos-beginPos);
							}
							append(codePrefix);
							append("\";");
							appendLineBreak();
							append(traslatedBody);
							appendLineBreak();	
							append("str+=\"");
						}
						
					} else if(currentToken.specialToken.kind == YoyooParserConstants.PREPROCESSOR_VARIABLE_BLOCK) {
						
						append("\"+((string)argsMap.get(\""+proprocessorCode.substring(3, proprocessorCode.length()-3).trim()+"\"))+\" ");					
						
					}
					
					
				} else if(currentToken.kind == YoyooParserConstants.STRING_LITERAL) {
					append("\\\""+code.substring(1, code.length()-1)+"\\\"");
				} else {
					append(code);
				}
				
				currentToken = nextToken;				
				if(currentToken!=null) {
					nextToken = currentToken.next;	
					beginPos = currentToken.beginPos;
					endPos = nextToken==null?currentToken.endPos:nextToken.beginPos;
				} else {
					break;
				}
				
			}
			
		}  catch (IOException e) {
			throw e;
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				
			}
		}
		
	}


	protected void appendQuotedString(String str) {
		buffer.append("\\\""+str+"\\\"");
	}
	
	
	private String translatePrecessScript(String code) {
		Matcher macther = pattern.matcher(code);
		if(macther.matches()) {
			return translate(macther.group(1), macther.group(2), macther.group(3));
		} else {
			return null;
		}		
	}
	
	private String translate(String mode, String strfun, String param) {
		return Mode.findMode(mode).translate(new String[]{StringFunction.findStringFunction(strfun).functionName, param});		
	}
	
	public static void main(String[] args) {
		PreProcessorCodeDecorator d = new PreProcessorCodeDecorator();
		System.out.println(d.translatePrecessScript("$classx.print(AbstractDataProvider){"));
	}

}
