package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 消息发送记录
 * @author Ethan
 *
 */
@Entity
@Table(name="sys_message_send")
public class SysMessageSend extends idEntity {

	/**
	 * uuid
	 */
	private static final long serialVersionUID = -4619536980704689875L;

	@Column(name="userId")
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private SysUser user;
	
	@Column(name="message_id")
	private String messageId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", insertable = false, updatable = false)
	private SysMessage message;

	private Boolean isRead=Boolean.FALSE;
	
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public SysMessage getMessage() {
		return message;
	}

	public void setMessage(SysMessage message) {
		this.message = message;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
}
