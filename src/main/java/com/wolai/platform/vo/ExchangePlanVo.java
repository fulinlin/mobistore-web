package com.wolai.platform.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.wolai.platform.entity.ExchangePlan.SourceType;
import com.wolai.platform.entity.ExchangePlan.TargetType;

public class ExchangePlanVo {
	private String code;
	private TargetType targetType;
	private SourceType sourceType;
	private BigDecimal price;
	private Integer number;
	private Date startTime;
	private Date endTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public TargetType getTargetType() {
		return targetType;
	}
	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
	public SourceType getSourceType() {
		return sourceType;
	}
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
