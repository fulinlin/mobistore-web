package com.wolai.platform.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * 积分变动记录
 * @author Ethan
 *
 */
@Entity
@Table(name="wo_rewardPoints_record")
public class RewardPointsRecord extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 307315644736388795L;
	
	public static enum RewardPointsChangeType{
  		/**
  		 * 停车获得
  		 */
  		PARKING("PARKING"),
  		
  		/**
  		 * 市场活动赠送
  		 */
  		MARKING("MARKING"),
  		
  		/**
  		 * 兑换优惠券
  		 */
  		CONSUM("CONSUM");
  		
  		
  		private RewardPointsChangeType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
	
	/**
	 * 积分变动类型
	 */
	@Enumerated(EnumType.STRING)
	private RewardPointsChangeType type;
	
	/**
	 * 变动数量
	 */
	private BigDecimal number;
	
	/**
	 * 关联的积分账户
	 */
	@Column(name="reward_points_id")
	private String rewardPointsId;
	
	/**
	 * 关联的积分账户
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reward_points_id",insertable=false,updatable=false)
	private RewardPoints rewardPoints;
	
	
	/**
	 * 关联记录
	 */
	@Column(name="relation_id")
	private String relationId;
	
	@Any(metaColumn=@Column(name="type"))
    @AnyMetaDef(idType="string", metaType="string",
        metaValues={
         @MetaValue(targetEntity=ParkingRecord.class, value="parking"),
         @MetaValue(targetEntity=Coupon.class, value="consum"),
    })
    @JoinColumn(name="relation_id",insertable=false,updatable=false)
	private Object relation;

	/**
	 * 所属用户
	 */
	@Column(name="user_id")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	public RewardPointsChangeType getType() {
		return type;
	}

	public void setType(RewardPointsChangeType type) {
		this.type = type;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Object getRelation() {
		return relation;
	}

	public void setRelation(Object relation) {
		this.relation = relation;
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

	public String getRewardPointsId() {
		return rewardPointsId;
	}

	public void setRewardPointsId(String rewardPointsId) {
		this.rewardPointsId = rewardPointsId;
	}

	public RewardPoints getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(RewardPoints rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
}
