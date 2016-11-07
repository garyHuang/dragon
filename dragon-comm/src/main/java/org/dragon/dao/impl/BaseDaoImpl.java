package org.dragon.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dragon.beans.Entity;
import org.dragon.beans.GenerateSqlUtils;
import org.dragon.dao.BaseDao;
import org.dragon.dialects.Dialect;
import org.dragon.logs.LogManager;
import org.dragon.sqls.Condition;
import org.dragon.utils.ConvertUtils;
import org.dragon.utils.Pager;
import org.dragon.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SuppressWarnings({"unchecked" , "rawtypes"}) 
public abstract class BaseDaoImpl<T , PK> implements BaseDao<T, PK>{

	@Autowired
	protected JdbcTemplate jdbc ;
	
	@Autowired
	protected Dialect dialect;
	
	Class<T> persistentClass ;
	Class<PK> pkClass ;
	
	/**
	 * 默认构造函数
	 * */
	public BaseDaoImpl(){
		persistentClass = (Class<T>)getSuperClassGenricType(getClass(), 0);
		pkClass = (Class<PK>)getSuperClassGenricType(getClass(), 1);  
	}
	

	/**
	 * 插入方法
	 * @param t 当前插入的对象
	 * */
	public PK insert(T t){
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc.getDataSource());
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( t );
		Entity entity = sqlUtils.getEntity();
		insert.setTableName( entity.getTableName() );
		insert.setColumnNames( new ArrayList<String>(entity.getColumns()) );
		insert.setGeneratedKeyName( entity.getPkName() );
		Number idKey = insert.executeAndReturnKey( entity.getNameParam() ) ;
        PK convert = ConvertUtils.convert(idKey, pkClass); 
        org.dragon.reflect.FieldUtils.setValueObj(t , entity.getPkName() , convert ) ; 
        return convert ;
	}
	
	/**
	 * 修改一个对象的方法
	 * @param t 对象
	 * @param whereMap 条件
	 * */
	public Integer update(T t){
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( t );
		String updateSql = sqlUtils.getUpdateSql() ;
		return jdbc.update( updateSql , sqlUtils.getParams().toArray() );
	}
	/**
	 * 修改一个对象的方法
	 * @param sql 修改Sql
	 * @param whereMap 条件
	 * */
	public Integer update(String sql , Object...params){
		return jdbc.update( sql , params) ;
	}
	/**
	 * 修改一个对象的方法
	 * @param setMap 修改的字段
	 * @param whereMap 条件
	 * */
	public Integer update(Map<String,?> 
	   setMap , Map<String,?> whereMap){
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		String updateSql = sqlUtils.getUpdateSql(setMap , whereMap ) ; 
		return jdbc.update( updateSql , sqlUtils.getParams().toArray() );
	}
	
	/**
	 * 根据主键删除
	 * @param key 主键id
	 * */
	public Integer delete(PK key){
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		return jdbc.update( sqlUtils.getDeleteSql() , key );
	}
	/**
	 * 根据多个参数删除语句
	 * @param whereMap 根据where删除
	 * */
	public Integer delete(Map<String,?> whereMap){
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		return jdbc.update( sqlUtils.getDeleteSql(whereMap) , sqlUtils.getParams().toArray() );
	}
	
	/**
	 * 查询语句
	 * */
	public T get(PK pk) {
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		List<Map<String, Object>> results = jdbc.queryForList(sqlUtils.getQueryForPk() , pk ) ;  
		if(CollectionUtils.isEmpty(results)){
			return null ;
		}
		Map<String, Object> result = results.get( 0 ) ; 
		T t = null ; 
		try {
			t = setEntityVal(result);  
		} catch (Exception e) {
			LogManager.err( String.format("get %s", persistentClass.getSimpleName() ) , e ) ; 
		}
		return t ;
	}
	
	/**
	 * @desc 获取泛型类型
	 * @author Gary
	 * @param clazz 获取泛型的类
	 * @param index 当前获取第几个泛型
	 * */
	protected static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {  
        Type genType = clazz.getGenericSuperclass();  
        if (!(genType instanceof ParameterizedType)) {  
           return Object.class;  
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {  
                     return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
              return Object.class;
        }
        return (Class) params[index];
    }
	
	/**
	 * 分页查询
	 * */
	@Override
	public Pager queryPage(Pager pager) {
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		Entity entity = sqlUtils.getEntity( ); 
		String queryAllHead = StringUtils.join(entity.getColumns() , "," ) ;
		/*创建简单Sql*/
		StringBuffer buffer = new StringBuffer( String.format(" from %s" ,entity.getTableName() )  ) ;
		/*添加where语句*/
		List<Object> params = SqlUtils.appendSql(buffer, pager.getConditions()) ; 
		/*添加分页*/
		String limitSql = dialect.getLimitString("select " + queryAllHead +buffer.toString() , pager.getFristRow(), pager.getPageSize() ) ;
		
		/*jdbc查询*/
		List<Map<String, Object>> results = jdbc.queryForList( limitSql , params.toArray() ) ;  
		List<T> datas = new Vector<T>();
		for(Map<String, Object> result:results){
			T t = setEntityVal(result);
			datas.add( t ) ; 
		}
		/**
		 * 查询数量
		 * */
		Map<String, Object> countMap = jdbc.queryForMap("select count(*) c " + buffer.toString()  , params.toArray()); 
		if(!MapUtils.isEmpty(countMap)){
			pager.setTotal( ConvertUtils.toInt(countMap.get("c")) );
		}
		pager.setRows( datas ) ; 
		return pager ;
	}
	
	

	/**
	 * 根据SQL语句查询返回
	 * @param baseSql 自定义SQL
	 * @param pager 分页对象
	 * @return List<Map<String, Object>>
	 * */
	public List<Map<String, Object>> queryAll(String baseSql , Object...params) {
		StringBuffer sqlBuffer = new StringBuffer(baseSql);
		 /* 返回结果 */
		return jdbc.queryForList( sqlBuffer.toString() , params) ;
	}
	/**
	 * 根据SQL语句查询返回
	 * @param baseSql 自定义SQL
	 * @param pager 分页对象
	 * @return List<Map<String, Object>>
	 * */
	public List<Map<String, Object>> queryPage(String baseSql,Pager pager) {
		StringBuffer sqlBuffer = new StringBuffer(baseSql);
		/*添加where语句*/
		List<Object> params = SqlUtils.appendSql(sqlBuffer, pager.getConditions()) ; 
		/*添加分页*/
		String limitSql = dialect.getLimitString(sqlBuffer.toString() , pager.getFristRow(), pager.getPageSize() ) ;   
		/* 返回结果 */
		return jdbc.queryForList( limitSql , params.toArray() ) ;
	}
	/**
	 * 蒋值填入T实体中
	 * */
	private T setEntityVal(Map<String, Object> result){
		T t = null ;
		try {
			t = persistentClass.newInstance(); 
			Iterator<String> iterator = result.keySet().iterator();
			while(iterator.hasNext()){
				Object v = iterator.next();
				if(null == result.get(v)){
					iterator.remove();
				}
			}
			BeanUtilsBean2.getInstance().populate(t, result); 
		} catch (Exception e) {
			LogManager.err( "BaseDao.setEntityVal" , e );
		}
		return t;
	}
	
	
	@Override
	public List<T> query( Condition...conditions) {
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		Entity entity = sqlUtils.getEntity( ); 
		/*创建简单Sql*/
		StringBuffer buffer = new StringBuffer( String.format("select %s from %s" , StringUtils.join(entity.getColumns() , "," ) ,entity.getTableName() )  ) ;
		/*添加where语句*/
		List<Object> params = SqlUtils.appendSql(buffer, Arrays.asList(conditions)) ; 
		List<Map<String, Object>> results = jdbc.queryForList( buffer.toString() , params.toArray() ); 
		List<T> datas = new Vector<T>();
		results.forEach((result)->{
			T t = setEntityVal(result);
			datas.add( t ) ;
		});
		
		return datas;
	}
	
	@Override
	public T queryItem(Condition...conditions) {
		GenerateSqlUtils sqlUtils = new GenerateSqlUtils( persistentClass );
		Entity entity = sqlUtils.getEntity( ); 
		/*创建简单Sql*/
		StringBuffer buffer = new StringBuffer( String.format("select %s from %s" , StringUtils.join(entity.getColumns() , "," ) ,entity.getTableName() )  ) ;
		/*添加where语句*/
		List<Object> params = SqlUtils.appendSql(buffer, Arrays.asList(conditions)) ; 
		List<Map<String, Object>> results = jdbc.queryForList( buffer.toString() , params.toArray() ); 
		T t = null ;
		if(!CollectionUtils.isEmpty(results)){
			t = setEntityVal(results.get(0)) ; 
		}
		
		return  t ;
	}
	
}
