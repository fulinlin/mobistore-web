package com.tinypace.mobistore.vo;

import java.util.Date;

public class ClientVo {
	private String id;
	
	private String mobile;
    private String password;
    private String nickName;
    private String email;
    private String authToken;
    private Date lastLoginTime;
    private Integer rewardPoints;
    private String clientPlatform;
    private String clientAgent;
    private String deviceToken;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public String getClientPlatform() {
		return clientPlatform;
	}
	public void setClientPlatform(String clientPlatform) {
		this.clientPlatform = clientPlatform;
	}
	public String getClientAgent() {
		return clientAgent;
	}
	public void setClientAgent(String clientAgent) {
		this.clientAgent = clientAgent;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
}
