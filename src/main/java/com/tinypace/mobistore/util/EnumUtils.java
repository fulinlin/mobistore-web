/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tinypace.mobistore.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.tinypace.mobistore.entity.Area.AreaType;

/**
 * enum工具类
 * @author sevenshi
 */
public class EnumUtils {

    /**
     * 获取区域类型
     * @author sevenshi
     * @return
     */
    public static List<AreaType> getAreaTypes() {
        return Lists.newArrayList(AreaType.values());
    }

}
