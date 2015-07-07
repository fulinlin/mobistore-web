package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


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
@Table(name = "sys_r_user_role")
public class SysRUserRole extends idEntity {

    /**
     * 
     */
    private static final long serialVersionUID  =  5537941213364186868L;
    
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    
    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private SysUser user;
    
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

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

}
