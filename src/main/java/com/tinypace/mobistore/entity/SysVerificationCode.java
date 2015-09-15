package com.tinypace.mobistore.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author xuxiang
 *
 */
@Entity
@Table(name="sys_verification_code")
public class SysVerificationCode extends IdEntity {
	private static final long serialVersionUID = 7949485328819207365L;
  	
    /**
     * 手机号码
     */
    private String mobile;
    
    /**
     * 验证码
     */
    private String code;
   
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 发送时间
     */
    private Date sendTime;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
 
}
