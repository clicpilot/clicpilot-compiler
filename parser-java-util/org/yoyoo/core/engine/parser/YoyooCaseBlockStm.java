/* Generated By:JJTree: Do not edit this line. YoyooCaseBlockStm.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooCaseBlockStm extends SimpleNode {
  public YoyooCaseBlockStm(int id) {
    super(id);
  }

  public YoyooCaseBlockStm(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=247d1c5283c090f77c389602eece84a3 (do not edit this line) */