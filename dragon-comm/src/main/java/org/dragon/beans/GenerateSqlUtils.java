package org.dragon.beans;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.dragon.annotations.ID;
import org.dragon.annotations.Table;
import org.dragon.logs.LogManager;
import org.dragon.reflect.BeanUtils;
import org.dragon.reflect.FieldUtils;
/**
 * @author Gary 
 * */
public class GenerateSqlUtils implements Serializable{
	
	protected static final String SQL_SEPARATOR = ","; 

	private static final long serialVersionUID = -92006765081491278L;
	
	protected Entity entity = new Entity() ;
	
	protected List<Object>params = new Vector<Object>();
	
	public GenerateSqlUtils(Class<? extends Object> targetClass){
		entity.setTableName( getTableName(targetClass) ) ; 
		
		List<PropertyDescriptor> descriptors = BeanUtils.getDescriptors( targetClass );
		for(PropertyDescriptor descriptor : descriptors){
			String fieldName =  descriptor.getDisplayName();
			Field targetField = FieldUtils.getTargetField(targetClass, fieldName ) ;
			ID idAnnotation = targetField.getAnnotation(ID.class); 
			entity.addColumn( descriptor.getDisplayName() , null );
			if(idAnnotation==null){
				entity.addColumn(fieldName, null);
			}else{
				entity.setPkName( fieldName ) ;
				entity.addColumn(fieldName, null) ;
			}
		}
	}
	
	public GenerateSqlUtils(Object target){
		generateEntity( target );
	}
	/**
	 * @param target 
	 * */
	private void generateEntity(Object target) {
		Class<? extends Object> targetClass = target.getClass();
		List<PropertyDescriptor> descriptors = BeanUtils.getDescriptors( target.getClass() );
		for(PropertyDescriptor descriptor : descriptors){
			String fieldName = descriptor.getDisplayName() ; 
			Field targetField = FieldUtils.getTargetField(targetClass, fieldName) ;
			ID idAnnotation = targetField.getAnnotation(ID.class); 
			if(null == idAnnotation){
				entity.addColumn( fieldName , 
						FieldUtils.getValueObj(target, targetField ) );
			}else{
				entity.setPkName( fieldName );
				entity.setPkValue(	FieldUtils.getValueObj(target, targetField )  ) ; 
			}
		}
		String tableName = getTableName(targetClass);
		entity.setTableName( tableName );
	}

	private String getTableName(Class<? extends Object> targetClass) {
		Table tableAnnotation = targetClass.getAnnotation( Table.class );
		String tableName = targetClass.getName() ;
		if(null != tableAnnotation){
			if(!StringUtils.isBlank(tableAnnotation.name())){
				tableName = tableAnnotation.name();
			}
		}
		return tableName;
	}
	/**
	 * 获取更新语句
	 * */
	public String getUpdateSql(){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("UPDATE ").append(entity.getTableName()).append(" SET "); 
		List<String>sets = new Vector<String>();
		for(String column:entity.getColumns()){
			sets.add(String.format("%s = ?", column));
			params.add( entity.getNameParam().get( column ) );
		}
		sqlBuffer.append( StringUtils.join(sets , SQL_SEPARATOR ) ); 
		sqlBuffer.append(" WHERE ").append( entity.getPkName() ).append(" = ?");
		params.add( entity.getPkValue() );
		LogManager.log("sql:" + sqlBuffer);
		return sqlBuffer.toString() ;
	}
	
	public String getDeleteSql(){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("DELETE FROM ")
		.append(entity.getTableName()) 
		.append(" WHERE ")
		.append( entity.getPkName() )
		.append(" = ?") ; 
		LogManager.log("sql:" + sqlBuffer);
		return sqlBuffer.toString() ;
	}
	 
	public String getDeleteSql(Map<String,?> whereMap){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("DELETE FROM ")
		.append(entity.getTableName()) 
		.append(" WHERE " ) ;
		List<String>where = new Vector<String>();
		for(String column:whereMap.keySet() ){
			where.add(String.format("%s = ?", column));
			params.add( whereMap.get(column) );
		}
		sqlBuffer.append( StringUtils.join(where , SQL_SEPARATOR ) );
		LogManager.log("sql:" + sqlBuffer);
		return sqlBuffer.toString() ;
	}
	/** 
	 * @param setMap
	 * @param whereMap
	 * */
	public String getUpdateSql(Map<String,?> 
	   setMap , Map<String,?> whereMap){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("UPDATE ").append(entity.getTableName()).append(" SET "); 
		List<String>sets = new Vector<String>();
		for(String column:setMap.keySet() ){
			sets.add(String.format("%s = ?", column));
			params.add( setMap.get(column) );
		}
		sqlBuffer.append( StringUtils.join(sets , SQL_SEPARATOR ) );
		
		List<String>where = new Vector<String>();
		for(String column:whereMap.keySet() ){
			where.add(String.format("%s = ?", column));
			params.add( whereMap.get(column) );
		}
		sqlBuffer.append(" WHERE ") ; 
		sqlBuffer.append( StringUtils.join( where , SQL_SEPARATOR ) );
		LogManager.log("sql:" + sqlBuffer);
		return sqlBuffer.toString() ; 
	}
	
	public String getQueryForPk(){
		StringBuffer buffer = new StringBuffer() ;
		buffer.append("SELECT ");
		buffer.append( StringUtils.join( entity.getColumns() , SQL_SEPARATOR ) ); 
		
		buffer.append(" FROM ").append( entity.getTableName() );
		buffer.append(" WHERE ").append( entity.getPkName() )
		.append(" = ?") ;
		LogManager.log("sql:" + buffer);
		return buffer.toString() ;
	}
	
	public Entity getEntity() {
		return entity;
	}
	public List<Object> getParams() {
		return params;
	}
}
