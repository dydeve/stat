package statistics.monitor.log;

import org.slf4j.Logger;

/**
 * 不关心 文件 方法 行号
 * Created by dy on 2018/4/10.
 */
public class Logs {

    //性能统计log
    public static final Logger profiling = org.slf4j.LoggerFactory.getLogger("profiling");
    //流处理log
    public static final Logger stream = org.slf4j.LoggerFactory.getLogger("stream");

}
