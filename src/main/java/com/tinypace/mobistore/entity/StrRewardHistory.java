package com.tinypace.mobistore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "str_view_history")
public class StrRewardHistory extends IdEntity {
	private static final long serialVersionUID = -1521461393720914818L;

	private Integer rewardPoints;
	private Date rewardTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	private StrClient client;
	
	@Column(name="client_id")
	private String clientId;
	
	private RewardSource source;
	private String sourceId;
	
	// 积分来源
	public static enum RewardSource{
		REGISTER("REGISTER"), PROMOTION("PROMOTION"), CONSUME("CONSUME");
		
		private RewardSource(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String value(){
  			return textVal;
  		}
  		
  		public String toString(){
  			return textVal;
  		}
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public Date getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}

	public StrClient getClient() {
		return client;
	}

	public void setClient(StrClient client) {
		this.client = client;
	}

	public RewardSource getSource() {
		return source;
	}

	public void setSource(RewardSource source) {
		this.source = source;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
