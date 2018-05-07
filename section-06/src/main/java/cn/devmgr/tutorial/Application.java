package cn.devmgr.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这里没用@MapperScan注解，是因为在每个mapper接口上都增加了@Mapper注解，已经告诉Mybatis从哪里去找mapper了
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
