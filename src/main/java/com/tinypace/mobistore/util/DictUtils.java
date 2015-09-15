package com.tinypace.mobistore.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tinypace.mobistore.entity.SysDict;
import com.tinypace.mobistore.service.DictService;

/**
 * 字典工具类
 * @author Ethan
 *
 */
public class DictUtils {

	private static DictService dictService = SpringContextHolder.getBean(DictService.class);
	
	/**
	 * 获取字典名称
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			//TODO
		}
		return defaultValue;
	}
	
	public static List<SysDict> getDictList(String type){
		if (StringUtils.isNotBlank(type)){
			return dictService.findAllList(type.trim());
		}
		return new ArrayList<SysDict>();
	}
}
