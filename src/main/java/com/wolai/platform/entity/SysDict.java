package com.wolai.platform.entity;

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
 * 数据字典实体
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
@Entity
@Table(name = "sys_dict")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysDict extends idEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3244534542389193186L;

    /**
     * 字典名称
     */
    private String name;
    
    /**
     * 字典编号
     */
    private String code;
    
    /**
     * 字典值
     */
    private String value;
    
    /**
     * 父字典
     */
    @Column(name = "parent_id")
    private String parentId;
    
    /**
     * 父字典
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private SysDict parent;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public SysDict getParent() {
        return parent;
    }

    public void setParent(SysDict parent) {
        this.parent = parent;
    }
}
