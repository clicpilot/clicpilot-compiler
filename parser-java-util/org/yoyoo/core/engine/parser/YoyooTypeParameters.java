/* Generated By:JJTree: Do not edit this line. YoyooTypeParameters.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooTypeParameters extends SimpleNode {
  public YoyooTypeParameters(int id) {
    super(id);
  }

  public YoyooTypeParameters(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=853d21a48bfcae04b00a6a9fdea84f30 (do not edit this line) */
