package com.wolai.platform.vo;

import java.math.BigDecimal;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Bill.PayType;

public class UnionpayVo {
	public UnionpayVo() {
		this.merId = Constant.unionpay_mchId;
		this.payType = PayType.UNIONPAY.toString();
	}
	
	private String merId;
	
	private String wolaiTradeNo;
	private String payTradeNo;
	private BigDecimal amount;	
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	


}
