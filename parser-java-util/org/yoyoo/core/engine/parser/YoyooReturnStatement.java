/* Generated By:JJTree: Do not edit this line. YoyooReturnStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooReturnStatement extends SimpleNode {
  public YoyooReturnStatement(int id) {
    super(id);
  }

  public YoyooReturnStatement(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b76602cc06a631498edcba1ace6c3dc5 (do not edit this line) */
