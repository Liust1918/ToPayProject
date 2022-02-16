package com.liust.myproject.Config;

import com.liust.myproject.MyInterceptor.timeLimitInterceptor;
import com.liust.myproject.MyInterceptor.timesLimitInterceptor;
import com.liust.myproject.MyInterceptor.valueLimitInterceptor;
import com.liust.myproject.MyInterceptor.whiteListInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private whiteListInterceptor whiteListInterceptor;

    @Resource
    private timeLimitInterceptor timeLimitInterceptor;

    @Resource
    private valueLimitInterceptor valueLimitInterceptor;

    @Resource
    private timesLimitInterceptor timesLimitInterceptor;

    private final String url="/pay/save";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle
        registry.addInterceptor(whiteListInterceptor)
                .addPathPatterns(url);
        registry.addInterceptor(timeLimitInterceptor)
                .addPathPatterns(url);
        registry.addInterceptor(valueLimitInterceptor)
                .addPathPatterns(url);
        registry.addInterceptor(timesLimitInterceptor)
                .addPathPatterns(url);

    }
}
