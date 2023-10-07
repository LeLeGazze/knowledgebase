package com.castle.fortress.admin.core.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * 跨域配置
 * @author castle
 */
@Configuration
public class CastleWebConfig implements WebMvcConfigurer {

    /**
     * 跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许跨域访问的源
                .allowedOrigins("*")
                // 允许请求方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 预检间隔时间
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*")
        ;
    }


    /**
     * 静态资源处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置文件上传的文件不拦截
//	        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+ TaleUtils.getUplodFilePath()+"upload/");
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //swagger
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 信息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        //解决前端与后端8小时时差问题
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //Long类型转String类型
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);
        //实体类未存在字段处理
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(mapper);

        return converter;
    }

    public static void update(ApplicationContext ctx,String templatePath, String resourceFolder) {
        SimpleUrlHandlerMapping mapping = (SimpleUrlHandlerMapping) ctx.getBean("resourceHandlerMapping");
        ResourceHttpRequestHandler cssHandler = (ResourceHttpRequestHandler) mapping.getUrlMap().get("/css/**");
        List<String> resourceList = new ArrayList<>();
        if (cssHandler != null) {
            resourceList.clear();
            resourceList.add("File:"+templatePath+resourceFolder+File.separator+"css"+File.separator);
            cssHandler.setLocationValues(resourceList);
            cssHandler.getLocations().clear();
            cssHandler.getResourceResolvers().clear();
            try {
                cssHandler.afterPropertiesSet();
            } catch (Throwable ex) {
            }
        }

        ResourceHttpRequestHandler jsHandler = (ResourceHttpRequestHandler) mapping.getUrlMap().get("/js/**");
        if (jsHandler != null) {
            resourceList.clear();
            resourceList.add("File:"+templatePath+resourceFolder+File.separator+"js"+File.separator);
            jsHandler.setLocationValues(resourceList);
            jsHandler.getLocations().clear();
            jsHandler.getResourceResolvers().clear();
            try {
                jsHandler.afterPropertiesSet();
            } catch (Throwable ex) {
            }
        }

        ResourceHttpRequestHandler imagesHandler = (ResourceHttpRequestHandler) mapping.getUrlMap().get("/images/**");
        if (imagesHandler != null) {
            resourceList.clear();
            resourceList.add("File:"+templatePath+resourceFolder+File.separator+"images"+File.separator);
            imagesHandler.setLocationValues(resourceList);
            imagesHandler.getLocations().clear();
            imagesHandler.getResourceResolvers().clear();
            try {
                imagesHandler.afterPropertiesSet();
            } catch (Throwable ex) {
            }
        }

        ResourceHttpRequestHandler iconHandler = (ResourceHttpRequestHandler) mapping.getUrlMap().get("/favicon.ico");
        if (iconHandler != null) {
            resourceList.clear();
            resourceList.add("File:"+templatePath+resourceFolder+File.separator+"favicon.ico");
            iconHandler.setLocationValues(resourceList);
            iconHandler.getLocations().clear();
            iconHandler.getResourceResolvers().clear();
            try {
                iconHandler.afterPropertiesSet();
            } catch (Throwable ex) {
            }
        }
    }
}
