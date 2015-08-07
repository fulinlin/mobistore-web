package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="wo_temp_parking_record",indexes={@Index(columnList="car_no")})
public class TempParkingRecord extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6460144911180382929L;

	/**
  	 * 外部数据id
  	 */
  	private String exNo;
  	
	/**
	 * 车牌号
	 */
  	@Column(name="car_no")
	private String carNo;
	
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
	 * 车头照
	 */
	private String carPicPath;
	
	/**
	 * 进入时刻
	 */
	private Date driveInTime;

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
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
}
