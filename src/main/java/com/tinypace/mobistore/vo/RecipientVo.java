package com.tinypace.mobistore.vo;

public class RecipientVo {
	private String id;
	private String name;
	private String phone;
	private String area;
	private String street;
    private String address;
    private Boolean defaultt;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getDefaultt() {
		return defaultt;
	}
	public void setDefaultt(Boolean defaultt) {
		this.defaultt = defaultt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
    
}
