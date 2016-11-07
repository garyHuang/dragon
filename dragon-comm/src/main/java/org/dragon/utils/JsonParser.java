package org.dragon.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonParser {
	/**
	 * 将JSON转换成为List<Map<String, Object>>格式
	 *  @param json json字符串
	 *  @return List<Map<String, Object>>返回数据
	 * */
	public static List<Map<String, Object>> parseToList(String json) {
		JSONArray jsonArray = null;
		try {
			jsonArray = JSONArray.parseArray( json );
		} catch (Exception e) {
		}
		return parseToMaps(jsonArray);
	}
	
	/**
	 * 将JSON转换成为Map<String, Object>格式
	 *  @param json json字符串
	 *  @return Map<String, Object>返回数据
	 * */
	public static Map<String, Object> parseToObj(String json) {
		JSONObject jSONObject = null;
		try {
			jSONObject = JSONObject.parseObject( json );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parseToMap(jSONObject);
	}

	protected static Map<String, Object> parseToMap(JSONObject obj) {
		Map<String, Object> map = new HashMap<String,Object>();
		if(null == obj){
			return map ;
		}
		try {
			obj.forEach((key , value) -> {
				if (value instanceof JSONArray) {
					map.put(key, parseToMaps((JSONArray) value));
				} else if (value instanceof JSONObject) {
					map.put(key, parseToMap((JSONObject) value));
				} else {
					map.put(key, ConvertUtils.toStr(value));
				}
			});
		} catch (Exception e) {
		}
		return map;
	}

	protected static List<Map<String, Object>> parseToMaps(JSONArray jsonArray) {
		List<Map<String, Object>> maps = new Vector<Map<String, Object>>();
		if(null == jsonArray){
			return maps ;
		}
		try {
			jsonArray.forEach((data) -> {
				if (data instanceof JSONObject) {
					maps.add(parseToMap((JSONObject) data));
				}
			});
		} catch (Exception e) {
		}
		return maps;
	}
}
