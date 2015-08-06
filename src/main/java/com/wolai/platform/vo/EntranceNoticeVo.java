package com.wolai.platform.vo;

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
	private String entranceNO;
	
	/**
	 * 车牌号
	 */
	@NotBlank
	private String carNO;
	
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
	private Long timestamp;

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getEntranceNO() {
		return entranceNO;
	}

	public void setEntranceNO(String entranceNO) {
		this.entranceNO = entranceNO;
	}

	public String getCarNO() {
		return carNO;
	}

	public void setCarNO(String carNO) {
		this.carNO = carNO;
	}

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

}
