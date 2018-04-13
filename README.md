# statistics
- 引入alibaba 的[ttl](https://github.com/alibaba/transmittable-thread-local),重新定义了`ThreadLocal`,支持 父线程到子线程， 以及线程池 之间的threadlocal
    <br>使ttl生效  需开启java angent 或 包装 [`java.lang.Runnable`] `Callable` `Executor`.
    <br>详见[`alibaba-ttl`](https://github.com/alibaba/transmittable-thread-local#busts_in_silhouette-user-guide)
- [`BizLog`](monitor/src/main/java/statistics/monitor/log/BizLog.java)
    <br>避免到处
    ````java
  //using  
  BizLog.info(...);
  //instead of 
  Logger logger = LoggerFactory.getLogger();
  logger.info(...);  
- MDC：MDC使用自定义的[`TtlMDCAdapter`](monitor/src/main/java/statistics/monitor/log/TtlMDCAdapter.java), 内部用ttl代替 `InheritableThreadLocal`