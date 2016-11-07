package org.dragon.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils{
	
	public Map<String,Object> keyMap = new HashMap<String,Object>();
	
	public static MapUtils getMapUtils(){
		return new MapUtils() ; 
	}
	
	public MapUtils put(String key, Object value){
		keyMap.put(key, value);
		return this;
	}
	
	public Map<String,Object> build(){
		return keyMap ;
	}
}
