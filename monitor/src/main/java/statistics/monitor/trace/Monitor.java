package statistics.monitor.trace;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by dy on 2018/4/9.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Monitor {

    //todo spring如何实现
    @AliasFor("name")
    String value();

    boolean ignoreParams() default false;

    boolean ignoreResult() default false;
}
