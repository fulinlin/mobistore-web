/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wolai.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

/**
 * 商圈Entity
 * @author sevenshi
 */
@Entity(name = "sys_zone")
public class Zone extends IdEntity {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area; // 区域
    @Column(name = "area_id")
    private String areaId; //  区域
    
    private String code; // 商圈编码
    private String name; // 商圈名称
    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
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
}