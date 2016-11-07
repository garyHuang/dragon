package org.dragon.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dragon.sqls.Condition;

public class Pager implements Serializable{
	
	private static final long serialVersionUID = 5721192875321132646L;

	private int pageId = 1;
	
	private int total ;
	
	private int totlePage ;
	
	private int pageSize = 15 ;
	
	private int fristRow ;
	
	private Object rows ;
	
	private boolean everyQueryCount = true ;
	
	private	List<Condition> conditions = new ArrayList<Condition>();
	
	public int getTotlePage() {
		return totlePage;
	}

	public void setTotlePage(int totlePage) {
		this.totlePage = totlePage;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void addConditions(Condition condition) {
		this.conditions.add( condition ) ;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

  

	public int getFristRow() {
		fristRow = (pageId-1) * pageSize ;
		return fristRow;
	}

	public void setFristRow(int fristRow) {
		this.fristRow = fristRow;
	}

	 
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}

	public boolean isEveryQueryCount() {
		return everyQueryCount;
	}

	public void setEveryQueryCount(boolean everyQueryCount) {
		this.everyQueryCount = everyQueryCount;
	}
}
