package org.dragon.dialects;

public interface Dialect {
	String getLimitString(String sql, int offset, int rows);
}