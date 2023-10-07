package com.castle.fortress.admin.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.castle.fortress.admin.core.interceptor.DataAuthInterceptor;
import com.castle.fortress.admin.core.interceptor.SaveInterceptor;
import com.castle.fortress.admin.core.interceptor.UpdateInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus 配置
 * @author  hcses
 */
@Configuration
@MapperScan(basePackages = {"com.castle.fortress.*.*.mapper","com.castle.fortress.*.*.*.mapper"})
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 数据权限插件
     * @return
     */
    @Bean
    public DataAuthInterceptor dataAuthInterceptor(){return new DataAuthInterceptor();}
    /**
     * 保存插件
     * @return
     */
    @Bean
    public SaveInterceptor saveInterceptor(){return new SaveInterceptor();}

    /**
     * 修改插件
     * @return
     */
    @Bean
    public UpdateInterceptor updateInterceptor(){return new UpdateInterceptor();}

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
