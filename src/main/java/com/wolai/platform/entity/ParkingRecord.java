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
 * 停车记录
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_parking_record")
public class ParkingRecord extends IdEntity {

	/**
	 * uuid
	 */
	private static final long serialVersionUID = 6012441198587216743L;
	
  	public static enum ParkStatus{
  		IN("IN"), PARKED("PARKED"), OUT("OUT");
  		
  		private ParkStatus(String code){
  			this.code = code;
  		}
  		private String code;
  		
  		public String Code(){
  			return code;
  		}
  		
  		public String toString() {
  			return code;
  		}
  	}

  	/**
  	 * 外部数据id
  	 */
  	private String exNo;
  	
	/**
	 * 车牌号
	 */
	private String carNo;
	
	@Column(name="car_no_id")
	private String carNoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_no_id", insertable = false, updatable = false)
	private License car;
	
	/**
	 * 停车场
	 */
	@Column(name="parking_lot_id")
	private String parkingLotId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id", insertable = false, updatable = false)
	private ParkingLot parkingLot;
	
	/**
	 * 入口编号
	 */
	private String entranceNo;
	
	/**
	 * 出口编号
	 */
	private String exportNo;
	
	/**
	 * 车头照
	 */
	private String carPicPath;
	
	/**
	 * 进入时刻
	 */
	private Date driveInTime;
	
	/**
	 * 出场时刻
	 */
	private Date driveOutTime;
	
	/**
	 * 在库状态
	 */
	@Enumerated(EnumType.STRING)
	private ParkStatus parkStatus;
	
	/**
	 * 应付金额
	 */
	private BigDecimal money;
	
	/**
	 * 实付金额
	 */
	private BigDecimal paidMoney;
	
	/**
	 * 是否已付款
	 */
	private Boolean isPaid = false;
	
	/**
	 * 支付完成时间
	 */
	private Date tradeSuccessTime;

	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
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

	public License getCar() {
		return car;
	}

	public void setCar(License car) {
		this.car = car;
	}

	public String getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(String parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
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

	public ParkStatus getParkStatus() {
		return parkStatus;
	}

	public void setParkStatus(ParkStatus parkStatus) {
		this.parkStatus = parkStatus;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Date getTradeSuccessTime() {
		return tradeSuccessTime;
	}

	public void setTradeSuccessTime(Date tradeSuccessTime) {
		this.tradeSuccessTime = tradeSuccessTime;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}
}
