package com.wolai.platform.vo;

import java.math.BigDecimal;

public class RewardPointsVo {
	private String id;
	private BigDecimal balance;
	

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
