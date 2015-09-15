package com.tinypace.mobistore.entity;

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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

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
	
	/**
	 * 邮箱
	 */
	@Email(message="邮箱格式不正确")
	@NotBlank(message="邮箱不能为空")
	private String email;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 状态
	 */
	private Integer status=SysLoginAccount.STATUS_NOT_ACTIVE;
	
	/**
	 * 激活码
	 */
	private String activeCode;
	
	/**
	 * 是否为供应商
	 */
	private Boolean isAdmin=Boolean.FALSE;
	
	 /** 
     * 客户id
     */
    @Column(name="user_id")
    @NotBlank(message="用户信息不能为空")
    private String userId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private SysUser user;
    
    @NotBlank(message="企业信息不能为空")
    @Column(name="enterprise_id")
    private String enterpriseId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", insertable = false, updatable = false)
    private Enterprise enterprise;
    
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

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
