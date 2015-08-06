package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wolai.platform.entity.Bill.PayType;

/**
 * 车牌信息
 * @author 陈琦
 */
@Entity
@Table(name="wo_unionpay_card_bound")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnionpayCardBound extends IdEntity{
	private static final long serialVersionUID = -396939135575320677L;

	/**
	 * 账号类型，目前支持银联信用卡
	 */
	@Enumerated(EnumType.STRING)
	private PayType accType = Bill.PayType.UNIONPAY;
	
	/**
	 * 账号编号银联卡号
	 */
	private String accNo;
	
	/**
	 * 喔来事务编号
	 */
	private String wolaiTradeNo;
	
	/**
	 * 卡有效期
	 */
	private String expired;
	
	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	

	public PayType getAccType() {
		return accType;
	}

	public void setAccType(PayType accType) {
		this.accType = accType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
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
