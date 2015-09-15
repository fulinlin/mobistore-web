package com.tinypace.mobistore.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 出厂缴费状态查询vo
 * 
 * @author Ethan
 *
 */
public class PaycheckVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6046668999644719602L;

	/**
	 * 新利泊数据ID
	 */
	@NotBlank
	private String exNo;
	
	/**
	 * 出口编号
	 */
	private String exportNo;
	
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
	 * 出场时间
	 */
	@NotNull
	private Long exitTime;
	 
	/**
	 * 应付金额
	 */
	@NotNull
	private BigDecimal fee;
	
	/**
	 * 时间戳
	 */
	@NotNull
	private Long timestamp;

	public String getExNo() {
		return exNo;
	}

	public void setExNo(String exNo) {
		this.exNo = exNo;
	}

	public String getExportNo() {
		return exportNo;
	}

	public void setExportNo(String exportNo) {
		this.exportNo = exportNo;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Long enterTime) {
		this.enterTime = enterTime;
	}

	public Long getExitTime() {
		return exitTime;
	}

	public void setExitTime(Long exitTime) {
		this.exitTime = exitTime;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
