/* Generated By:JJTree: Do not edit this line. YoyooAlloReferenceExpr.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooAlloReferenceExpr extends SimpleNode {
  public YoyooAlloReferenceExpr(int id) {
    super(id);
  }

  public YoyooAlloReferenceExpr(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=bfcf73d135fc514c7563e70713b73a3a (do not edit this line) */