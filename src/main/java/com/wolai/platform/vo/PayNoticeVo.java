package com.wolai.platform.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayNoticeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 162231396868408688L;

	
	private String exNo;
	private Boolean isPaid=Boolean.TRUE;
	private String orderId;
	private Long orderCreateTime;
	private Long payTime;
	private BigDecimal payAmount;
	private Long couponTime;
	private Long couponAmount;
	private String couponSn;
	private String exportNo;
	private String carNo;
	private Long enterTime;
	private Long exitTime;
	private Long timestamp;
	public String getExNo() {
		return exNo;
	}
	public void setExNo(String exNo) {
		this.exNo = exNo;
	}
	public Boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Long orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public Long getPayTime() {
		return payTime;
	}
	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public Long getCouponTime() {
		return couponTime;
	}
	public void setCouponTime(Long couponTime) {
		this.couponTime = couponTime;
	}
	public Long getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Long couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getCouponSn() {
		return couponSn;
	}
	public void setCouponSn(String couponSn) {
		this.couponSn = couponSn;
	}
	public String getExportNo() {
		return exportNo;
	}
	public void setExportNo(String exportNo) {
		this.exportNo = exportNo;
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
	public Long getExitTime() {
		return exitTime;
	}
	public void setExitTime(Long exitTime) {
		this.exitTime = exitTime;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
