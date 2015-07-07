package com.wolai.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="sys_user")
public class SysUser extends idEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6392945511347126577L;

	private String name; // 称呼
	
	private String email; // 邮箱
	
	private String phone; // 电话
	
	private String mobile; // 手机
	
	@JSONField(serialize = false)
	private String password;// 密码

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
}
