package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 兑换历史
 *
 */
@Entity
@ Table(name="wo_exchange_history")
public class ExchangeHistory extends IdEntity {
	
	private static final long serialVersionUID = 2228642104717478744L;

	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	@Column(name="exchange_plan_id")
	private String exchangePlanId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_plan_id", insertable = false, updatable = false)
	private ExchangePlan exchangePlan;
	
	/**
	 * 次数
	 */
	private Integer times;

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

	public String getExchangePlanId() {
		return exchangePlanId;
	}

	public void setExchangePlanId(String exchangePlanId) {
		this.exchangePlanId = exchangePlanId;
	}

	public ExchangePlan getExchangePlan() {
		return exchangePlan;
	}

	public void setExchangePlan(ExchangePlan exchangePlan) {
		this.exchangePlan = exchangePlan;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

}
