package org.yoyoo.core.engine.yoyoo.lang.type;

import static java.lang.System.out;

import org.yoyoo.core.engine.yoyoo.lang.YoyooBoolean;
import org.yoyoo.core.engine.yoyoo.lang.YoyooChar;
import org.yoyoo.core.engine.yoyoo.lang.YoyooDouble;
import org.yoyoo.core.engine.yoyoo.lang.YoyooFloat;
import org.yoyoo.core.engine.yoyoo.lang.YoyooInteger;
import org.yoyoo.core.engine.yoyoo.lang.YoyooLong;
import org.yoyoo.core.engine.yoyoo.lang.YoyooNull;
import org.yoyoo.core.engine.yoyoo.lang.YoyooObject;
import org.yoyoo.core.engine.yoyoo.lang.YoyooShort;
import org.yoyoo.core.engine.yoyoo.lang.YoyooString;

public class Function extends YoyooTypeDefine {

	public void print(YoyooObject obj) {
		if (obj instanceof YoyooString)
			System.out.print(((YoyooString) obj).getVal());
		else if (obj instanceof YoyooBoolean)
			System.out.print(((YoyooBoolean) obj).getVal());
		else if (obj instanceof YoyooInteger)
			System.out.print(((YoyooInteger) obj).getVal().intValue());
		else if (obj instanceof YoyooLong)
			System.out.print(((YoyooLong) obj).getVal());
		else if (obj instanceof YoyooShort)
			System.out.print(((YoyooShort) obj).getVal());
		else if (obj instanceof YoyooChar)
			System.out.print(((YoyooChar) obj).getVal());
		else if (obj instanceof YoyooDouble)
			System.out.print(((YoyooDouble) obj).getVal());
		else if (obj instanceof YoyooFloat)
			System.out.print(((YoyooFloat) obj).getVal().floatValue());
		else if (obj instanceof YoyooNull)
			System.out.print(((YoyooNull) obj).getVal());
	}

	public void println(YoyooObject obj) {
		if (obj instanceof YoyooString)
			System.out.println(((YoyooString) obj).getVal());
		else if (obj instanceof YoyooBoolean)
			System.out.println(((YoyooBoolean) obj).getVal());
		else if (obj instanceof YoyooInteger)
			System.out.println(((YoyooInteger) obj).getVal().intValue());
		else if (obj instanceof YoyooLong)
			System.out.println(((YoyooLong) obj).getVal());
		else if (obj instanceof YoyooShort)
			System.out.println(((YoyooShort) obj).getVal());
		else if (obj instanceof YoyooChar)
			System.out.println(((YoyooChar) obj).getVal());
		else if (obj instanceof YoyooDouble)
			System.out.println(((YoyooDouble) obj).getVal());
		else if (obj instanceof YoyooFloat)
			System.out.println(((YoyooFloat) obj).getVal().floatValue());
		else if (obj instanceof YoyooNull)
			System.out.println(((YoyooNull) obj).getVal());
	}

	public static void main(String args[]) {
		String[] str  = new String[5];
		for(int i=0;i<5;i++)
			out.println(str[i]);
		for (int i = 0; i < 3; i++) {
			out.println(i);
			;
			switch (i) {
			case 0:
				out.println("a");
				break;
			case 1:
				out.println("$");
				continue;
			case 2:
				out.println("#");
				break;
			}
		}

	}
}
