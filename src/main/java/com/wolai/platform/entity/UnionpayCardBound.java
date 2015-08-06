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
	 * 绑定的业务类型
	 */
	public static  enum BoundType{
		POST_PAY("POST_PAY");
		
		private BoundType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String value(){
  			return textVal;
  		}
	}

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
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	/**
	 * 绑定类型，目前支持停车后付费
	 */
	@Enumerated(EnumType.STRING)
	private BoundType boundType = UnionpayCardBound.BoundType.POST_PAY;

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

	public BoundType getBoundType() {
		return boundType;
	}

	public void setBoundType(BoundType boundType) {
		this.boundType = boundType;
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
}