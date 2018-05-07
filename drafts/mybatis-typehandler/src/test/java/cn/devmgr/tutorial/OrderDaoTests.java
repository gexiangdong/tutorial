package cn.devmgr.tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.devmgr.tutorial.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTests {
    private final static Log log = LogFactory.getLog(OrderDaoTests.class);
    
    @Autowired OrderDao orderDao;
    @Autowired Validator validator;
    @Value("${tutorial.key:}") String key;
    
	@Test
	public void testSelectProduct() {
	    log.trace("key=" + key);
	    Product p = orderDao.selectProductById("E0001");
	    if(log.isTraceEnabled()) {
	        log.trace("got: " + (p == null ? "NULL" : p.getName()));
	        if(p != null) {
	            log.trace("SPEC: " + p.getSpecs().getClass().getName());
	            log.trace("SPEC: " + p.getSpecs());
	            if(p.getMyType() != null) {
                    log.trace("MyType: " + p.getMyType().getClass().getName());
                    log.trace("MyType: " + p.getMyType());
	            }
	            log.trace("IMAGES: " + p.getImages() == null ? "NULL" : Arrays.deepToString(p.getImages()));
	        }
	    }
	}

	@Test
	public void testConstraints() {
	    OnePojoChild op = new OnePojoChild();
	    op.setAge(5.2f);
	    op.setScore(" ");
	    List<String> list = new ArrayList<>();
	    list.add(null);
	    list.add("a");
	    list.add("b");
	    op.setImages(list);
	    Set<ConstraintViolation<OnePojo>> result = validator.validate(op);
	    if (result.size() > 0) {
            //如果同不过验证，错误信息都在这个set里
            if(log.isTraceEnabled()) {
                log.trace("未通过验证");
                for(ConstraintViolation<OnePojo> cv : result) {
                    log.trace(cv.getPropertyPath() + " " + cv.getMessage());
                }
            }
	    }else {
	        log.trace("****通过验证****");
	    }
	}
}
