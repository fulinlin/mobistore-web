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
 * 角色实体
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
@Entity
@Table(name = "sys_role")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysRole extends IdEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4490780384999462762L;
    
    /**
     * 角色编码
     */
    private String code;
    
    /**
     * 编码名称
     */
    private String name;
    
    /**
     * parent id
     */
    @Column(name = "parent_id")
    private String parentId;
    
    /**
     * parent
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private SysRole parent;

    /**
     * 排序
     */
    private Integer position = 0;
    
    /**
     * prefix
     */
    private String rolePrefix;
    
    /**
     * 备注
     */
    private String remark;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public SysRole getParent() {
        return parent;
    }

    public void setParent(SysRole parent) {
        this.parent = parent;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

}
