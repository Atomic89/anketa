package com.ssp.table;


import org.springframework.web.servlet.config.annotation.*;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/, classpath:/META-INF/web-resources/");
    }

}