/* Generated By:JJTree: Do not edit this line. YoyooAllocationName.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooAllocationName extends SimpleNode {
  public YoyooAllocationName(int id) {
    super(id);
  }

  public YoyooAllocationName(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=416c4dfa08597f10e9c6d50bd40deed4 (do not edit this line) */