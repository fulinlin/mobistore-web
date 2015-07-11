package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wolai.platform.entity.Promotion.PromotionLimitType;

/**
 * 用户反馈
 * @author xuxiang
 *
 */
@Entity
@Table(name="wo_feed_back")
public class FeedBack extends idEntity {

	/**
	 * uuid
	 */
	private static final long serialVersionUID = 2897656135113468418L;
	
	public static enum FeedbackType{
  		SUGGESTION("SUGGESTION"), // 建议
  		COMPLAIN("COMPLAIN"), // 投诉
  		APPEAL_FOR_LINCENSE("APPEAL_FOR_LINCENSE"); // 车牌申诉
  		
		private FeedbackType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
	}

	/**
	 * 反馈类型
	 */
	@Lob
	private FeedbackType type;
	
	/**
	 * 反馈内容
	 */
	@Lob
	private String content;
	
	/**
	 * 用户id
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	/**
	 * 是否处理
	 */
	private Boolean isDeal=Boolean.FALSE;
	
	/**
	 * 处理人
	 */
	private String dealUser;
	
	/**
	 * 处理时间
	 */
	private Date dealTime;
	
	/**
	 * 处理明细
	 */
	private String dealDetail;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealDetail() {
		return dealDetail;
	}

	public void setDealDetail(String dealDetail) {
		this.dealDetail = dealDetail;
	}

	public Boolean getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(Boolean isDeal) {
		this.isDeal = isDeal;
	}

	@Enumerated(EnumType.STRING)
	private FeedbackType limitType;
	public FeedbackType getType() {
		return type;
	}

	public void setType(FeedbackType type) {
		this.type = type;
	}
	
}
