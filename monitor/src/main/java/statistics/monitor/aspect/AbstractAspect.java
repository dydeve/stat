package statistics.monitor.aspect;

import statistics.monitor.trace.Overall;
import statistics.monitor.util.UniqueId;

/**
 * Created by dy on 2018/4/10.
 */
public abstract class AbstractAspect {

    protected static final ThreadLocal<Overall> holder = new ThreadLocal<Overall>()/* {
        @Override
        protected Overall initialValue() {
            return new Overall();
        }
    }*/;

    protected Overall get(String tid) {
        Overall overall = holder.get();
        if (overall == null) {

            if (this instanceof MonitorAspect) {
                throw new UnsupportedOperationException("monitorAspect can't init threadLocal");
            }
            //todo 测试引用改变
            //第一次
            overall = new Overall(getTid(tid));
            holder.set(overall);
        }
        return overall;
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
