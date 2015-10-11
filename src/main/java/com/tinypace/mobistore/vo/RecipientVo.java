package com.tinypace.mobistore.vo;

public class RecipientVo {
	private String id;
	private String name;
	private String phone;
	private String provice;
	private String city;
	private String region;
	private Integer proviceId;
	private Integer cityId;
	private Integer regionId;
	private String street;
    private String address;
    private Boolean defaultt;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getProvice() {
		return provice;
	}
	public void setProvice(String provice) {
		this.provice = provice;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Integer getProviceId() {
		return proviceId;
	}
	public void setProviceId(Integer proviceId) {
		this.proviceId = proviceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
    
}
