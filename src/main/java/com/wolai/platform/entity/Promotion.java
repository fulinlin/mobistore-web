package com.wolai.platform.entity;

import java.math.BigDecimal;
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
public class Promotion extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036551881557803261L;
	
	public static enum ExchangeCode{
		/**
  		 * 注册送
  		 */
  		REGISTER_PRESENT("REGISTER_PRESENT","注册送"),
  		/**
  		 * 积分兑换
  		 */
  		POINTS_EXCHANGE("POINTS_EXCHANGE","积分兑换"),
  		/**
  		 * 抢券
  		 */
  		SNAPUP_FREE("SNAPUP_FREE","抢券");
  		
  		
  		private ExchangeCode(String textVal,String textLable){
  			this.textVal=textVal;
  			this.textLable=textLable;
  		}
  		private String textVal;
  		private String textLable;
  		
  		public String getTextVal(){
  			return this.textVal;
  		}
  		
  		public void setTextVal(String textVal){
  			this.textVal = textVal;
  		}
		public String getTextLable() {
			return textLable;
		}

		public void setTextLable(String textLable) {
			this.textLable = textLable;
		}

		public String toString(){
  			return textVal;
  		}
	}

	public static enum PromotionLimitType{
		/**
  		 * 根据车牌的限制
  		 */
  		BYCARNO("BYCARNO","车牌限制"),
  		/**
  		 * 无限制
  		 */
  		ALL("ALL","无限制");
  		
  		
  		private PromotionLimitType(String textVal,String textLable){
  			this.textVal=textVal;
  			this.textLable=textLable;
  		}
  		private String textVal;
  		private String textLable;
  		
  		public String getTextVal(){
  			return this.textVal;
  		}
  		
  		public void setTextVal(String textVal){
  			this.textVal = textVal;
  		}
		public String getTextLable() {
			return textLable;
		}

		public void setTextLable(String textLable) {
			this.textLable = textLable;
		}

		public String toString(){
  			return textVal;
  		}
	}
	
	/**
	 * 类型编码
	 */
	@Enumerated(EnumType.STRING)
	private ExchangeCode code;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 图标
	 */
	private String picIcon;
	
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
	 * 是否推荐
	 */
	private Boolean recommended;
	
	/**
	 * Web URL
	 */
	private String url;
	
	/**
	 * 限制时间
	 */
	@Enumerated(EnumType.STRING)
	private PromotionLimitType limitType=PromotionLimitType.ALL;

	/**
	 * 获奖概率
	 */
	private BigDecimal probability;
	
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
	
	public ExchangeCode getCode() {
		return code;
	}

	public void setCode(ExchangeCode code) {
		this.code = code;
	}

	public Boolean getRecommended() {
		return recommended;
	}

	public void setRecommended(Boolean recommended) {
		this.recommended = recommended;
	}

	public String getPicIcon() {
		return picIcon;
	}

	public void setPicIcon(String picIcon) {
		this.picIcon = picIcon;
	}

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
