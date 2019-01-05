package cn.devmgr.tutorial;

import cn.devmgr.tutorial.dao.DynamicSqlDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisLangTest {
    private static final Logger logger = LoggerFactory.getLogger(MybatisLangTest.class);

    @Autowired
    DynamicSqlDao tvSeriesDao;

    @Test
    public void testLang(){
        logger.trace("test mybatis lang.....");
        Map<String, Object> params = new HashMap<>();
        params.put("id",  1);
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("name");
        List<?> list = tvSeriesDao.getByConditions("tv_series", columns, params);
        logger.trace("dao return {}", list == null ? "null" : list.size());
        if(list.size() > 0){
            logger.trace("row 1: {}", list.get(0));
        }
    }
}
