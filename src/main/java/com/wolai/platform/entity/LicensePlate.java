package com.wolai.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 车辆信息
 * @author Ethan
 */
@Entity
@Table(name="wo_car_no")
public class LicensePlate extends idEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5128744877634675218L;

	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 车架号
	 */
	private String frameNumber;
	
	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 支付方式
	 */
	private Boolean isPostpaid=Boolean.FALSE;
	
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
}
