package cn.devmgr.tutorial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@MapperScan("cn.devmgr.tutorial.dao")
@EnableConfigurationProperties
public class  Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
