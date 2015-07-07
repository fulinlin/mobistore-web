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
 * 账单实体
 * 每笔消费记录
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_bill")
public class Bill extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4478071029703492078L;
	
	public static enum PayType{
  		/**
  		 * alipay(支付宝)
  		 */
  		ALIPAY("alipay"),
  		/**
  		 * unionpay(银联)
  		 */
  		UNIONPAY("unionpay"),
  		/**
  		 *remaint(余额)
  		 */
  		/*REMAINT("remaint"),*/
  		/**
  		 * weixin(微信)
  		 */
  		WEIXIN("weixin");
  		
  		
  		
  		private PayType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	@Column(name="license_plate_id")
	private String licensePlateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_plate_id", insertable = false, updatable = false)
	private LicensePlate licensePlate;
	
	/**
	 * 所属停车记录
	 */
	@Column(name="parking_record_id")
	private String parkingRecordId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_record_id", insertable = false, updatable = false)
	private ParkingRecord parkingRecord;
	
	/**
	 * 应付金额
	 */
	private BigDecimal Money;
	
	/**
	 * 是否已付款
	 */
	private Boolean isPaid;
	
	/**
	 * 优惠券信息
	 */
	@Column(name="coupon_id")
	private String couponId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
	private Coupon coupon;
	
	/**
	 * 付款方式：alipay(支付宝),unionpay(银联),weixin(微信),remaint(余额)
	 */
	@Enumerated(EnumType.STRING)
	private PayType paytype;
	
	/**
	 * 交易号
	 */
	private String tradeNo;
	
	/**
	 * 付款账号
	 */
	@Column(name="account_id")
	private String accountId;
	
	@Column(name="account_id")
	private SysAccount account;

	/**
	 * 是否已出票
	 * @return
	 */
	private
	
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getParkingRecordId() {
		return parkingRecordId;
	}

	public void setParkingRecordId(String parkingRecordId) {
		this.parkingRecordId = parkingRecordId;
	}

	public ParkingRecord getParkingRecord() {
		return parkingRecord;
	}

	public void setParkingRecord(ParkingRecord parkingRecord) {
		this.parkingRecord = parkingRecord;
	}

	public BigDecimal getMoney() {
		return Money;
	}

	public void setMoney(BigDecimal money) {
		Money = money;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public PayType getPaytype() {
		return paytype;
	}

	public void setPaytype(PayType paytype) {
		this.paytype = paytype;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public SysAccount getAccount() {
		return account;
	}

	public void setAccount(SysAccount account) {
		this.account = account;
	}

	public String getLicensePlateId() {
		return licensePlateId;
	}

	public void setLicensePlateId(String licensePlateId) {
		this.licensePlateId = licensePlateId;
	}

	public LicensePlate getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(LicensePlate licensePlate) {
		this.licensePlate = licensePlate;
	}
	
}
