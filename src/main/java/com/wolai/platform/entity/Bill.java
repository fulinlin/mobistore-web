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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 账单实体
 * 每笔消费记录
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_bill")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bill extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4478071029703492078L;
	
	public static  enum PayType{
  		/**
  		 * alipay(支付宝)
  		 */
  		ALIPAY("ALIPAY","支付宝"),
  		
  		/**
  		 * unionpay(银联)
  		 */
  		UNIONPAY("UNIONPAY","银联"),

  		/**
  		 * CASH(现金)
  		 */
  		CASH("CASH","现金"),
  		
  		/**
  		 * weixin(微信)
  		 */
  		WEIXIN("WEIXIN","微信支付");
  		
  		/**
  		 *remaint(余额)
  		 */
  		/*REMAINT("REMAINT"),*/
  		
  		private PayType(String textVal,String textLable){
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

		public String toString(){
  			return textVal;
  		}
  	}
	
	/**
	 * 支付状态
	 */
	public static  enum PayStatus{
//		NOT_USE(-1000);
		FEATURE(-1), INIT(0),IN_PROGRESS(1),SUCCESSED(2);
		
		private PayStatus(Integer textVal){
  			this.textVal=textVal;
  		}
  		private Integer textVal;
  		
  		public Integer value(){
  			return textVal;
  		}
  		
  		public String toString(){
  			return textVal.toString();
  		}
	}
	
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
	 * 优惠券信息
	 */
	@Column(name="coupon_id")
	private String couponId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
	private Coupon coupon;
	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	/**
	 * 总额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 优惠后，实际需支付金额
	 */
	private BigDecimal payAmount;
	
	/**
	 * 支付成功返回的金额
	 */
	private BigDecimal tradeAmount;
	
	/**
	 * 付款方式：alipay(支付宝),unionpay(银联),weixin(微信),remaint(余额)
	 */
	@Enumerated(EnumType.STRING)
	private PayType paytype;
	
	/**
	 * 支付宝交易号
	 */
	private String tradeNo;
	
	/**
	 * 支付宝交易状态
	 */
	private String tradeStatus;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 支付请求时间
	 */
	private Date tradeSendTime;
	
	/**
	 * 支付完成时间
	 */
	private Date tradeResponseTime;
	
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
	private  Boolean isSendedInvoice = false;
	
	/**
	 * 支付状态
	 */
	@Enumerated(EnumType.ORDINAL)
	private PayStatus payStatus = PayStatus.INIT;
	
	/**
	 * 是否是后付费
	 * @return
	 */
	private  Boolean isPostPay = false;
	
	
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

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Date getTradeSendTime() {
		return tradeSendTime;
	}

	public void setTradeSendTime(Date tradeSendTime) {
		this.tradeSendTime = tradeSendTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsPostPay() {
		return isPostPay;
	}

	public void setIsPostPay(Boolean isPostPay) {
		this.isPostPay = isPostPay;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Date getTradeResponseTime() {
		return tradeResponseTime;
	}

	public void setTradeResponseTime(Date tradeResponseTime) {
		this.tradeResponseTime = tradeResponseTime;
	}
	
}
