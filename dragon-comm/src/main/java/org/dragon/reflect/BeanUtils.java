package org.dragon.reflect;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.dragon.logs.LogManager;
import org.dragon.utils.ConvertUtils;

public class BeanUtils {
	
	public static List<PropertyDescriptor> getDescriptors(Class<?> clazz){
		List<PropertyDescriptor> properties = new Vector<PropertyDescriptor>();
		try {
			PropertyDescriptor[] propertyDescriptores = Introspector
					.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptores) {
				if (!"class".equals(property.getName())) {
					properties.add(property);
				}
			}
		} catch (Exception e) {
			LogManager.err("BeanUtils.getDescriptors" , e );;
		}
		return properties ;
	}
	
	public static Map<String,Object> beanToMap(Object vo){
		List<PropertyDescriptor> descriptors = getDescriptors(vo.getClass());
		Map<String,Object> voMap = new HashMap<String, Object>();
		try {
			for(PropertyDescriptor descriptor:descriptors){
				voMap.put(descriptor.getDisplayName() 
						, descriptor.getReadMethod().invoke( vo )) ;
			}
		} catch (Exception e){
		}
		return voMap ;
	}
	
	
	public static Map<String,String> beanToMapString(Object vo){
		List<PropertyDescriptor> descriptors = getDescriptors(vo.getClass());
		Map<String,String> voMap = new HashMap<String, String>();
		try {
			for(PropertyDescriptor descriptor:descriptors){
				Object tempValue = descriptor.getReadMethod().invoke( vo ) ; 
				String value = ConvertUtils.toStr( tempValue ) ; 
				
				voMap.put(descriptor.getDisplayName() 
						, value ) ;
			}
		} catch (Exception e){
		}
		return voMap ;
	}
}
