package statistics.moniitor.log;

import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dy on 2018/4/9.
 */
@Deprecated
public class LoggerFactory implements InvocationHandler {

    private static final ConcurrentHashMap<String, Logger> loggerProxyPool = new ConcurrentHashMap<>();
    private final Logger target;

    public LoggerFactory(Logger target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在目标对象的方法执行之前

        // 执行目标对象的方法
        Object result = method.invoke(target, args);

        // 在目标对象的方法执行之后
        return result;
    }

    public Logger getProxy() {
        Logger l = (Logger) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
        System.err.println(l.getClass().getName());
        return l;
    }

    public static Logger getLogger(String className) {
        Logger logger = loggerProxyPool.get(className);
        if (logger != null) {
            return logger;
        }
        //LoggerFactory myInvocationHandler = new LoggerFactory(org.slf4j.LoggerFactory.getLogger(className));
        //logger = myInvocationHandler.getProxy();
        logger = org.slf4j.LoggerFactory.getLogger(className);
        loggerProxyPool.putIfAbsent(className, logger);
        return loggerProxyPool.get(className);
    }

    public static void main(String[] args) {

    }
}
