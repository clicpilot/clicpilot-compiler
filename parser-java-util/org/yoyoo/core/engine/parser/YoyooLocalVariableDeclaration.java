/* Generated By:JJTree: Do not edit this line. YoyooLocalVariableDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooLocalVariableDeclaration extends SimpleNode {
  public YoyooLocalVariableDeclaration(int id) {
    super(id);
  }

  public YoyooLocalVariableDeclaration(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0b3d97f11ba993aefd56755fddd0e3e6 (do not edit this line) */
