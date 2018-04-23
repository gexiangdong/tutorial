package cn.devmgr.tutorial.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class Order {
	private String id;
	
	@Null
	private Date orderDate;
	
	@NotNull
	@Valid
	private ConsigneeAddress consigneeAddress;
	
	@NotNull
	@Size(min=1, message="订单中至少需要一个产品")
	@Valid
	private List<OrderItem> orderItems;
	
	private int status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(@Valid List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public ConsigneeAddress getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(ConsigneeAddress consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


    
    @Override
	public String toString(){
		return "Order{id=" + id + "; consigeeAddress=" + (consigneeAddress) + "; orderItems=" + 
					(orderItems == null ? "null" : "size=" + orderItems.size() + ", " + Arrays.toString(orderItems.toArray())) + "}";
	}
}
