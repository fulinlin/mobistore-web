package com.tinypace.mobistore.vo;

import java.io.Serializable;
import java.util.Date;

public class PaychekResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6106603227791390916L;

	private Integer code=2;
	
	private String msg="";
	
	/**
	 * 新利泊外部系统编号
	 */
	private String exNo;
	
	/**
	 * 是否已付费
	 */
	private Boolean isPaid=Boolean.FALSE;
	
	/**
	 * 喔来订单编号
	 */
	private String orderId;
	
	/**
	 * 喔来订单生成时间
	 */
	private Long orderCreateTime;
	
	/**
	 * 付款时间
	 */
	private Long payTime;
	
	/**
	 * 已付金额
	 */
	private String payAmount="0";
	
	/**
	 * 抵时券时长
	 */
	private Long couponTime=0L;
	
	/**
	 * 代金券金额
	 */
	private Integer couponAmount=0;
	
	/**
	 *  抵时券或代金券编号
	 */
	private String couponSn;
	
	/**
	 * 出口编号
	 */
	private String exportNo;
	
	/**
	 * 	车牌号
	 */
	private String carNo;
	
	/**
	 * 	入库时间
	 */
	private Long enterTime ;
	
	/**
	 * 出库时间
	 */
	private Long exitTime ;
	
	/**
	 * 时间戳
	 */
	private Long timestamp=new Date().getTime();

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
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

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public Long getCouponTime() {
		return couponTime;
	}

	public void setCouponTime(Long couponTime) {
		this.couponTime = couponTime;
	}

	public Integer getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Integer couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getCouponSn() {
		return couponSn;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

}
