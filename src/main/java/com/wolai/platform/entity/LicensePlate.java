package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 车牌信息
 * @author 徐祥
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
	
	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
}
