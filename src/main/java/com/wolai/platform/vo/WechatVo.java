package com.wolai.platform.vo;

import java.math.BigDecimal;

import com.wolai.platform.wechat.WechatConfigure;

public class WechatVo {
	
	private String appid = WechatConfigure.appId;
	private String partnerid = WechatConfigure.mchId;
	
	private String wolaiTradeNo;

	private String sign;
	private String prepayId;
	
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private String payType;
	
	public String getWolaiTradeNo() {
		return wolaiTradeNo;
	}
	public void setWolaiTradeNo(String wolaiTradeNo) {
		this.wolaiTradeNo = wolaiTradeNo;
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

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	


}
