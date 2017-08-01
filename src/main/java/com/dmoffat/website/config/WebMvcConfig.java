package com.dmoffat.website.config;

import com.dmoffat.website.controller.interceptor.AdminAuthenticationInterceptor;
import com.dmoffat.website.controller.interceptor.PaginationInterceptor;
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
    private PaginationInterceptor paginationInterceptor;

    @Autowired
    public WebMvcConfig(AdminAuthenticationInterceptor adminAuthenticationInterceptor,
                        PaginationInterceptor paginationInterceptor) {
        this.adminAuthenticationInterceptor = adminAuthenticationInterceptor;
        this.paginationInterceptor = paginationInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/management", "/management/**")
                .excludePathPatterns("/management/auth");

        registry.addInterceptor(paginationInterceptor).addPathPatterns("/**");
    }

}
