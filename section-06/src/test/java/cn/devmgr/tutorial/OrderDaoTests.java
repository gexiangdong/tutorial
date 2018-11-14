package cn.devmgr.tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.devmgr.tutorial.model.ConsigneeAddress;
import cn.devmgr.tutorial.model.Order;
import cn.devmgr.tutorial.model.OrderItem;
import cn.devmgr.tutorial.model.OrderType;
import cn.devmgr.tutorial.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTests {
    private final static Log log = LogFactory.getLog(OrderDaoTests.class);
    
    @Autowired OrderService orderService;
    @Autowired OrderDao orderDao;
	
    @Test
	public void testSelectProduct() {
	    Order orderNew = new Order();
	    orderNew.setOrderType(OrderType.WHOLESALE);
	    orderNew.setConsigneeAddress(new ConsigneeAddress("张三", "13344446666", "上海市", "上海", "黄浦区", "人民广场"));
        ArrayList<OrderItem> list = new ArrayList<>();
        OrderItem oi1 = new OrderItem();
	    oi1.setProductId("E0001");
	    oi1.setProductName("iphone手机模型");
	    oi1.setNum(5);
	    list.add(oi1);
        OrderItem oi2 = new OrderItem();
        oi2.setProductId("B0002");
        oi2.setProductName("电脑包");
        oi2.setNum(3);
        list.add(oi2);

        orderNew.setOrderItems(list);
	    Order ord = orderService.createOrder(orderNew);
	    if(log.isTraceEnabled()) {
	        log.trace("id: " + ord.getId());
	    }
	    
	    Product p = orderDao.selectProductById("E0001");
	    if(log.isTraceEnabled()) {
	        log.trace("got: " + (p == null ? "NULL" : p.getName()));
	        if(p != null) {
	            log.trace("SPEC: " + p.getSpecs().getClass().getName());
	            log.trace("SPEC: " + p.getSpecs());
	            log.trace("IMAGES: " + p.getImages() == null ? "NULL" : Arrays.deepToString(p.getImages()));
	        }
	    }
	    
	    Order order = orderDao.getOrderById(1);
	    if(order != null) {
	        if(log.isTraceEnabled()) {
	            log.trace("ORDER: #" + order.getId() + "; type:" + order.getOrderType());
	        }
	    }
	}
	
	//@Test
	public void testAddProduct() {
	    if(log.isTraceEnabled()) {
	        log.trace("testAddProduct");
	    }
	    Product prod = new Product();
	    prod.setId("T" + String.valueOf(new Random().nextInt(10000)));
	    prod.setName("N-" + prod.getId());
	    prod.setPrice(1111);
	    Map<String, String> specs = new HashMap<String, String>();
	    specs.put("weight", "4kg");
	    prod.setSpecs(specs);
	    prod.setImages(new String[] {"a.jpg", "b.jpg", "c.jpg", "d.jpg"});
	    orderService.createProduct(prod);
	}

}
