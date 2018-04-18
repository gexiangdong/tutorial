package cn.devmgr.tutorial.model;

import javax.validation.constraints.NotNull;

public class ConsigneeAddress {
	@NotNull
	private String province,city, district, address;
	@NotNull
	private String consignee, phone;
	
	public ConsigneeAddress(){
		
	}
	public ConsigneeAddress(String consignee, String phone, String province, String city, String district, String address){
		this.consignee = consignee;
		this.phone = phone;
		this.province = province;
		this.city = city;
		this.district = district;
		this.address = address;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
