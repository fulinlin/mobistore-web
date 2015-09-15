package com.tinypace.mobistore.vo;

import java.math.BigDecimal;

import com.tinypace.mobistore.constant.Constant;
import com.tinypace.mobistore.entity.Bill.PayType;

public class AlipayVo {
	public AlipayVo() {
		this.productionName = Constant.payment_productionName;
		this.productionDesrcription = Constant.payment_productionDesrcription;
		this.partnerId = Constant.alipay_partnerId;
		this.partnerPrivKey = Constant.alipay_partnerPrivKey;
		this.sellerAccount = Constant.alipay_sellerAccount;
		this.notifyUrl = Constant.WEB_PATH + Constant.alipay_notify_uri;
		this.payType = PayType.ALIPAY.toString();
	}
	
	private String partnerId;
	private String partnerPrivKey;	
	private String sellerAccount;
	private String productionName;
	private String productionDesrcription;
	private String notifyUrl;
	
	private String wolaiTradeNo;
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private String payType;
	

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

	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
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

}
