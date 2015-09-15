package com.tinypace.mobistore.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tinypace.mobistore.entity.Enterprise;
import com.tinypace.mobistore.entity.SysLoginAccount;
import com.tinypace.mobistore.entity.SysRole;
import com.tinypace.mobistore.entity.SysUser;

/**
 * 
 * 
 * 用户登录信息
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
@SuppressWarnings("serial")
public class LoginInfo implements Serializable {

    /**
     * 登陆账户
     */
    private SysLoginAccount loginAccount;
    
    private SysUser user;
    
    private Enterprise enterprice;
    
    /**
     * 角色相关信息
     */
    private List<SysRole> roles = new ArrayList<SysRole>();
    

    public LoginInfo() {

    }

    public LoginInfo(SysLoginAccount loginAccount) {
        super();
        this.loginAccount = loginAccount;
    }

    public LoginInfo(SysLoginAccount loginAccount, List<SysRole> roles) {
        super();
        this.loginAccount = loginAccount;
        this.roles = roles;
    }


    public SysLoginAccount getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(SysLoginAccount loginAccount) {
        this.loginAccount = loginAccount;
    }


    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String toString(){
    	return getLoginAccount().getEmail();
    }

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public Enterprise getEnterprice() {
		return enterprice;
	}

	public void setEnterprice(Enterprise enterprice) {
		this.enterprice = enterprice;
	}
}
