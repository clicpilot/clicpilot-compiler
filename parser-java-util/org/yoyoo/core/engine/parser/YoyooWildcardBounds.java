/* Generated By:JJTree: Do not edit this line. YoyooWildcardBounds.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooWildcardBounds extends SimpleNode {
  public YoyooWildcardBounds(int id) {
    super(id);
  }

  public YoyooWildcardBounds(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7004a2c67573283da48b74130ff24bfb (do not edit this line) */
