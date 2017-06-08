package org.yoyoo.core.engine.compile.atom;

import java.util.ArrayList;
import java.util.List;

import org.yoyoo.core.engine.compile.CompilationUnit;
import org.yoyoo.core.engine.compile.CompileException;
import org.yoyoo.core.engine.compile.stm.Stm;
import org.yoyoo.core.engine.compile.type.IType;
import org.yoyoo.core.engine.parser.SimpleNode;
import org.yoyoo.core.engine.runtime.IRuntimeValueAtom;
import org.yoyoo.core.engine.runtime.RuntimeContext;
import org.yoyoo.core.engine.runtime.YoyooRTException;

public class ReferenceAtom extends AbstractValueAtom {

	// public ReferenceNameAtom(String name) {
	// super(null);
	// this.name = name;
	// }
	//	
	// public ReferenceNameAtom(YoyooObject val, String name) {
	// super(val);
	// this.name = name;
	// }

	protected String name;

	protected Stm stm;
	
	protected List<IType> typeList;
	
	protected IType assignedType; 
	

//	public ReferenceAtom(YoyooTypeDefineClass ycls, IType type, String name,
//			SimpleNode node, CompilationUnit unit) {
//		super(type, node, unit);
//		this.name = name;
//		this.ycls = ycls;
//		
//		
//	}


	public List<IType> getTypeList() {
		return typeList;
	}

	public void addType(IType type) {
		if(typeList==null) {
			typeList = new ArrayList<IType>();
		}
		typeList.add(type);		
	}
	
	
	public ReferenceAtom(Stm stm, String name,
			SimpleNode node, CompilationUnit unit) {
		super(null, node, unit);
		this.name = name;
		this.stm = stm;
	}

	public IRuntimeValueAtom getVal(RuntimeContext ctx) throws YoyooRTException {
		
		IRuntimeValueAtom valAtom = ctx.lookupAtom(name, node, unit);
		return valAtom;

	}

	public String getName() {
		return name;
	}

	public AtomType getAtomType() {
		return AtomType.Ref;
	}

	public IType getYoyooType() throws CompileException {
		if (type != null) {
			return type;
		} else {
			type = this.unit.getVariableType(stm.getYcls(), stm.getMethod(), stm.getVariableTable(), this, name, node, unit);
			return type;
		}
//		else {
//			if (name.equals(RuntimeContext.THIS)) {
//				type = TypeParserHelper.createReferenceNameType(ycls
//						.getCompilationUnit().getPackageName(), ycls.getName(),
//						node);
//				return type;
//			} else if(name.indexOf(".")==-1){
//				YoyooField field = ycls.getField(name);
//				if (field == null) {
//					throw new CompileException.Undefined(name, node, unit);
//				} else {
//					type = field.getType();
//					return type;
//				}
//			}
//			else {
//				String subnames[] = name.split("\\.");
//				YoyooClass typecls = ycls;
//				
//				for(int i=1;i<subnames.length;i++)
//				{
//					if(typecls instanceof YoyooTypeDefineClass)
//					{
//						YoyooField field = ((YoyooTypeDefineClass)typecls).getField(subnames[i]);
//						type = field.getType();
//						if(type instanceof ReferenceNameType)
//						{
//							typecls = (YoyooTypeDefineClass)((ReferenceNameType)type).map2YoyooClass(YoyooEnvironment.getDefault());
//						}
//						else
//						{
//							if(i == subnames.length-1)
//							{
//								return type;
//							}
//							else
//							{
//								throw new CompileException.FieldNotFound(subnames[i], type.getName(), node, unit);
//							}
//						}
//					}
//					else
//					{
//						throw new CompileException.FieldNotFound(subnames[i], type.getName(), node, unit);
//					}
//					
//					
//				}
//				return type;
//			}
//				
//		}
	}

	public Stm getStm() {
		return stm;
	}
	
	public IType getAssignedType() {
		return assignedType;
	}

	public void setAssignedType(IType assignedType) {
		this.assignedType = assignedType;
	}

}
