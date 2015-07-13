package com.wolai.platform.service;

import java.util.List;

import com.wolai.platform.entity.SysDict;

/**
 * 字典服务类
 * @author Ethan
 *
 */
public interface DictService extends CommonService {

	/**
	 * 
	 * @return
	 */
	public List<SysDict> findAllList(String code);
	
	public List<SysDict> findListById(String id);
	
}
