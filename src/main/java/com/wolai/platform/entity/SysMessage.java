package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统消息
 * @author xuxiang
 *
 */
@Entity
@Table(name="sys_message")
public class SysMessage extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5462783088930984388L;

	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
