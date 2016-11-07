package org.dragon.dialects;

public class MySqlDialect implements Dialect {

	@Override
	public String getLimitString(String sql, int offset, int rows) {
		return String.format("%s LIMIT %d,%d", sql, offset, rows);
	}
}