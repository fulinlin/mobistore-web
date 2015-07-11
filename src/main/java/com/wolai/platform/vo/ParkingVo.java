package com.wolai.platform.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.wolai.platform.entity.Bill.PayType;
import com.wolai.platform.entity.Coupon.CouponType;

public class ParkingVo {

	private String carNo;
	private String carNoId;
	private String parkingLotId;
	private String entranceNo;
	private String exportNo;
	private String carPicPath;
	private Date driveInTime;
	private Date driveOutTime;
	private String parkStatus;
	private BigDecimal money;
	private BigDecimal paidMoney;
	private Boolean isPaid;
	
	private PayType paytype;
	private CouponType couponType;
	private BigDecimal couponMoney;
	private Long couponTime;
	
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarNoId() {
		return carNoId;
	}
	public void setCarNoId(String carNoId) {
		this.carNoId = carNoId;
	}
	public String getParkingLotId() {
		return parkingLotId;
	}
	public void setParkingLotId(String parkingLotId) {
		this.parkingLotId = parkingLotId;
	}
	public String getEntranceNo() {
		return entranceNo;
	}
	public void setEntranceNo(String entranceNo) {
		this.entranceNo = entranceNo;
	}
	public String getExportNo() {
		return exportNo;
	}
	public void setExportNo(String exportNo) {
		this.exportNo = exportNo;
	}
	public String getCarPicPath() {
		return carPicPath;
	}
	public void setCarPicPath(String carPicPath) {
		this.carPicPath = carPicPath;
	}
	public Date getDriveInTime() {
		return driveInTime;
	}
	public void setDriveInTime(Date driveInTime) {
		this.driveInTime = driveInTime;
	}
	public Date getDriveOutTime() {
		return driveOutTime;
	}
	public void setDriveOutTime(Date driveOutTime) {
		this.driveOutTime = driveOutTime;
	}
	public String getParkStatus() {
		return parkStatus;
	}
	public void setParkStatus(String parkStatus) {
		this.parkStatus = parkStatus;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getPaidMoney() {
		return paidMoney;
	}
	public void setPaidMoney(BigDecimal paidMoney) {
		this.paidMoney = paidMoney;
	}
	public PayType getPaytype() {
		return paytype;
	}
	public void setPaytype(PayType paytype) {
		this.paytype = paytype;
	}
	public CouponType getCouponType() {
		return couponType;
	}
	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}
	public BigDecimal getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}
	public Long getCouponTime() {
		return couponTime;
	}
	public void setCouponTime(Long couponTime) {
		this.couponTime = couponTime;
	}
	public Boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}
	
}
