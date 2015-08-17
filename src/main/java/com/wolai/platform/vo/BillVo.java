package com.wolai.platform.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wolai.platform.entity.Coupon;
import com.wolai.platform.entity.SysAccount;
import com.wolai.platform.entity.Bill.PayStatus;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.Coupon.CouponType;

public class BillVo {
	private String id;
	private String carNo;
	private BigDecimal Money;
	private BigDecimal realMoney;
	private PayType paytype;
	private String tradeNo;
	private String tradeStatus;
	private  Boolean isSendedInvoice;
	private PayStatus payStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public BigDecimal getMoney() {
		return Money;
	}
	public void setMoney(BigDecimal money) {
		Money = money;
	}
	public BigDecimal getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(BigDecimal realMoney) {
		this.realMoney = realMoney;
	}
	public PayType getPaytype() {
		return paytype;
	}
	public void setPaytype(PayType paytype) {
		this.paytype = paytype;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public Boolean getIsSendedInvoice() {
		return isSendedInvoice;
	}
	public void setIsSendedInvoice(Boolean isSendedInvoice) {
		this.isSendedInvoice = isSendedInvoice;
	}
	public PayStatus getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	
}
