/* Generated By:JJTree: Do not edit this line. YoyooArrayDecl.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooArrayDecl extends SimpleNode {
  public YoyooArrayDecl(int id) {
    super(id);
  }

  public YoyooArrayDecl(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b0c28ef6bf5ffbe51df0dbbaafdd6c78 (do not edit this line) */
