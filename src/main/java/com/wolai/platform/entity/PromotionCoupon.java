package com.wolai.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wolai.platform.entity.Coupon.CouponType;

/**
 * 优惠活动可发优惠券
 * @author xuxiang
 *
 */
@Entity
@Table(name="wo_promotion_coupon")
public class PromotionCoupon extends IdEntity {

	/**
	 * UUID
	 */
	private static final long serialVersionUID = 6195513779540250705L;

	/**
	 * 最大可发数量
	 */
	private Integer maxNumber;

	/**
	 * 优惠券类型
	 */
	private CouponType type;
	
	/**
	 * 优惠券面额
	 */
	private BigDecimal money;
	
	/**
	 * 抵时券面额
	 */
	private Long time;
	
	/**
	 * 有效期：起始时间
	 */
	private Date startTime;
	
	/**
	 * 有效期：截止时间
	 */
	private Date endTime;
	
	/**
	 * 关联优惠活动
	 */
	@Column(name="promotion_id")
	private String promotionId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "promotion_id", insertable = false, updatable = false)
	private Promotion promotion;

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
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
