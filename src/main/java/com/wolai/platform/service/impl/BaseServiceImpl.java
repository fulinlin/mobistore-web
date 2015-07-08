package com.wolai.platform.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolai.platform.dao.HibernateDao;
import com.wolai.platform.service.BaseService;

/**
 * service实现类的基类
 * @author xuxiang
 * @version $Id$
 * @since
 * @see
 */
@Service
public class BaseServiceImpl implements BaseService{

    /**
     * 基本dao
     */
    @Autowired
    private HibernateDao dao;
    
    /**
     * 
     *获得dao
     * @return 返回dao
     */
    public HibernateDao getDao() {
        /*if (dao == null) {
            dao = SpringContextHolder.getBean("baseDao",HibernateDao.class);
        }*/
        return dao;
    }

    
    /**
     * 判断编码是否存在，确保编码的唯一性
     * @param clazz <font color='red'>*必要参数</font>
     * @param codeName 编码的字段名,如果为null，则默认为code
     * @param codeValue 编码的字段值 <font color='red'>*必要参数</font>
     * @param id 对象的Id，<font color='red'>如果编辑的时候此Id必须得有</font>
     * @return 如果返回true，则说明该编码已经存在，否则返回false，编码不存在
     */
    public boolean checkCodeUnique(Class<?> clazz, String codeName, String codeValue, String id) {
        if (StringUtils.isBlank(codeName)) {
            codeName = "code";
        }
        String hql = "select count(t.id) from " + clazz.getSimpleName() + " t where  t." + codeName.trim() + "=? and t.ifDelete=0";
        long c  = 0;
        if (id != null) {
            hql += " and t.id!=?";
            c = this.getDao().countByHql(hql, new Object[]{codeValue, id});
        } else {
            c = this.getDao().countByHql(hql, new Object[]{codeValue});
        }
        return c == 0 ? true : false;
    }
    
    /**
     * 
     * 根据id获得对象
     * @param clazz 类型
     * @param id 类型的id
     * @return 返回object
     */
    @SuppressWarnings("rawtypes")
    public Object getById(Class clazz, Serializable id) {
        return this.getDao().get(clazz, id);
    }
    
    /**
     * 
     * 根据hql获得list
     * @param hql hql语句
     * @param values 值
     * @return 返回list
     */
    public List<Object> getListByHql(String hql, Object...values) {
        return this.getDao().getListByHQL(hql, values);
    }
    
    public static int getSqlAfterSelectInsertPoint(String sql) {
        int selectIndex = sql.toLowerCase().indexOf("select");  
        int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");  
        return selectIndex + ((selectDistinctIndex == selectIndex) ? 15 : 6);
    }  
}
