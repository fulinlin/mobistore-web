package com.wolai.platform.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author xuxiang
 *
 */
@Entity
@Table(name="sys_customer")
public class SysCustomer extends idEntity {
    private static final long serialVersionUID = 7613949261966119827L;
    
  	/**
  	 *	客户类型枚举(UserType)
  	 */
  	public static enum CustomerType{
  		/**
  		 * individual(个人)
  		 */
  		INDIVIDUAL("individual"),
  		/**
  		 * enterpriseAdministrator(企业管理员)
  		 */
  		ENTERPRISE("enterpriseAdministrator");
  		
  		private CustomerType(String textVal){
  			this.textVal=textVal;
  		}
  		private String textVal;
  		
  		public String toString(){
  			return textVal;
  		}
  	}
  	
    /**
     * 名称
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String tel;
    /**
     * 是否企业
     */
    @Enumerated(EnumType.STRING)
    private CustomerType customerType=CustomerType.INDIVIDUAL;
    /**
     * 企业地址
     */
    private String address;
    /**
     * 企业联系人
     */
    private String contact;
    /**
     * 详细信息
     */
    private String info;
    /**
     * 企业供商编码
     */
    private String code;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
