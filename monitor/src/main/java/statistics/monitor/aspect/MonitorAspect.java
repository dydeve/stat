package statistics.monitor.aspect;

import com.google.common.base.Throwables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import statistics.monitor.log.Logs;
import statistics.monitor.trace.Monitor;
import statistics.monitor.trace.StopWatch;
import statistics.monitor.trace.TaskInfo;

/**
 * 先经过web,mq rpc 等切面 设置host，uri等
 * 然后进过monitor切面
 * Created by dy on 2018/4/9.
 */
@Aspect
public class MonitorAspect extends AbstractAspect {

    public static final String monitorProfilingLog = "monitor profiling, taskInfo:{}";

    @Pointcut("!@annotation(org.springframework.web.bind.annotation.RequestMapping) && @annotation(monitor)")
    public void logPointCut(Monitor monitor) {

    }

    @Around("logPointCut(monitor)")
    public Object profilingMonitor(ProceedingJoinPoint pjp, Monitor monitor) throws Throwable {
        StopWatch stopWatch = get(null);

        boolean success = true;
        TaskInfo taskInfo = stopWatch.start(monitor.value());
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            success = false;
            taskInfo.setError(Throwables.getStackTraceAsString(throwable));
            throw throwable;
        } finally {
            taskInfo = stopWatch.stop();
            if (success) {
                Logs.profiling.info(monitorProfilingLog, taskInfo);
            } else {
                Logs.profiling.error(monitorProfilingLog, taskInfo);
            }
            //clear in web, mq, rpc, and so on...
            //clear();
        }

    }


}
