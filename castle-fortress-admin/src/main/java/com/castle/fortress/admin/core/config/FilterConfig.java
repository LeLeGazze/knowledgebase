package com.castle.fortress.admin.core.config;

import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.core.filter.ApiReleaseCommonFilter;
import com.castle.fortress.admin.core.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * @author castle
 */
@Configuration
public class FilterConfig {

    /**
     * 对外开放接口
     * @return
     */
    @Bean
    public FilterRegistrationBean apiReleaseFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ApiReleaseCommonFilter());
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.addUrlPatterns(GlobalConstants.API_PREFIX+"*");
        registration.setName("apiReleaseCommonFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    /**
     * 对外开放接口
     * @return
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}
