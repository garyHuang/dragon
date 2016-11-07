package org.dragon.sqls;

import java.io.Serializable;

public class Condition implements Serializable{

	private static final long serialVersionUID = -4959030950008242385L;
	
	public enum Comparison {
		Eq("="), NotEq("<>"), Lt("<"), Gt(">"), Le("<="), Ge(">="), Like(" LIKE ") , IN(" IN ") , 
		EMPTY( "" );
		
		private String comparison ;

		private Comparison(String comparison) {
			this.comparison = comparison;
		}
		public String getComparison() {
			return comparison;
		}
	}
	
	private String field;
	private Comparison comparison = Comparison.Eq;
	private Object compareValue;
	
	public Condition(String field, Comparison comparison,
			Object compareValue) {
		this.field = field ;
		this.comparison = comparison ;
		this.compareValue = compareValue ; 
	}
	
	public Condition(String field, Object compareValue){
		this.field = field;
		this.compareValue = compareValue;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Comparison getComparison() {
		return comparison;
	}

	public void setComparison(Comparison comparison) {
		this.comparison = comparison;
	}

	public Object getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(Object compareValue) {
		this.compareValue = compareValue;
	}
	
}
	
