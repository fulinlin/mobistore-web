package com.wolai.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author xuxiang
 * 计费账户
 */
@Entity
@Table(name="sys_account")
public class SysAccount extends IdEntity{
	private static final long serialVersionUID = 1659327323804354816L;
	
	/**
  	 *	帐户类型枚举(UserType)
  	 */
  	public static enum AccountType{
  		/**
  		 * alipay(支付宝)
  		 */
  		ALIPAY("ALIPAY"),
  		/**
  		 * unionpay(银联)
  		 */
  		UNIONPAY("UNIONPAY"),
  		/**
  		 *remaint(余额)
  		 */
  		/*REMAINT("remaint"),*/
  		/**
  		 * weixin(微信)
  		 */
  		WEIXIN("WEIXIN");
  		
  		
  		
  		private AccountType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
  	
	/**
	 *付费帐户类型: alipay(支付宝),unionpay(银联),weixin(微信),remaint(余额)
	 */
	@Enumerated(EnumType.STRING)
	private AccountType accountType=AccountType.UNIONPAY;
	
	/**
	 * 帐户名
	 */
	private String accountNo;
	
	/**
	 * 授权码
	 */
	private String signKey;
	
	/**
	 * cvs2
	 */
	private String cvs2;
	
	// TODO 待加
	
	/**
	 * 余额
	 */
	private BigDecimal remaint;
	
	@Column(name="user_id")
	private String  userId;
	/**
	 * 付费帐户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getRemaint() {
		return remaint;
	}

	public void setRemaint(BigDecimal remaint) {
		this.remaint = remaint;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getCvs2() {
		return cvs2;
	}

	public void setCvs2(String cvs2) {
		this.cvs2 = cvs2;
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
}