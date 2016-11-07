package org.dragon.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class ConvertUtils {

	public final static String NO_BUG = StringUtils.join(new String[] {
			"                   _ooOoo_",
			"                  o8888888o",
			"                  88\" . \"88",
			"                  (| -_- |)",
			"                  O\\  =  /O",
			"               ____/`---'\\____",
			"             .'  \\\\|     |//  `.",
			"            /  \\\\|||  :  |||//  \\",
			"           /  _||||| -:- |||||-  \\",
			"           |   | \\\\\\  -  /// |   |",
			"           | \\_|  ''\\---/''  |   |",
			"           \\  .-\\__  `-`  ___/-. /",
			"         ___`. .'  /--.--\\  `. . __",
			"      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".",
			"     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |",

			"     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /",
			"======`-.____`-.___\\_____/___.-`____.-'======",
			"                   `=---='" , " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  "
			, " //         佛祖保佑       永无BUG     永不修改                      // "}, "\n");

	public final static Integer INT_ZERO = 0;
	public final static Byte BYTE_ZERO = INT_ZERO.byteValue();
	public final static BigDecimal BIG_DECIMAL = new BigDecimal(0);
	public final static String EMPTY_STRING = "";
	public final static Boolean FALSE = false;
	public final static String TRUE = "true";

	public static boolean toBoolean(Object value) {
		return toBoolean(value, FALSE);
	}

	public static boolean toBoolean(Object value, boolean defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		String str = trimString(value);
		if (TRUE.equals(str)) {
			return true;
		}
		return defaultValue;
	}

	public static byte toByte(Object obj) {
		return toByte(obj, BYTE_ZERO);
	}

	public static byte toByte(Object obj, byte defaultValue) {
		if (obj == null) {
			return defaultValue;
		}

		if (obj instanceof Number) {
			Number number = (Number) obj;
			return number.byteValue();
		}
		String str = trimString(obj);
		return NumberUtils.toByte(str, defaultValue);
	}

	public static double toDouble(Object obj) {
		return toDouble(obj, INT_ZERO);
	}

	public static double toDouble(Object obj, double defaultValue) {
		if (obj == null) {
			return defaultValue;
		}

		if (obj instanceof Number) {
			Number number = (Number) obj;
			return number.doubleValue();
		}
		String str = trimString(obj);
		return NumberUtils.toDouble(str, defaultValue);
	}

	public static float toFloat(Object obj) {
		return toFloat(obj, INT_ZERO);
	}

	public static float toFloat(Object obj, float defaultValue) {
		if (obj == null) {
			return defaultValue;
		}

		if (obj instanceof Number) {
			Number number = (Number) obj;
			return number.floatValue();
		}
		String value = trimString(obj);
		return NumberUtils.toFloat(value, defaultValue);
	}

	public static int toInt(Object value) {
		return toInt(value, INT_ZERO);
	}

	public static int toInt(Object value, int defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		String str = trimString(value);
		return NumberUtils.toInt(str, defaultValue);
	}

	public static long toLong(Object value) {
		return toLong(value, INT_ZERO);
	}

	public static long toLong(Object value, long defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		String str = trimString(value);
		return NumberUtils.toLong(str, defaultValue);
	}

	public static short toShort(Object obj) {
		return toShort(obj, (byte) 0);
	}

	public static short toShort(Object obj, short defaultValue) {
		if (obj == null) {
			return defaultValue;
		}

		if (obj instanceof Number) {
			Number number = (Number) obj;
			return number.shortValue();
		}
		String value = trimString(obj);
		return NumberUtils.toShort(value, defaultValue);
	}

	public static BigDecimal toBigDecimal(Object value) {
		return toBigDecimal(value, BIG_DECIMAL);
	}

	public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof BigDecimal) {
			BigDecimal decimal = (BigDecimal) value;
			return decimal;
		}
		return new BigDecimal(toDouble(value));
	}

	public static String trimString(Object value) {
		if (null == value) {
			return EMPTY_STRING;
		}
		String str = value.toString().trim();
		if (StringUtils.isBlank(str)) {
			return EMPTY_STRING;
		}
		return str;
	}

	public static String toStr(Object val) {
		if (null == val) {
			return "";
		}
		if (val instanceof BigDecimal) {
			return DecimalFormatUtils.numToString((BigDecimal) val);
		} else if (val instanceof Double) {
			return DecimalFormatUtils.numToString((Double) val);
		} else if (val instanceof Float) {
			return DecimalFormatUtils.numToString((Float) val);
		} else if (val instanceof Number) {
			return val.toString();
		} else if (val instanceof Date) {
			Date date = (Date) val;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String pattern = "yyyy-MM-dd HH:mm:ss";

			return DateFormatUtils.format(calendar, pattern);
		}
		return StringUtils.trim(val.toString());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T convert(Object obj, Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		if (clazz.isEnum()) {
			java.lang.reflect.Field[] fields = clazz.getFields();
			int tempInt = toInt(obj);
			if (fields.length > tempInt) {
				try {
					return (T) Enum.valueOf((Class) clazz,
							fields[tempInt].getName());
				} catch (Exception e) {
				}
			}
		}
		if (obj.getClass().equals(clazz)) {
			return (T) obj;
		}
		if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
			return (T) new Integer(toInt(obj));
		} else if (String.class.equals(clazz)) {
			return (T) trimString(obj);
		} else if (float.class.equals(clazz) || Float.class.equals(clazz)) {
			return (T) new Float(toFloat(obj));
		} else if (double.class.equals(clazz) || Double.class.equals(clazz)) {
			return (T) new Double(toDouble(obj));
		} else if (byte.class.equals(clazz) || Byte.class.equals(clazz)) {
			return (T) new Byte(toByte(obj));
		} else if (short.class.equals(clazz) || Short.class.equals(clazz)) {
			return (T) new Short(toShort(obj));
		} else if (long.class.equals(clazz) || Long.class.equals(clazz)) {
			return (T) new Long(toLong(obj));
		} else if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
			return (T) new Boolean(toBoolean(obj));
		} else if (BigDecimal.class.equals(clazz)) {
			return (T) toBigDecimal(obj);
		} else {
			if (java.util.Date.class.equals(clazz)
					|| java.sql.Date.class.equals(clazz)
					|| java.sql.Timestamp.class.equals(clazz)) {
				try {
					Date date = DateUtils.parseDate(obj.toString(),
							"yyyy-MM-dd", "yyyyMMdd", "yyyy年MM月dd日",
							"yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss",
							"yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日 HH时mm分ss秒");
					if (java.sql.Date.class.equals(clazz)) {
						return (T) new java.sql.Date(date.getTime());
					} else if (java.sql.Timestamp.class.equals(clazz)) {
						return (T) new java.sql.Timestamp(date.getTime());
					}
					return (T) date;
				} catch (Exception e) {
				}
			}
		}
		return (T) obj;
	}
	
}
