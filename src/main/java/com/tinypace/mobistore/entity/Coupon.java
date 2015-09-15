package com.tinypace.mobistore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 优惠券
 * @author xuxiang
 */
@Entity
@Table(name = "wo_coupon")
public class Coupon extends IdEntity {

    /**
	 * 
	 */
    private static final long serialVersionUID = 413926692190356010L;

    /**
     * 帐户类型枚举(UserType)
     */
    public static enum CouponType {
        /**
         * money(抵用券)
         */
        MONEY("MONEY","代金券"),

        /**
         * time(抵时券)
         */
        TIME("TIME","抵时券");

        private CouponType(String textVal,String textLable){
  			this.textVal=textVal;
  			this.textLable=textLable;
  		}
  		private String textVal;
  		private String textLable;
  		
  		public String getTextVal(){
  			return this.textVal;
  		}
  		
  		public void setTextVal(String textVal){
  			this.textVal = textVal;
  		}
		public String getTextLable() {
			return textLable;
		}

		public void setTextLable(String textLable) {
			this.textLable = textLable;
		}
        public String toString() {
            return textVal;
        }
    }
    
    /**
     * 优惠券状态
     */
    public static enum CouponStatus {
        /**
         * 初始状态
         */
    	INIT("INIT"),
        
        /**
         * 冻结
         */
        HOLD("HOLD"),

        /**
         * 已使用
         */
        USED("USED");

        private CouponStatus(String textVal) {
            this.textVal = textVal;
        }

        private String textVal;

        public String toString() {
            return textVal;
        }
    }

    @Column(name = "owner_id")
    private String ownerId;

    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private SysUser owner;

    /**
     * 绑定车牌号
     */
    private String carNo;

    /**
     * 优惠券类型：money(抵用券)，time(抵时券)
     */
    @Enumerated(EnumType.STRING)
    private CouponType type;
    
    /**
     * 优惠券状态
     */
    @Enumerated(EnumType.STRING)
    private CouponStatus status = CouponStatus.INIT;

    /**
     * 优惠金额
     */
    private Integer money;

    /**
     * 抵用时长
     */
    private Long time;

    /**
     * 可用时间：起始
     */
    private Date startDate;

    /**
     * 可用时间：结束
     */
    private Date endDate;

    /**
     * 来源
     */
    private String origin;

    /**
     * 备注
     */
    private String note;

    /**
     * 车牌Id
     */
    @Column(name = "license_id")
    private String licenseId;

    /**
     * 车牌
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id", insertable = false, updatable = false)
    private License license;

    /** 
     * 企业id
     */
    @Column(name="login_account_id")
    private String loginAccountId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "login_account_id", insertable = false, updatable = false)
    private SysLoginAccount loginAccount;
    
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public SysUser getOwner() {
        return owner;
    }

    public void setOwner(SysUser owner) {
        this.owner = owner;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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

    @Transient
    private String[] carNos;
    @Transient
    private String[] licenseCategories;

    @Transient
    public String[] getCarNos() {
        return carNos;
    }

    @Transient
    public void setCarNos(String[] carNos) {
        this.carNos = carNos;
    }

    @Transient
    public String[] getLicenseCategories() {
        return licenseCategories;
    }

    @Transient
    public void setLicenseCategories(String[] licenseCategories) {
        this.licenseCategories = licenseCategories;
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

	public CouponStatus getStatus() {
		return status;
	}

	public void setStatus(CouponStatus status) {
		this.status = status;
	}
}