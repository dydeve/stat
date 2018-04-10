package statistics.monitor.aspect;

import statistics.monitor.trace.StopWatch;
import statistics.monitor.util.UniqueId;

/**
 * Created by dy on 2018/4/10.
 */
public abstract class AbstractAspect {

    protected static final ThreadLocal<StopWatch> holder = new ThreadLocal<>()/* {
        @Override
        protected Overall initialValue() {
            return new Overall();
        }
    }*/;

    protected StopWatch get(String tid) {
        StopWatch stopWatch = holder.get();
        if (stopWatch == null) {

            if (this instanceof MonitorAspect) {
                throw new UnsupportedOperationException("monitorAspect can't init threadLocal");
            }
            //todo 测试引用改变
            //第一次
            stopWatch = new StopWatch(getTid(tid));
            holder.set(stopWatch);
        }
        return stopWatch;
    }

    protected static final void clear() {
        holder.remove();
    }

    private String getTid(String tid) {
        if (tid == null) {
            return UniqueId.generate();
        }
        return tid;
    }

}
