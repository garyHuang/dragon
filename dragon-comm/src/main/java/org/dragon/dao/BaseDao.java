package org.dragon.dao;

import java.util.List;
import java.util.Map;

import org.dragon.sqls.Condition;
import org.dragon.utils.Pager;

public interface BaseDao<T, PK> {
	public PK insert(T t);

	public Integer update(T t);
	
	public Integer update(Map<String, ?> setMap, Map<String, ?> whereMap); 
	
	public Integer delete(Map<String,?> whereMap) ; 
	
	public Integer delete(PK key) ;  
	
	public T get(PK pk) ; 
	/**
	 * 根据SQL语句查询返回
	 * @param baseSql 自定义SQL
	 * @param pager 分页对象
	 * @return List<Map<String, Object>>
	 * */
	public List<Map<String, Object>> queryPage(String baseSql,Pager pager) ;
	/**
	 * 分页查询
	 * */
	public Pager queryPage(Pager pager) ; 
	
	public List<T> query(Condition...conditions) ; 
	
	public T queryItem(Condition...conditions) ; 
	
	
	public List<Map<String, Object>> queryAll(String baseSql , Object...params);
}