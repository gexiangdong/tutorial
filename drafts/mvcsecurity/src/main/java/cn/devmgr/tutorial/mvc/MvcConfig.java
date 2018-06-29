package cn.devmgr.tutorial.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(MvcConfig.class);
    
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        logger.trace("MvcConfig.addViewControllers()");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

}
