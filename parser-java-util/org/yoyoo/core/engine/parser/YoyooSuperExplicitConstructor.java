/* Generated By:JJTree: Do not edit this line. YoyooSuperExplicitConstructor.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooSuperExplicitConstructor extends SimpleNode {
  public YoyooSuperExplicitConstructor(int id) {
    super(id);
  }

  public YoyooSuperExplicitConstructor(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c312fb79d5d4489995e094dbb85fd76f (do not edit this line) */
