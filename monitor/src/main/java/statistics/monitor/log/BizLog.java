package statistics.monitor.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 业务日志
 * 避免到处写 LoggerFactory.getLogger
 * 关心 文件 方法 行号
 * Created by dy on 2018/4/9.
 */
public class BizLog {

    public static final String CUSTOM_LOGGER_CLASS_NAME = BizLog.class.getName();
    private static final Logger LOGGER = LoggerFactory.getLogger(BizLog.class);

    private static final HashMap<String, Logger> loggerPool = new HashMap<>();

    private static Logger log() {
        //return statistics.moniitor.log.LoggerFactory.getLogger(getCallerClassName());
        Logger logger = loggerPool.get(getCallerClassName());
        if (logger != null) {
            return logger;
        }
        String className = getCallerClassName();
        logger = LoggerFactory.getLogger(className);
        synchronized (BizLog.class) {
            loggerPool.putIfAbsent(className, logger);
        }
        return logger;//loggerPool.get(className);
    }


    private static String getCallerClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 4; i < stackTrace.length; i++) {
            if (!stackTrace[i].getClassName().equals(CUSTOM_LOGGER_CLASS_NAME)) {
                return stackTrace[i].getClassName();
            }
        }

        //it's impossible to run here
        LOGGER.error("can't find caller class, ", new RuntimeException());
        return CUSTOM_LOGGER_CLASS_NAME;
    }

    public static void debug(String msg) {
        log().debug(msg);
    }


    public static void debug(String format, Object arg) {
        log().debug(format, arg);
    }


    public static void debug(String format, Object arg1, Object arg2) {
        log().debug(format, arg1, arg2);
    }


    public static void debug(String format, Object... arguments) {
        log().debug(format, arguments);
    }


    public static void debug(String msg, Throwable t) {
        log().debug(msg, t);
    }


    public static void info(String msg) {
        log().info(msg);
    }

    public static void info(String format, Object arg) {
        log().info(format, arg);
    }

    public static void info(String format, Object arg1, Object arg2) {
        log().info(format, arg1, arg2);
    }

    public static void info(String format, Object... arguments) {
        log().info(format, arguments);
    }

    public static void info(String msg, Throwable t) {
        log().info(msg, t);
    }


    public static void error(String msg) {
        log().error(msg);
    }


    public static void error(String format, Object arg) {
        log().error(format, arg);
    }


    public static void error(String format, Object arg1, Object arg2) {
        log().error(format, arg1, arg2);
    }


    public static void error(String format, Object... arguments) {
        log().error(format, arguments);
    }

    public static void error(String msg, Throwable t) {
        log().error(msg, t);
    }
}
