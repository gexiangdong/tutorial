package cn.devmgr.tutorial;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.devmgr.tutorial.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTests {
    private final static Log log = LogFactory.getLog(OrderDaoTests.class);
    
    @Autowired OrderDao orderDao;
    
	@Test
	public void testSelectProduct() {
	    Product p = orderDao.selectProductById("E0001");
	    if(log.isTraceEnabled()) {
	        log.trace("got: " + (p == null ? "NULL" : p.getName()));
	        if(p != null) {
	            log.trace("SPEC: " + p.getSpecs().getClass().getName());
	            log.trace("SPEC: " + p.getSpecs());
	            log.trace("IMAGES: " + p.getImages() == null ? "NULL" : Arrays.deepToString(p.getImages()));
	        }
	    }
	}

}
