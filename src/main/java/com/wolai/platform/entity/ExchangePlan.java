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
 * 兑换计划
 * @author Ethan
 *
 */
@Entity
@ Table(name="wo_exchange_plan")
public class ExchangePlan extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 780639903449593380L;

	/**
	 * 交换类型
	 * @author Ethan
	 *
	 */
	public static enum TargetType{
		
		/**
  		 * 代金券
  		 */
  		TO_COUPON_MONEY("TO_COUPON_MONEY"),
  		
  		/**
  		 * 礼品券
  		 */
  		TO_GOODS("TO_GOODS");
  		
  		private TargetType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
	}
	
	/**
	 * 货币类型
	 * @author Ethan
	 *
	 */
	public static enum SourceType{
		/**
  		 * 积分
  		 */
  		REWRRD_POINTS("REWRRD_POINTS","积分"),
  		
  		/**
  		 * 现金
  		 */
  		MONEY("MONEY","现金"),
  		
  		/**
  		 * 无条件
  		 */
  		NONE("NONE","无条件");
  		
  		private SourceType(String textVal,String textLable){
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
	 * 兑换类型
	 */
	@Enumerated(EnumType.STRING)
	private TargetType targetType;
	
	
	/**
	 * 兑换介质
	 */
	@Enumerated(EnumType.STRING)
	private SourceType sourceType;
	
	/**
	 * 单价
	 */
	private Integer price;
	
	/**
	 * 总数量
	 */
	private Integer number;
	
	/**
	 * 面值，如时长或抵金
	 */
	private Integer faceValue;
	
	/**
	 * 活动有效期：开始时间
	 */
	private Date startTime;
	
	/**
	 * 活动有效期：结束时间
	 */
	private Date endTime;
	
	/**
	 * 限制次数
	 */
	private Integer timesLimit;
	
	/**
	 * 获奖概率
	 */
	private BigDecimal probability;
	
	/**
	 * 营销活动
	 */
	@Column(name="promotion_id")
	private String promotionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", insertable = false, updatable = false)
	private Promotion promotion;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
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

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public Integer getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(Integer faceValue) {
		this.faceValue = faceValue;
	}

	public Integer getTimesLimit() {
		return timesLimit;
	}

	public void setTimesLimit(Integer timesLimit) {
		this.timesLimit = timesLimit;
	}

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}
	
}
