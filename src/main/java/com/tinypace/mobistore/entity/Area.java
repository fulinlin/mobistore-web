package com.tinypace.mobistore.entity;

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
 * 区域Entity
 * @author sevenshi
 */
@Entity(name = "sys_area")
public class Area extends IdEntity {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Area parent; // 父级编号
    @Column(name = "parent_id")
    private String parentId; // 父级编号
    
    private String parentIds; // 所有父级编号
    private String code; // 区域编码
    private String name; // 区域名称
    private Integer sort; // 排序
    @Enumerated(EnumType.STRING)
    private AreaType type; // 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）

    @Transient
    private String typeLable;
    
    public Area() {
        super();
        this.sort = 30;
    }

    public enum AreaType {
        /**
         * 省直辖市
         */
        PROVINCE("PROVINCE", "省直辖市"),

        /**
         * 地市
         */
        CITY("CITY", "地市"),

        /**
         * 区县
         */
        COUNTY("COUNTY", "区县");

        private AreaType(String textVal, String textLable) {
            this.textVal = textVal;
            this.textLable = textLable;
        }

        private String textVal;
        private String textLable;

        public String getTextVal() {
            return this.textVal;
        }

        public void setTextVal(String textVal) {
            this.textVal = textVal;
        }

        public String getTextLable() {
            return textLable;
        }

        public void setTextLable(String textLable) {
            this.textLable = textLable;
        }

        public String toString() {
            return textVal;
        }
    }
    
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }

    @Length(min = 1, max = 2000)
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public AreaType getType() {
        return type;
    }

    public void setType(AreaType type) {
        this.type = type;
    }

    @Length(min = 0, max = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   

    @Transient
    public String getTypeLable() {
        return type.textLable;
    }

    @Override
    public String toString() {
        return name;
    }
}