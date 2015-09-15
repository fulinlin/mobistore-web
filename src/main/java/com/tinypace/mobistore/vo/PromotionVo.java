package com.tinypace.mobistore.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PromotionVo {
	private String id;
	private String title;
	private String picPath;
	private String picIcon;
	private String detail;
	private Date startTime;
	private Date endTime;
	private String code;
	private String url;
	
	private List<ExchangePlanVo> exchangePlanList = new ArrayList<ExchangePlanVo>();

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ExchangePlanVo> getExchangePlanList() {
		return exchangePlanList;
	}

	public void setExchangePlanList(List<ExchangePlanVo> exchangePlanList) {
		this.exchangePlanList = exchangePlanList;
	}
	public String getPicIcon() {
		return picIcon;
	}
	public void setPicIcon(String picIcon) {
		this.picIcon = picIcon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
