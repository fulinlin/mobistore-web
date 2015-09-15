package com.tinypace.mobistore.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统相关配置
 * @author Ethan
 *
 */
@Entity
@Table(name="sys_config")
public class SysConfig extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6392034186335845431L;

	/**
	 * 是否审核车牌
	 */
	private Boolean validateLicensePlat=Boolean.FALSE;
	
	/**
	 * 是否允许出口处App手工缴费
	 */
	private Boolean canAppPayWhenSignout=Boolean.FALSE;
	
	/**
	 * 是否允许使用App给票号付费
	 */
	private Boolean canAppPayForTicket=Boolean.FALSE;

	public Boolean getValidateLicensePlat() {
		return validateLicensePlat;
	}

	public void setValidateLicensePlat(Boolean validateLicensePlat) {
		this.validateLicensePlat = validateLicensePlat;
	}

	public Boolean getCanAppPayWhenSignout() {
		return canAppPayWhenSignout;
	}

	public void setCanAppPayWhenSignout(Boolean canAppPayWhenSignout) {
		this.canAppPayWhenSignout = canAppPayWhenSignout;
	}

	public Boolean getCanAppPayForTicket() {
		return canAppPayForTicket;
	}

	public void setCanAppPayForTicket(Boolean canAppPayForTicket) {
		this.canAppPayForTicket = canAppPayForTicket;
	}
	
}
