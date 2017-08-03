package com.dmoffat.website.config;

import com.dmoffat.web.common.view.manifest.ManifestFileLoaderInterceptor;
import com.dmoffat.website.controller.interceptor.AdminAuthenticationInterceptor;
import com.dmoffat.website.controller.interceptor.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author dan
 */
@Configuration
@ComponentScan("com.dmoffat.web.common")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private AdminAuthenticationInterceptor adminAuthenticationInterceptor;
    private PaginationInterceptor paginationInterceptor;
    private ManifestFileLoaderInterceptor manifestFileLoaderInterceptor;

    @Autowired
    public WebMvcConfig(AdminAuthenticationInterceptor adminAuthenticationInterceptor,
                        PaginationInterceptor paginationInterceptor, ManifestFileLoaderInterceptor manifestFileLoaderInterceptor) {
        this.adminAuthenticationInterceptor = adminAuthenticationInterceptor;
        this.paginationInterceptor = paginationInterceptor;
        this.manifestFileLoaderInterceptor = manifestFileLoaderInterceptor;
    }



    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/management", "/management/**")
                .excludePathPatterns("/management/auth");

        registry.addInterceptor(paginationInterceptor).addPathPatterns("/**");
        registry.addInterceptor(manifestFileLoaderInterceptor).addPathPatterns("/**");
    }

}
