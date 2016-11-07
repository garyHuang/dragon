package org.dragon.utils;

import java.util.ArrayList;
import java.util.List;

import org.dragon.sqls.Condition;

/**
 * 创建人：gary 创建时间：2016年9月20日 14:08:18
 * */
public class SqlUtils {

	public static List<Object> appendSql(StringBuffer sql,
			List<Condition> conditions) {
		List<Object> params = new ArrayList<Object>();

		conditions.forEach((condition) -> {
			if (sql.toString().toLowerCase().indexOf("where") == -1) {
				sql.append(" where ");
			} else {
				sql.append(" and ");
			}
			sql.append(condition.getField())
					.append(condition.getComparison().getComparison())
					.append("?");

			params.add(condition.getCompareValue());
		});

		return params;
	}

	public static String appendLikeValue(String value, Position position) {
		switch (position) {
		case PRE_END:
			return "%" + value + "%";
		case PRE:
			return "%" + value;
		case END:
			return value + "%";
		default:
			break;
		}
		return value;
	}

	public static enum Position {
		PRE_END, PRE, END
	}
}
