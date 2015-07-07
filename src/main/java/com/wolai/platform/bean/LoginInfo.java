package com.wolai.platform.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wolai.platform.entity.SysRole;
import com.wolai.platform.entity.SysUser;

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
     * 用户
     */
    private SysUser user;
    
    /**
     * 角色相关信息
     */
    private List<SysRole> roles = new ArrayList<SysRole>();
    

    public LoginInfo() {

    }

    public LoginInfo(SysUser user) {
        super();
        this.user = user;
    }

    public LoginInfo(SysUser user, List<SysRole> roles) {
        super();
        this.user = user;
        this.roles = roles;
    }


    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }


    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String toString(){
    	return getUser().getName();
    }
}
