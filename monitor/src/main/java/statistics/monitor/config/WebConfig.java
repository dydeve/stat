package statistics.monitor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import statistics.monitor.web.MDCHandlerInterceptor;

/**
 * @see org.springframework.web.servlet.config.annotation.EnableWebMvc
 * Created by dy on 2018/4/13.
 */
@Configuration
//@ComponentScan(basePackageClasses = { NOPClass.class })
public class WebConfig extends DelegatingWebMvcConfiguration {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new MDCHandlerInterceptor());
        interceptorRegistration.addPathPatterns("/**");

        //No mapping found for HTTP request with URI [/favicon.ico] in DispatcherServlet
        interceptorRegistration.excludePathPatterns("/favicon.ico");
        super.addInterceptors(registry);
    }
}
