/* Generated By:JJTree: Do not edit this line. YoyooSpecialExpr.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooSpecialExpr extends SimpleNode {
  public YoyooSpecialExpr(int id) {
    super(id);
  }

  public YoyooSpecialExpr(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=470fa9c1227d344670c60c3bfa836963 (do not edit this line) */
