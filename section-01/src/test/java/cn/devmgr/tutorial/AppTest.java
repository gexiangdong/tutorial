package cn.devmgr.tutorial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

	@Test
	public void contextLoads() {
	    //别小看这个空测试用例；如果spring-boot配置出现问题，这个空用例很可能执行不通过
	}

}
