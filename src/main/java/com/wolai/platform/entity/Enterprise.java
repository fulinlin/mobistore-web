package com.wolai.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 企业信息
 * @author xuxiang
 */
@Entity
@Table(name="wo_enterprise")
public class Enterprise extends idEntity {

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
    private String name;
    
    /**
     * 企业地址
     */
    private String  address;
    
    /**
     * 电话
     */
    private String  tel;
    
    /**
     * 组织机构证号
     */
    private String organizationCode;

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
    
    
}
