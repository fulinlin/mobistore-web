package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 网站登录用户
 * @author xuxiang
 *
 */
@Entity
@Table(name="sys_login_account")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysLoginAccount extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6392945511347126577L;

	public static final Integer STATUS_NORMAL=1;
	public static final Integer STATUS_NOT_ACTIVE=-1;
	public static final Integer STATUS_DISABLE=-2;
	public static final Integer STATUS_DELETE=-3;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 状态
	 */
	private Integer status=SysLoginAccount.STATUS_NOT_ACTIVE;
	
	private String activeCode; // 激活码
	
	/**
	 * 是否为供应商
	 */
	private Boolean isSupplier=Boolean.FALSE;
	
	 /** 
     * 客户id
     */
    @Column(name="user_id")
    private String userId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private SysUser user;
    
	@Transient
	public boolean isEnable(){
		return status>0;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public Boolean getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
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
