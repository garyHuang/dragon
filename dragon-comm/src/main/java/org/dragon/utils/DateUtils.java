package org.dragon.utils;

import java.util.Date;

public class DateUtils {
	
	public static Date parseYYYYMMDD(String dateStr){
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, "yyyy-MM-dd");
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Date parseYYYYMMDD0(String dateStr){
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr
					+ " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Date parseYYYYMMDD23(String dateStr){
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(dateStr
					+ " 23:59:59", "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e){
		}
		return null;
	}
}
