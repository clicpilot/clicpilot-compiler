/* Generated By:JJTree: Do not edit this line. YoyooSwitchKey.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooSwitchKey extends SimpleNode {
  public YoyooSwitchKey(int id) {
    super(id);
  }

  public YoyooSwitchKey(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3cd075dce5a1141a57498fd776a6f81e (do not edit this line) */
