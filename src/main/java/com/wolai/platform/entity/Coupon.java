package com.wolai.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 优惠券
 * @author xuxiang
 *
 */
@Entity
@Table(name="wo_coupon")
public class Coupon extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 413926692190356010L;
	
	/**
  	 *	帐户类型枚举(UserType)
  	 */
  	public static enum CouponType{
  		/**
  		 * money(抵用券)
  		 */
  		MONEY("money"),
  		
  		/**
  		 * time(抵时券)
  		 */
  		TIME("time");
  		
  		private CouponType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
	
  	@Column(name="owner_id")
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
  	 * 优惠金额
  	 */
  	private BigDecimal money;
  	
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
  	 * 是否已使用
  	 */
  	private Boolean isUsed = false;
  	
  	/**
  	 * 备注
  	 */
  	private String note;

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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
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

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

}
