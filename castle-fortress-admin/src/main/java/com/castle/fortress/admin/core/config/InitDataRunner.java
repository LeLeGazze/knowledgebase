package com.castle.fortress.admin.core.config;

import com.castle.fortress.admin.system.service.SysRegionService;
import com.castle.fortress.admin.utils.SpringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * 初始化系统部分数据
 * @author castle
 */
@Configuration
public class InitDataRunner implements ApplicationRunner, ServletContextAware {
    private ServletContext servletContext;
    private SysRegionService sysRegionService;


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
        //初始化applicationContext
        new SpringUtils().setApplicationContext(cxt);
        if(cxt != null ){
            if(sysRegionService == null){
                sysRegionService = (SysRegionService) cxt.getBean(SysRegionService.class);
            }
        }
        //初始化redis 地区tree
        sysRegionService.initRegionTree();
    }

}
