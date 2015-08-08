package com.wolai.platform.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.wolai.platform.bean.Page;
import com.wolai.platform.entity.IdEntity;
import com.wolai.platform.service.CommonService;
import com.wolai.platform.util.StringUtil;

@Service
public class CommonServiceImpl extends BaseServiceImpl implements CommonService {

	@Override
	public Object getByHQL(String hqlString, Object... values) {
		return getDao().getByHQL(hqlString, values);
	}

	@Override
	public List getListByHQL(String hqlString, Object... values) {
		return getDao().getListByHQL(hqlString, values);
	}

	@Override
	public Page findPageByFetchedHql(String hql, String countHql, int pageNo,
			int pageSize, Object... values) {
		return getDao().findPageByFetchedHql(hql,countHql,pageNo,pageSize,values);
	}

	@Override
	public Object get(Class clazz, Serializable id) {
		return getDao().get(clazz, id);
	}

	@Override
	public List findObjectBySql(String queryString, Class pojoClass) {
		return getDao().findObjectBySql(queryString,pojoClass);
	}

	@Override
	public Object getBySQL(String sqlString, Object... values) {
		return getDao().getBySQL(sqlString, values);
	}

	@Override
	public Page<?> findPage(DetachedCriteria dc, int start, int limit) {
		return getDao().findPage(dc, start, limit);
	}

	@Override
	public List<?> findAllByCriteria(DetachedCriteria dc) {
		return getDao().findAllByCriteria(dc);
	}

	@Override
	public List<Map> findListBySql(String queryString) {
		return  getDao().findListBySql(queryString);
	}

	@Override
	public Object getFirstByHql(String hql, Object... values) {
		return getDao().findFirstByHQL(hql, values);
	}

	@Override
	 public Object  FindFirstByCriteria(DetachedCriteria dc) {
		 return getDao().FindFirstByCriteria(dc);
	 }

	@Override
	public List<Map> findMapByHQL(String hqlString, Object... values) {
		return getDao().findMapByHQL(hqlString, values);
	}
	
    @Override
    public void delete(IdEntity entity) {
        entity.setIsDelete(Boolean.TRUE);
        getDao().saveOrUpdate(entity);
    }

    @Override
    public IdEntity saveOrUpdate(IdEntity entity) {
        if(StringUtil.isBlank(entity.getId())){
            entity.setId(null);
        }
        getDao().saveOrUpdate(entity);
        return  entity;
    }
    
    @Override
    public void saveOrUpdateAll(List<?> list) {
        getDao().saveOrUpdateAll(list);
    }

	@Override
	public Object getObjectByCriteria(DetachedCriteria dc) {
		return getDao().getByCriteria(dc);
	}
}
