package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 优惠活动实体
 * @author xuxiang
 *
 */
@Entity
@Table(name="wo_promotion")
public class Promotion extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036551881557803261L;

	public static enum PromotionLimitType{
		/**
  		 * 根据车牌的限制
  		 */
  		BYCARNO("bycarno"),
  		/**
  		 * 无限制
  		 */
  		ALL("all");
  		
  		
		private PromotionLimitType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
	}
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 主图片
	 */
	private String picPath;
	
	/**
	 * 活动详情
	 */
	@Lob
	private String detail;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 截至时间
	 */
	private Date endTime;
	
	/**
	 * 限制时间
	 */
	@Enumerated(EnumType.STRING)
	private PromotionLimitType limitType;

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

	public PromotionLimitType getLimitType() {
		return limitType;
	}

	public void setLimitType(PromotionLimitType limitType) {
		this.limitType = limitType;
	}
}
