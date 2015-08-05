package com.wolai.platform.vo;

import java.math.BigDecimal;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Bill.PayType;

public class UnionpayVo {
	public UnionpayVo() {
		this.productionName = Constant.payment_productionName;
		this.productionDesrcription = Constant.payment_productionDesrcription;
		this.partnerId = Constant.alipay_partnerId;
		this.partnerPrivKey = Constant.alipay_partnerPrivKey;
		this.sellerAccount = Constant.alipay_sellerAccount;
		this.notifyUrl = Constant.alipay_notify_url;
	}
	
	private String partnerId;
	private String partnerPrivKey;	
	private String sellerAccount;
	private String productionName;
	private String productionDesrcription;
	private String notifyUrl;
	
	private String wolaiTradeNo;
	private String payTradeNo;
	private BigDecimal amount;	
	private PayType paytype;
	

	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerPrivKey() {
		return partnerPrivKey;
	}
	public void setPartnerPrivKey(String partnerPrivKey) {
		this.partnerPrivKey = partnerPrivKey;
	}
	public String getSellerAccount() {
		return sellerAccount;
	}
	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}
	public String getProductionName() {
		return productionName;
	}
	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}
	public String getProductionDesrcription() {
		return productionDesrcription;
	}
	public void setProductionDesrcription(String productionDesrcription) {
		this.productionDesrcription = productionDesrcription;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public PayType getPaytype() {
		return paytype;
	}
	public void setPaytype(PayType paytype) {
		this.paytype = paytype;
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

}
