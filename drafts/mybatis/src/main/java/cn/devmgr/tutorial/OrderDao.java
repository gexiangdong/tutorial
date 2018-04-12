package cn.devmgr.tutorial;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OrderDao {
	
	public int insertOrder(@Param("order") OrderPojo order);
	
	public OrderPojo getOrderById(int id);
	
	public ProductPojo selectProductById(@Param("id") String id);
}
