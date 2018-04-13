package statistics.monitor.aspect;

import com.google.common.base.Throwables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import statistics.monitor.log.Logs;
import statistics.monitor.trace.Monitor;
import statistics.monitor.trace.StopWatch;
import statistics.monitor.trace.TaskInfo;
import statistics.monitor.util.IpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * web通用切面
 * Created by dy on 2018/4/9.
 */
@Aspect
public class WebAspect extends AbstractAspect {

    public static final String webProfilingLog = "web profiling, stopWatch:{}, taskInfo:{}";

    @Pointcut("@annotation(requestMapping) && @annotation(monitor)")
    public void logPointCut(RequestMapping requestMapping, Monitor monitor) {

    }

    @Around("logPointCut(requestMapping, monitor)")
    public Object profilingWeb(ProceedingJoinPoint pjp, RequestMapping requestMapping, Monitor monitor) throws Throwable {
        ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletContainer.getRequest();

        StopWatch stopWatch = get(MDC.get("traceId"));

        stopWatch.setHost(IpUtils.getLocalIpAddress());
        stopWatch.setClient(IpUtils.getIpAddress(request));
        stopWatch.setUri(request.getRequestURI());
        stopWatch.setQueryString(request.getQueryString());

        boolean success = true;
        TaskInfo taskInfo = stopWatch.start(monitor.value());
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            success = false;
            taskInfo.setError(Throwables.getStackTraceAsString(throwable));
            throw throwable;
        } finally {
            try {
                taskInfo = stopWatch.stop();
                stopWatch.setStatus(servletContainer.getResponse().getStatus());
                if (success) {
                    Logs.profiling.info(webProfilingLog, stopWatch, taskInfo);
                } else {
                    Logs.profiling.error(webProfilingLog, stopWatch, taskInfo);
                }
            } finally {
                clear();
            }


        }
    }

    /*public static void main(String[] args) {
        NoSuchElementException e1 = new NoSuchElementException("no");
        NullPointerException e = new NullPointerException("null");
        Log.error("!!!!!!!!!!,{}", new StopWatch("asf"));

        Logs.profiling.error("!!!!!!!!!!", e);
        Logs.stream.error("!!!!!!!!!!", e);
    }*/

}
