package com.dragon.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dragon.logs.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextUtils {
	
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes()  ; 
		
		return null == requestAttributes ? null : requestAttributes.getRequest() ;
	}
	
	
	public static HttpSession getSession(){
		HttpServletRequest request = getRequest();
		return null==request?null:request.getSession() ;
	}
	
	
	public static Object getBean(String name , ApplicationContext context){
		try {
			return context.getBean(name) ;
		} catch (Exception e) {
			LogManager.err(String.format("cann't get bean \"%s\"", name ) , e );
		}
		return null;
	}
	
	public static String getBasePath(){
		HttpServletRequest request = getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()
				+ "://"
				+ request.getServerName()
				+ (request.getServerPort() == 80 ? "" : (":" + request
						.getServerPort())) + path + "/";
		return basePath;
	}
}
