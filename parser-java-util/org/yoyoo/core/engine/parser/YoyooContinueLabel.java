/* Generated By:JJTree: Do not edit this line. YoyooContinueLabel.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooContinueLabel extends SimpleNode {
  public YoyooContinueLabel(int id) {
    super(id);
  }

  public YoyooContinueLabel(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1504244dc7fd4becba2010dc158e4918 (do not edit this line) */
