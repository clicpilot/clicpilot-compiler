/* Generated By:JJTree: Do not edit this line. YoyooLiteralChar.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooLiteralChar extends SimpleNode {
  public YoyooLiteralChar(int id) {
    super(id);
  }

  public YoyooLiteralChar(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ba3a734541c3d183e4fa77697c8fefbc (do not edit this line) */