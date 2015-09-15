package com.tinypace.mobistore.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.tinypace.mobistore.entity.SysDict;
import com.tinypace.mobistore.service.DictService;

@Service
public class DictServiceImpl extends CommonServiceImpl implements DictService {

	@Override
	public List<SysDict> findAllList(String code) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysDict.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.createAlias("parent", "parent", JoinType.LEFT_OUTER_JOIN);
		dc.add(Restrictions.eq("parent.code",code));
		return getDao().findAllByCriteria(dc);
	}

	@Override
	public List<SysDict> findListById(String id) {
		DetachedCriteria dc = DetachedCriteria.forClass(SysDict.class);
		dc.add(Restrictions.eq("isDelete", Boolean.FALSE));
		dc.createAlias("parent", "parent", JoinType.LEFT_OUTER_JOIN);
		dc.add(Restrictions.eq("parent.id",id));
		return getDao().findAllByCriteria(dc);
	}

}
