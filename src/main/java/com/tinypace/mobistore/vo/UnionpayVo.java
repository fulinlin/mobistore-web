package com.tinypace.mobistore.vo;

import java.math.BigDecimal;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.Bill.PayType;

public class UnionpayVo {
	public UnionpayVo() {
		this.merId = Constant.unionpay_mchId;
		this.payType = PayType.UNIONPAY.toString();
	}
	
	private String merId;
	
	private String wolaiTradeNo;
	private String payTradeNo;
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private String payType;
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getWolaiTradeNo() {
		return wolaiTradeNo;
	}
	public void setWolaiTradeNo(String wolaiTradeNo) {
		this.wolaiTradeNo = wolaiTradeNo;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	


}
