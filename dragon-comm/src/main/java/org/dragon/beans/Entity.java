package org.dragon.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Entity implements Serializable {
	
	private static final long serialVersionUID = 4129470023128587348L;
	
	private String tableName;
	
	private String pkName ;
	
	public Map<String, Object> getNameParam() {
		return nameParam;
	}
	private Object pkValue ; 
	
	public Object getPkValue() {
		return pkValue;
	}

	public void setPkValue(Object pkValue) {
		this.pkValue = pkValue;
	}
	protected Set<String> columns = new TreeSet<String>();
	
	protected Map<String,Object> nameParam = new HashMap<String, Object>(); 
	
	public void addColumn(String column , Object value){
		columns.add( column );
		nameParam.put(column , value);
	}
	
	public Set<String> getColumns() {
		return columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	@Override
	public String toString() {
		return "Entity [tableName=" + tableName + ", pkName=" + pkName
				+ ", pkValue=" + pkValue + ", columns=" + columns
				+ ", nameParam=" + nameParam + "]";
	}
	
}
