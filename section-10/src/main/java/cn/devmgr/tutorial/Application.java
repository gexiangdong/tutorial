package cn.devmgr.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 如果需要使用JMS，需要@EnableJms注解打开JMS
 * 如果要使用Spring Scheduling的功能，需要加@EnableScheduling注解，否则也是不会出现编译错误，只是定时程序不被启动。
 */

@EnableScheduling
@EnableJms
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
