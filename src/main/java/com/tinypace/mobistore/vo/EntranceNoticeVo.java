package com.tinypace.mobistore.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 入口
 * 
 * @author Ethan
 *
 */
public class EntranceNoticeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7825488667588322573L;

	/**
	 * 新利泊数据ID
	 */
	@NotBlank
	private String exNo;
	
	/**
	 * 入口编号
	 */
	private String entranceNo;
	
	/**
	 * 车牌号
	 */
	@NotBlank
	private String carNo;
	
	/**
	 * 入场时间
	 */
	@NotNull
	private Long enterTime;
	
	/**
	 * 图片地址
	 */
	@NotNull
	private String carPicUrl;
	
	/**
	 * 时间戳
	 */
	@NotNull
	private Long timestamp;


	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	public String getCarPicUrl() {
		return carPicUrl;
	}

	public void setCarPicUrl(String carPicUrl) {
		this.carPicUrl = carPicUrl;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getEntranceNo() {
		return entranceNo;
	}

	public void setEntranceNo(String entranceNo) {
		this.entranceNo = entranceNo;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

}
