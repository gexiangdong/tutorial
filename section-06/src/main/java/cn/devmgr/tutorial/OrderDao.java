package cn.devmgr.tutorial;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.devmgr.tutorial.model.Order;
import cn.devmgr.tutorial.model.Product;


@Mapper
public interface OrderDao {
	
	public int insertOrder(@Param("order") Order order);
	
	public Order getOrderById(int id);
	
	public Product selectProductById(@Param("id") String id);
	
	public int insertProduct(@Param("product") Product product);
}
