/* Generated By:JJTree: Do not edit this line. YoyooWhileCond.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooWhileCond extends SimpleNode {
  public YoyooWhileCond(int id) {
    super(id);
  }

  public YoyooWhileCond(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c4b6f3f0bd61b01a51b60257d3eea27d (do not edit this line) */
