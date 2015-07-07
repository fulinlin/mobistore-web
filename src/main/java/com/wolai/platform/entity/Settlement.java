package com.wolai.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 结算信息
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_settlement")
public class Settlement extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1409610006846500734L;

	/**
	 * 创建时间
	 */
	private Date createTime=new Date();
	
	/**
	 * 结算金额
	 */
	private BigDecimal Money;
	
	/**
	 * 是否成功付款
	 */
	private  Boolean isPaid=Boolean.FALSE;
	
	/**
	 * 付款时间
	 */
	private Date paidTime;
	
	/**
	 * 交易号
	 */
	private String tradeNo;
	
	/**
	 * 付款账号
	 */
	@Column(name="account_id")
	private String accountId;
	
	@Column(name="account_id")
	private SysAccount account;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getMoney() {
		return Money;
	}

	public void setMoney(BigDecimal money) {
		Money = money;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public SysAccount getAccount() {
		return account;
	}

	public void setAccount(SysAccount account) {
		this.account = account;
	}
}
