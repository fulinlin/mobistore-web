package com.wolai.platform.vo;

import java.io.Serializable;

public class PayQueryVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6935660870549995316L;
	
	private String exNo;
	private Long couponTime;
	private String carNo;
	private Long enterTime;
	private Long timestamp;
	
	public String getExNo() {
		return exNo;
	}
	public void setExNo(String exNo) {
		this.exNo = exNo;
	}
	public Long getCouponTime() {
		return couponTime;
	}
	public void setCouponTime(Long couponTime) {
		this.couponTime = couponTime;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public Long getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
}
