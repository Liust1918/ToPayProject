package com.liust.myproject.Config;

import com.liust.myproject.MyInterceptor.whiteListInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private whiteListInterceptor whiteListInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(whiteListInterceptor)
                .addPathPatterns("/pay/save");

    }
}
