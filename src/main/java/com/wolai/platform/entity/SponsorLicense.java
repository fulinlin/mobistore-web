package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 赞助车牌
 * @author sevenshi
 */
@Entity
@Table(name="wo_sponsor_license")
public class SponsorLicense extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422799884916485425L;

    /** 
     * 车牌
     */
    @Column(name="car_no")
	private String carNo;
	
	/** 
     * 车牌id
     */
    @Column(name="license_id")
    private String licenseId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "license_id", insertable = false, updatable = false)
    private  License license;
    
    /** 
     * 企业id
     */
    @Column(name="login_account_id")
    private String loginAccountId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "login_account_id", insertable = false, updatable = false)
    private SysLoginAccount loginAccount;
    
    /** 
     * 车牌分类id
     */
    @Column(name="license_category_id")
    private String licenseCategoryId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "license_category_id", insertable = false, updatable = false)
    private LicenseCategory licenseCategory;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(String loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public SysLoginAccount getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(SysLoginAccount loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLicenseCategoryId() {
        return licenseCategoryId;
    }

    public void setLicenseCategoryId(String licenseCategoryId) {
        this.licenseCategoryId = licenseCategoryId;
    }

    public LicenseCategory getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(LicenseCategory licenseCategory) {
        this.licenseCategory = licenseCategory;
    }
}
