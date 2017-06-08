package org.yoyoo.core.engine.compile.opt;

import org.yoyoo.core.engine.runtime.RuntimeContext;

public interface IStop {

	public boolean isStop();

	public boolean isContinue();

	public void setStop(boolean stop, RuntimeContext ctx);

	public void setContinue(boolean stop);

	public void resetStop();

	public boolean isMark();

	public void mark();

	public boolean isLoop();

	public void loop();
}
