/* Generated By:JJTree: Do not edit this line. YoyooVoidType.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooVoidType extends SimpleNode {
  public YoyooVoidType(int id) {
    super(id);
  }

  public YoyooVoidType(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ab3ee98f220d21d9f33625bee5226e09 (do not edit this line) */
