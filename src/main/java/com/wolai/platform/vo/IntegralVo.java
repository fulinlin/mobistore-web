package com.wolai.platform.vo;

import java.math.BigDecimal;

public class IntegralVo {

	private BigDecimal balance;
	
	private Boolean isUsed;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
	
}
