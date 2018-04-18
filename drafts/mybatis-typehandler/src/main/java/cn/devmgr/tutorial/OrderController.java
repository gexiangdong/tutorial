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

import cn.devmgr.tutorial.model.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired OrderService orderService;
    
	@GetMapping
	public List<Order> getAll() {
		List<Order> list = new ArrayList<>();
		Order order = new Order();
		order.setOrderDate(Calendar.getInstance().getTime());
		String s2 = orderService.getAString(null, order);
		System.out.println("返回值：" + s2);
		return list;
	}
	
	@GetMapping("/{id}")
	public Order getOne(@PathVariable int id) {
	    return orderService.getOneOrder(id);
	}
	
	@PostMapping
	public Order crateOne(@RequestBody @Valid Order order) {
	    return order;
	}
}
