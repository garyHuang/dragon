package org.dragon.reflect;

import java.lang.reflect.Field;
import java.util.Map;
/**
 * @author GaryHuang
 * @date 2016��6��28�� 18:14:35
 * @mail 834865081@qq.com
 * */
public class FieldUtils {
	public static Field getTargetField(Class<?> targetClass, String fieldName) {
		Field field = null;
		try {
			if (targetClass == null) {
				return field;
			}

			if (Object.class.equals(targetClass)) {
				return field;
			}

			field = org.apache.commons.lang3.reflect.FieldUtils
					.getDeclaredField(targetClass, fieldName, true);
			if (field == null) {
				field = getTargetField(targetClass.getSuperclass(), fieldName);
			}
		} catch (Exception e) {
		}
		return field;
	}

	public static Object getValueObj(Object o, String field) {
		if (o == null)
			return null;
		Object value = null;
		if (o instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) o;
			value = map.get(field);
		} else {
			try {
				value = org.apache.commons.lang3.reflect.FieldUtils.readField(
						getTargetField(o.getClass(), field), o, true);
			} catch (Exception e) {
			}
		}
		return value;
	}
	
	public static Object getValueObj(Object o,Field field) {
		try {
			return org.apache.commons.lang3.reflect.FieldUtils.readField(
					field, o, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ; 
	}
	
	public static void setValueObj(Object o, String fieldName,Object value) {
		Field field = getTargetField(o.getClass() , fieldName);
		try {
			org.apache.commons.lang3.reflect.FieldUtils.writeField(field, o, value, true );
		} catch (Exception e) {
		}
	}
}
