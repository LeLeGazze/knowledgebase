package com.castle.fortress.admin.shiro.config;

import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.shiro.diversity.JwtFilter;
import com.castle.fortress.admin.shiro.realm.CastleUserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 * @author castle
 */
//@CacheConfig
@Configuration
public class ShiroConfig {
    //引入之前定义好的域
    @Bean
    CastleUserRealm castleUserRealm(){
        return new CastleUserRealm();
    }
    //配置一个安全管理器
    @Bean
    DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        CastleUserRealm castleUserRealm = castleUserRealm();
        //将域添加到我们的安全管理器中
        manager.setRealm(castleUserRealm);
        //设置Session管理器，配置shiro中Session的持续时间
        manager.setSessionManager(getDefaultWebSessionManager());
        return manager;
    }

    //设置session过期时间
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1000 * 60*60);// 会话过期时间，单位：毫秒--->一分钟,用于测试
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        return defaultWebSessionManager;
    }



    //设置访问拦截器
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //安全管理器
        bean.setSecurityManager(securityManager());
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        //自定义过滤器
        filterMap.put("jwt", new JwtFilter());
        filterMap.put("anon", new AnonymousFilter());
        bean.setFilters(filterMap);
//        //登录页面
//        bean.setLoginUrl("/login");
//        //登录成功后跳转页面
//        bean.setSuccessUrl("/page/main");
//        //未授权页面
//        bean.setUnauthorizedUrl("/error404");
        Map<String, String> map = new LinkedHashMap<>();
        //白名单
        for(String whiteUrl: GlobalConstants.WHITE_LIST){
            map.put(whiteUrl,"anon");
        }
        //非白名单请求需要校验请求头的jwt Authorization
        map.put("/**","jwt");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
