/* Generated By:JJTree: Do not edit this line. YoyooThrowsNameList.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooThrowsNameList extends SimpleNode {
  public YoyooThrowsNameList(int id) {
    super(id);
  }

  public YoyooThrowsNameList(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=b300a6834da4de590f2c0b3953d6f941 (do not edit this line) */
