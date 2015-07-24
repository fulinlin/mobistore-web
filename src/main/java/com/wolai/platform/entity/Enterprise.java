package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 企业信息
 * @author xuxiang
 */
@Entity
@Table(name="wo_enterprise")
public class Enterprise extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422799884916485425L;

	/**
     * 是否为供应商
     */
    private Boolean isSupplier=Boolean.FALSE;
    
    /**
     * 企业名称
     */
    @NotBlank(message="还未填写企业名称")
    private String name;
    
    /**
     * 企业地址
     */
    @Length(min=0, max=255)
    private String  address;
    
    /**
     * 电话
     */
    private String  tel;
    
    /**
     * 组织机构证号
     */
    private String organizationCode;
    
    /**
     * 余额
     */
    private long balance;

    /** 
     * 客户id
     */
    @NotNull(message="客户所属id不能为空！")
    @Column(name="user_id")
    private String userId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private SysUser user;
    
	public Boolean getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
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
