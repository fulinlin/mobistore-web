package com.wolai.platform.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
  	 *	消息类型
  	 */
  	public static enum MsgType{
  		/**
  		 * 通知
  		 */
  		NOTICE("NOTICE"),
  		/**
  		 * 消息
  		 */
  		MSG("MSG");
  		
  		private MsgType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
  	
	/**
	 * 消息类型
	 */
  	@Enumerated(EnumType.STRING)
	private MsgType type;

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
	
	/**
     * 是否已发送
     */
	
	private boolean published = false;
	
	
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

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}
}
