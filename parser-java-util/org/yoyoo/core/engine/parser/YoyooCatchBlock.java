/* Generated By:JJTree: Do not edit this line. YoyooCatchBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooCatchBlock extends SimpleNode {
  public YoyooCatchBlock(int id) {
    super(id);
  }

  public YoyooCatchBlock(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=952530f95a476c847c117b6cfbc68cc3 (do not edit this line) */
