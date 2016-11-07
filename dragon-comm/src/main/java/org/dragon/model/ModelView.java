package org.dragon.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModelView implements Serializable{
	
	private static final long serialVersionUID = -3833385718521511536L;
	
	Map<String, Object> requestDatas = new HashMap<String, Object>() ;
	
	Map<String, Object> sessionDatas = new HashMap<String, Object>() ;
	
	private Object json ; 
	
	public Object getJson() {
		return json;
	}

	public void setJson(Object json) {
		this.json = json;
	}

	public void addRequest(String key , Object value){
		requestDatas.put(key , value ) ;
	}
	
	public void addSession(String key , Object value){
		sessionDatas.put(key, value);
	}
}
