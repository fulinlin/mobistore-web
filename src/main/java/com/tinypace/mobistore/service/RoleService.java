package com.tinypace.mobistore.service;

import java.util.List;

import com.tinypace.mobistore.entity.SysRole;

public interface RoleService extends BaseService {

	 /**
     * 根据userid返回所包含的基本角色
     * @param userId 用户id
     * @return 返回角色list
     */
    List<SysRole> getTopRoleByUserId(String userId);
}
