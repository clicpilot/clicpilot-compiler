package org.yoyoo.core.engine.compile.encode;

import java.io.IOException;
import java.io.Writer;


import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.atom.IValueAtom;
import org.yoyoo.core.engine.compile.declaration.IDeclaration;
import org.yoyoo.core.engine.compile.exp.Expr;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.encode.exception.EncodeException;

public interface ICodeDecorator extends Observer {

	public void encode(IValueAtom atom) throws EncodeException;
	public void encode(Expr expr) throws EncodeException;
	public void encode(Stm stm) throws EncodeException;
	public void encode(IDeclaration decl) throws EncodeException;
	public void start(CompilationUnit unit) throws EncodeException;
	public void output(Writer out) throws IOException ;
	public boolean output(StringBuffer out, long checksum) throws IOException ;
	public boolean output(Writer out, long checksum) throws IOException;
	public void encodeDecl(IDeclaration decl) throws EncodeException;
	
}
