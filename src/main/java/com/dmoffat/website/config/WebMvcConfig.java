package com.dmoffat.website.config;

import com.dmoffat.website.controller.interceptor.AdminAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author dan
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private AdminAuthenticationInterceptor adminAuthenticationInterceptor;

    @Autowired
    public WebMvcConfig(AdminAuthenticationInterceptor adminAuthenticationInterceptor) {
        this.adminAuthenticationInterceptor = adminAuthenticationInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/management", "/management/**")
                .excludePathPatterns("/management/auth");
    }

}
