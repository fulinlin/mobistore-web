package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 赞助车牌
 * @author sevenshi
 */
@Entity
@Table(name="wo_sponsor_license")
public class LicenseCategory extends IdEntity {

	private static final long serialVersionUID = -2422799884916485425L;

    /**
     * 分类名称
     */
    private String name;
    
    /** 
     * 客户id
     */
    @Column(name="login_account_id")
    private String loginAccountId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "login_account_id", insertable = false, updatable = false)
    private SysLoginAccount loginAccount;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getLoginAccountId() {
        return loginAccountId;
    }

    public void setLoginAccountId(String loginAccountId) {
        this.loginAccountId = loginAccountId;
    }

    public SysLoginAccount getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(SysLoginAccount loginAccount) {
        this.loginAccount = loginAccount;
    }
}
