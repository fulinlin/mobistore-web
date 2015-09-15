package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 *  
 * @author Ethan
 *
 */
@Entity
@Table(name="sys_api_key")
public class SysAPIKey extends IdEntity {

	/**
	 * uuid
	 */
	private static final long serialVersionUID = -4775260431590313982L;
	
	/**
	 * 
	 */
	private String token;
	
	/**
	 * 加密AESkey
	 */
	private String AESkey;
	
	/**
	 * 回传主机ip或域名
	 */
	@NotBlank
	private String url;
	
	/**
	 * 端口
	 */
	@NotBlank
	private String port;
	
	/**
	 * 根路径
	 */
	@NotBlank
	private String rootPath;
	
	/**
	 * 接入的停车场
	 */
	@NotBlank
	@Column(name="parking_lot_id")
	private String parkingLotId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private ParkingLot parkingLot;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAESkey() {
		return AESkey;
	}

	public void setAESkey(String aESkey) {
		AESkey = aESkey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
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

	
}
