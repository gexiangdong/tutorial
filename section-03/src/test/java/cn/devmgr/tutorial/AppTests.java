package cn.devmgr.tutorial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.devmgr.tutorial.service.TvSeriesService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {
    @Autowired TvSeriesService tvSeriesService;
    
	@Test
	public void contextLoads() throws Exception {
	}

}
