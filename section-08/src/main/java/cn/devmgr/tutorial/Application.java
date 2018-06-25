package cn.devmgr.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 如果要使用Spring Cache的相关功能， 需要增加@EnableCaching的注解，否则cache是不会生效的，而且不生效也不会出错。
 * 如果要使用Spring Scheduling的功能，同样要@EnableScheduling注解，否则也是不会出现编译错误，只是定时程序不被启动。
 * 如果要使用异步执行@Async注解的功能，同样要@EnableAsync注解，否则也是不会出现编译错误，只是不会被异步执行。
 */

@EnableCaching
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
