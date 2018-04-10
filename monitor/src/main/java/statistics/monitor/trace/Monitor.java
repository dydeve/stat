package statistics.monitor.trace;

import org.springframework.core.annotation.AliasFor;

/**
 * Created by dy on 2018/4/9.
 */
public @interface Monitor {

    //todo spring如何实现
    @AliasFor("name")
    String value();

    @AliasFor("value")
    String name();

    boolean ignoreParams() default false;

    boolean ignoreResult() default false;
}
