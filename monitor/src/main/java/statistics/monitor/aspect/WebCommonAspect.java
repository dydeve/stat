package statistics.monitor.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import statistics.monitor.log.Log;
import statistics.monitor.trace.Overall;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * web通用切面
 * Created by dy on 2018/4/9.
 */
@Aspect
public class WebCommonAspect extends AbstractAspect {


    @Pointcut("@annotation(requestMapping)")
    public void logPointCut(RequestMapping requestMapping) {

    }

    @Around("logPointCut(requestMapping)")
    public Object logWebCommon(ProceedingJoinPoint pjp, RequestMapping requestMapping) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Overall overall = get(request.getHeader("tid"));
        try {

        } catch (Exception e) {

        } finally {

        }
        return null;
    }

    public static void main(String[] args) {
        NoSuchElementException e1 = new NoSuchElementException("no");
        NullPointerException e = new NullPointerException("null");
        Log.info("###########");
        Log.error("!!!!!!!!!!", e);
    }

}
