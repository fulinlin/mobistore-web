package com.tinypace.mobistore.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

@Entity
@Table(name="str_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StrClient extends IdEntity {
	private static final long serialVersionUID = 5857362761727915396L;
	private String mobile;
    private String password;
    private String nickName;
    private String email;
    private String authToken;
    private Date lastLoginTime;
    private Integer rewardPoints = 0;

    // 当前登录的设备类型
    private PlatformType clientPlatform;
    // 当前登录的浏览器类型
    private AgentType clientAgent;
    
    // 友盟设备Token
    private String deviceToken;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
	@Where(clause = "is_delete = 0 and is_disable = 0")
	private Set<StrRecipient> addressSet = new HashSet<StrRecipient>(0);
    
	public static enum PlatformType{
		IOS("IOS"), ANDROID("ANDROID"),WINDOWS("WINDOWS");
		
		private PlatformType(String textVal){
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
	
	// 支付状态
	public static enum AgentType{
		WEBVIEW("WEBVIEW"), BROWSER("BROWSER");
		
		private AgentType(String textVal){
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
    
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public PlatformType getClientPlatform() {
		return clientPlatform;
	}
	public void setClientPlatform(PlatformType clientPlatform) {
		this.clientPlatform = clientPlatform;
	}
	public AgentType getClientAgent() {
		return clientAgent;
	}
	public void setClientAgent(AgentType clientAgent) {
		this.clientAgent = clientAgent;
	}
	public Set<StrRecipient> getAddressSet() {
		return addressSet;
	}
	public void setAddressSet(Set<StrRecipient> addressSet) {
		this.addressSet = addressSet;
	}
}
