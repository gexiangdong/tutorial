package cn.devmgr.tutorial.bpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 此处禁止了activiti的默认spring security设置，因为它使用的是spring security 4.x部分，在
 * spring-boot 2.x中不能生效
 */
@SpringBootApplication(exclude=org.activiti.spring.boot.SecurityAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}