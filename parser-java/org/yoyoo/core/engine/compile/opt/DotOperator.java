package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.YoyooEnvironment;
import org.yoyoo.core.engine.compile.atom.IAtom;
import org.yoyoo.core.engine.compile.declaration.YoyooField;
import org.yoyoo.core.engine.compile.declaration.YoyooTypeDefineClass;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.compile.type.ReferenceNameType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.RuntimeValueAtom;
import org.yoyoo.core.engine.runtime.YoyooRTException;
import org.yoyoo.core.engine.yoyoo.lang.YoyooArray;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.type.YoyooTypeDefine;


public class DotOperator extends AbstractOperator {

	public DotOperator(SimpleNode node, CompilationUnit unit,
			YoyooTypeDefineClass ycls) {
		super(node, unit, ycls);
		// TODO Auto-generated constructor stub
	}

	private IAtom left;

	private IAtom right;

	private String fieldName;

	protected IRuntimeValueAtom eval(RuntimeContext ctx)
			throws YoyooRTException {
		IRuntimeValueAtom atom = getValueAtom(left, ctx);
		YoyooObject yobj = null;
		switch (atom.getRuntimeAtomType()) {
		case Ref:
			return super.eval(ctx);
		case Value:
			yobj = ((RuntimeValueAtom) atom).getValue();
			if(yobj instanceof YoyooArray)
			{
				return new RuntimeValueAtom(YoyooArray
						.fieldValue(((YoyooArray) yobj), fieldName, ctx), node, unit);
			}
			else {
				return new RuntimeValueAtom(((YoyooTypeDefine) yobj)
						.getFieldValue(fieldName), node, unit);
			}
		default:
			return super.eval(ctx);
		}
	}

	public IRuntimeValueAtom updateValue(RuntimeContext ctx,
			IRuntimeValueAtom runtimeAtom) throws YoyooRTException {
		IRuntimeValueAtom atom = getValueAtom(left, ctx);
		YoyooObject yobj = null;
		switch (atom.getRuntimeAtomType()) {
		case Ref:
			return super.eval(ctx);
		case Value:
			yobj = ((RuntimeValueAtom) atom).getValue();

			((YoyooTypeDefine) yobj).setFieldValue(fieldName, runtimeAtom
					.getValue());

			return new RuntimeValueAtom(((YoyooTypeDefine) yobj)
					.getFieldValue(fieldName), node, unit);
		default:
			return super.eval(ctx);
		}
	}

	public void installArguments(IAtom[] arguments) throws CompileException {

		checkNumOfArguments(arguments.length, 2);
		this.left = arguments[0];
		this.right = arguments[1];

	}

	public IType operatorTypeCheck() throws CompileException {
		IType type = left.getYoyooType();
		if(type.isPrimitiveType() && type.isArray())
		{
			fieldName = getReferenceName(right);
			return YoyooArray.fieldCheck(fieldName, node, unit);
		}
		else
		{
			if(type.isArray())
			{
				fieldName = getReferenceName(right);
				return YoyooArray.fieldCheck(fieldName, node, unit);
			}
			else
			{
				ReferenceNameType leftType = (ReferenceNameType) left.getYoyooType();
				YoyooTypeDefineClass cls = YoyooEnvironment.getDefault()
						.getDeclaration(leftType.getFullName());
				if(cls==null)
					throw new CompileException.Undefined(leftType.getFullName(), node, unit);
				fieldName = getReferenceName(right);
				YoyooField field = cls.getField(fieldName);
				if(field==null)
					throw new CompileException.FieldNotFound(fieldName, cls.getFullName(), node, unit);
				else if(field.accessibilityCheck(cls)) {
					return field.getType();
				} else {
					return null;
				}
				
			}
		}
	}
	
	public String getLeftName()
	{
		return this.getReferenceName(left);
	}
	
	protected IAtom getLeft() {
		return left;
	}

	

	protected IAtom getRight() {
		return right;
	}


	public String getRightName()
	{
		return this.getReferenceName(right);
	}
	
	
	
	

}
