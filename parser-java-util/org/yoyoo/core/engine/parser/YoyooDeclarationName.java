/* Generated By:JJTree: Do not edit this line. YoyooDeclarationName.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=Yoyoo,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.yoyoo.core.engine.parser;

public
class YoyooDeclarationName extends SimpleNode {
  public YoyooDeclarationName(int id) {
    super(id);
  }

  public YoyooDeclarationName(YoyooParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(YoyooParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=fcce695db7fd7a1fc51afe66d8973f40 (do not edit this line) */
