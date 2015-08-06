package com.wolai.platform.vo;

public class UnionpayCardBoundVo {
	
	String id;
	private String accType;
	private String accNo;
	private String boundType;
	private String wolaiTradeNo;
	private String expired;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getBoundType() {
		return boundType;
	}
	public void setBoundType(String boundType) {
		this.boundType = boundType;
	}
	public String getWolaiTradeNo() {
		return wolaiTradeNo;
	}
	public void setWolaiTradeNo(String wolaiTradeNo) {
		this.wolaiTradeNo = wolaiTradeNo;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
}
