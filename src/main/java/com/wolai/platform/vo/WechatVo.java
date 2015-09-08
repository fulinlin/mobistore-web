package com.wolai.platform.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.util.IdGen;
import com.wolai.platform.wechat.WechatConfigure;

public class WechatVo {
	
	public WechatVo() {
		this.notifyUrl = Constant.WEB_PATH + Constant.wechat_pay_notify_uri;
	}
	
	private String appid = WechatConfigure.appId;
	private String partnerid = WechatConfigure.mchId;
	private String signStr;
	
	private String wolaiTradeNo;

	private String sign;
	private String prepayId;
	
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private String payType;
	
	private String packagee = WechatConfigure.packagee;
	private String nonce_str;
	private String timestamp;
	private String notifyUrl;
	private String tradeType = WechatConfigure.tradeType;
	
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
	public String getPackagee() {
		return packagee;
	}
	public void setPackagee(String packagee) {
		this.packagee = packagee;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getSignStr() {
		return signStr;
	}
	public void setSignStr(String signStr) {
		this.signStr = signStr;
	}
	


}
