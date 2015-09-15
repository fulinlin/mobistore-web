package com.tinypace.mobistore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * 
 * 
 * 用户与角色关系
 * <详细描述>
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */

@Entity
@Table(name = "sys_r_login_account_role")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysRLoginAccountRole extends IdEntity {

    /**
     * 
     */
    private static final long serialVersionUID  =  5537941213364186868L;
    
    /**
     * 用户id
     */
    @Column(name = "login_account_id")
    private String loginAccountId;
    
    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_account_id", insertable = false, updatable = false)
    private SysLoginAccount loginAccount;
    
    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;
    
    /**
     * 角色
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private SysRole role;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
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
