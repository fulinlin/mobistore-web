package com.wolai.platform.vo;

import java.math.BigDecimal;

import com.wolai.platform.constant.Constant;
import com.wolai.platform.entity.Bill.PayType;

public class PayVo {
	public PayVo() {

	}
	
	private BigDecimal totalAmount;
	private BigDecimal payAmount;
	private Boolean confirmPostPay;
	private Boolean isPaid;

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
	public Boolean getConfirmPostPay() {
		return confirmPostPay;
	}
	public void setConfirmPostPay(Boolean confirmPostPay) {
		this.confirmPostPay = confirmPostPay;
	}
	public Boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

}
