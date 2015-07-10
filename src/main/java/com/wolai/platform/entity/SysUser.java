package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author xuxiang
 *
 */
@Entity
@Table(name="sys_user",indexes={
		@Index(unique=true,columnList="auth_token")
})
public class SysUser extends idEntity {
    private static final long serialVersionUID = 7613949261966119827L;
    
  	/**
  	 *	客户类型枚举(UserType)
  	 */
  	public static enum UserType{
  		/**
  		 * individual(个人)
  		 */
  		INDIVIDUAL("individual"),
  		/**
  		 * enterpriseAdministrator(企业)
  		 */
  		ENTERPRISE("enterprise");
  		
  		private UserType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
  	
  	
  	public static enum PayType{
  		/**
  		 * perpaid(手动付费)
  		 */
  		PERPAID("perpaid"),
  		/**
  		 * perpaid(确认后付费)
  		 */
  		NOTICEPAID("noticeperpaid"),
  		/**
  		 * postpaid(后付费)
  		 */
  		POSTPAID("postpaid");
  		
  		private PayType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
  	
    /**
     * 名称
     */
    private String name;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机
     */
    private String mobile;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 电话
     */
    private String tel;
   
    /**
     * 是否企业
     */
    @Enumerated(EnumType.STRING)
    private UserType customerType=UserType.INDIVIDUAL;
    
    private PayType payType=PayType.PERPAID;

    @Column(name="auth_token")
    private String authToken;
    
    private Date lastLoginTime;
    
    /**
     * 主账户
     */
    @Column(name="main_account_id")
    private String mainAccountId;
    
    @ManyToOne
    @JoinColumn(name = "main_account_id", insertable = false, updatable = false)
    private SysAccount mainAccount;
    
	public UserType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(UserType customerType) {
		this.customerType = customerType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
