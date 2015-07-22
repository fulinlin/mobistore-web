package com.wolai.platform.service;

import java.io.Serializable;
import java.util.List;

import com.wolai.platform.dao.HibernateDao;

public interface BaseService {
    
	public HibernateDao getDao();

    public boolean checkCodeUnique(Class<?> clazz, String codeName, String codeValue, String id);
    
    boolean checkCodeUnique(Class<?> clazz, String[] codeNames, String id , Object... codeValues);

    public Object getById(Class clazz, Serializable id);

    public List<Object> getListByHql(String hql, Object...values);
}
