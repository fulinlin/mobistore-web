package com.wolai.platform.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 
 * 数据库操作dao类
 * 提供相关的数据库操作方法
 * @author xuxiang
 * @version $Id$
 * @since 	1.0
 */
@Repository("baseDao")
public class HibernateDao {

    /**
     * sessionFactory
     */
    @Autowired
    private SessionFactory sessionFactory;
	
    /**
     * 
     * @return session
     */
    public Session getSession() {
        //需要开启事物，才能得到CurrentSession
        return sessionFactory.getCurrentSession();
    }
    
    /* save操作 */
    /**
     * 	实例化实体
     *  保存相关数据至数据库。
     * @param t 实体
     * @since 	1.0
     */
    public void save(Object t) {
        this.getSession().save(t);
    }
    
    /**
     * 	批量实例化实体
     *  批量保存相关数据至数据库。
     * @param t 实体
     * @since 	1.0
     */
    public void saveAll(List<?> list) {
    	Session session = getSession();
		for ( int i=0; i<list.size(); i++ ) {  
			   Object ob = list.get(i);  
			   session.save(ob);  
			   if ( i % 30 == 0 ) { //30, same as the JDBC batch size
			      session.flush();
			      session.clear(); 
			   }
		}
    }
    
    /* update操作 */
    /**
     * 	更新实体
     *  更新数据库中实体相关数据。
     * @param t 实体
     * @since 	1.0
     */
    public void update(Object t) {
        this.getSession().merge(t);
    }
    
    /**
     * 	批量更新实体
     *  批量更新数据库中的实体信息。
     * @param t 实体
     * @since 	1.0
     */
    public void updateAll(List<?> list) {
    	Session session = getSession();
		for ( int i=0; i<list.size(); i++ ) {  
			   Object ob = list.get(i);  
			   session.update(ob);  
			   if ( i % 30 == 0 ) { //30, same as the JDBC batch size
			      session.flush();
			      session.clear(); 
			   }
		}
    }
    
    /* saveOrUpdate操作 */
    /**
     * 	更新或保存实体
     *  更新或保存相关数据至数据库。
     * @param t 实体
     * @since 	1.0
     */
    public void saveOrUpdate(Object t) {
        this.getSession().saveOrUpdate(t);
    }
    
    /**
     * 	批量更新或保存实体
     *  批量更新或保存相关数据至数据库。
     * @param t 实体
     * @since 	1.0
     */
	public void saveOrUpdateAll(List<?> list) {
		Session session = getSession();
		for ( int i=0; i<list.size(); i++ ) {  
			   Object ob = list.get(i);  
			   session.saveOrUpdate(ob);  
			   if ( i % 30 == 0 ) { //30, same as the JDBC batch size
			      session.flush();
			      session.clear(); 
			   }
			}
	}
    
	/* 删除操作 */
	/**
	 *  删除操作
     *  删除表中的t数据
     * @param t 实体
     * @since 	1.0
     */
    public void delete(Object t) {
        this.getSession().delete(t);
    }
     
    /**
     *  根据ID删除数据
     *  
     * @param clazz clazz
     * @param id 实体id
     * @return 是否删除成功
     * @since 	1.0
     */
    public boolean deleteById(Class<?> clazz, Serializable id) {
        Object t = get(clazz ,  id);
        if (t == null) {
            return false;
        }
        delete(t);
        return true;
    }
 
    /**
     *  根据ID删除数据
     *  
     * @param clazz clazz
     * @param id 实体id
     * @return 是否删除成功
     * @since 	1.0
     */
    public void deleteAll(List<Object> entities) {
        for (Object entity : entities) {
            this.getSession().delete(entity);
        }
    }
    
    /* 单个实体get操作 */
    /**
     *	根据id获取实体
     *  根据id获取实体的详细信息
     * @param clazz clazz
     * @param id 实体的id
     * @return 查询出来的实体
     * @since 	1.0
     */
    public Object get(Class<?> clazz, Serializable id) {
        return  this.getSession().get(clazz, id);
    }
    
    /**
     * 根据Criteria查找唯一实体
     * @param dc Criteria条件
     * @return 查询实体
     * @throws HibernateException - 如果查询结果>1
     * @since 	1.0
     */
    public Object  getByCriteria(DetachedCriteria dc) throws HibernateException {
    	Criteria  ca =  dc.getExecutableCriteria(getSession());
        return ca.uniqueResult();
    }
    
    /**
     * 根据HQL语句查找唯一实体
     * @param hqlString HQL语句
     * @param values 不定参数的Object数组
     * @return 查询实体
     * @throws NonUniqueResultException -如果查询结果>1
     * @since 	1.0
     */
    public Object getByHQL(String hqlString, Object... values)  throws NonUniqueResultException{
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.uniqueResult();
    }
    
    
  
}
