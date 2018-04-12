package cn.devmgr.tutorial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired OrderService orderService;
    
	@GetMapping
	public List<OrderPojo> getAll() {
		List<OrderPojo> list = new ArrayList<>();
		OrderPojo order = new OrderPojo();
		order.setOrderDate(Calendar.getInstance().getTime());
		String s2 = orderService.getAString(null, order);
		System.out.println("返回值：" + s2);
		return list;
	}
	
	@GetMapping("/{id}")
	public OrderPojo getOne(@PathVariable int id) {
	    return orderService.getOneOrder(id);
	}
	
	@PostMapping
	public OrderPojo crateOne(@RequestBody @Valid OrderPojo order) {
	    return order;
	}
}
