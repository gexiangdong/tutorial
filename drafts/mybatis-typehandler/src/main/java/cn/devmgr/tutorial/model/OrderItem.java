package cn.devmgr.tutorial.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class OrderItem {
	@NotNull
	private String productId;
	private String productName;
	
    @DecimalMin(value = "1", message = "购买数量不可以少于1个")
    @DecimalMax(value = "10", message = "购买数量不可以多于10个")
	private int num;
    
	private int price;
	private int id;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString(){
        return "OrderItem{id=" + id + "; productId=" + productId + "; num=" + num + "; price=" + price + ";}";
    }

}
