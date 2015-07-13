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
	
	public static  enum PayType{
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
  		/*REMAINT("REMAINT"),*/
  		/**
  		 * weixin(微信)
  		 */
  		WEIXIN("WEIXIN");
  		
  		private PayType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
	
	/**
	 * 支付状态
	 */
	public static  enum PayStatus{
		INIT(0),FEATURE(-1),SUCCESSED(1);
		
		private PayStatus(Integer textVal){
  			this.textVal=textVal;
  		}
  		private Integer textVal;
  		
  		public String toString(){
  			return textVal.toString();
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
	private License licensePlate;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
	private SysAccount account;

	/**
	 * 是否已出票
	 * @return
	 */
	private  Boolean isSendedInvoice;
	
	/**
	 * 支付状态
	 */
	private PayStatus payStatus;
	
	
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

	public License getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(License licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Boolean getIsSendedInvoice() {
		return isSendedInvoice;
	}

	public void setIsSendedInvoice(Boolean isSendedInvoice) {
		this.isSendedInvoice = isSendedInvoice;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	
}
