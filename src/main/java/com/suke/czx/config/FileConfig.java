package com.suke.czx.config;

import com.suke.czx.common.utils.ConfigConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/*").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + ConfigConstant.FILE_BASE_PATH);
    }

}
