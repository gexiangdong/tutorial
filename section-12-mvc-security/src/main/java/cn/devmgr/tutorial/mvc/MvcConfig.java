package cn.devmgr.tutorial.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(MvcConfig.class);
    
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        logger.trace("MvcConfig.addViewControllers()");
        // 设置登录页面
        registry.addViewController("/login").setViewName("login");
    }
    
}
