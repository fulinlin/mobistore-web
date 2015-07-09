package com.wolai.platform.vo;

public class LicenseVo {
	private String id;
	private String carNo;
	private String frameNumber;
	private String brand;
	private Boolean isPostpaid;
	
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Boolean getIsPostpaid() {
		return isPostpaid;
	}
	public void setIsPostpaid(Boolean isPostpaid) {
		this.isPostpaid = isPostpaid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
