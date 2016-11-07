package com.dragon.action.admin;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.dragon.logs.LogManager;
import org.dragon.utils.ConvertUtils;
import org.dragon.utils.JsonParser;
import org.dragon.utils.Pager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dragon.entity.SysUser;
import com.dragon.utils.ContextUtils;
import com.dragon.utils.UrlConstant;

@Controller
@RequestMapping("/admin/")
public class AdminController implements ApplicationContextAware{
	
	ApplicationContext context ;
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	@RequestMapping("p_{name}")
	public String forword(@PathVariable("name") String name){
		HttpServletRequest request = ContextUtils.getRequest() ; 
		if(!UrlConstant.LOGIN_URL.equalsIgnoreCase(request.getServletPath())){
			SysUser sysUser = (SysUser) ContextUtils.getSession().getAttribute("sysUser");
			if(null == sysUser){
				return UrlConstant.LOGIN_JSP ;
			}
		}
		
		
		String[] paths = name.split("-"); 
		if(paths.length > 1){
			return "admin/" + name.replace("-", "/") ;
		}
		
		paths = name.split("_");
		if(paths.length == 1){
			return "admin/" + name ;
		}
		
		
		Map<String, Object> data = new HashMap<String, Object>( );
		Enumeration<String> parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String paramName = parameterNames.nextElement();
			data.put(paramName, request.getParameter(paramName) ) ;
		}
		Object bean = ContextUtils.getBean( "admin_" + paths[0] , context) ;
		if(null != bean){
			try {
				/*执行需要调用的方法*/
				Object invokeData = MethodUtils.invokeMethod(bean, paths[1], data) ; 
				request.setAttribute( "data" , invokeData ) ;
			} catch (Exception e) {
				LogManager.err( String.format("p_invoke_error :%s", name) , e );
			}
		}
		return "admin/" + name.replace("_", "/") ;
	}
	
	/**
	 * 调用ajax方法
	 * */
	@RequestMapping("ajax_{name}")
	@ResponseBody
	public String ajax(@PathVariable("name") String name){
		
		Map<String,Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("success", true ) ;
		resultMap.put("msg", "执行成功" ) ;
		HttpServletRequest request = ContextUtils.getRequest() ; 
		String[]paths = name.split("_") ;
		if(paths.length!=2){
			resultMap.put("success", false ) ;
			resultMap.put("msg", "url格式不正确" ) ;
			return JSON.toJSONString(resultMap) ;
		}
		
		Map<String, Object> data =null ;
		
		String dataType = ConvertUtils.toStr(request.getParameter("dataType")) ;
		/*判断前台传递的参数是JSON格式，还是普通url传参*/
		if("json".equalsIgnoreCase(dataType)){ 
			String jsonData = ConvertUtils.toStr( request.getParameter("data") ) ;
			data = JsonParser.parseToObj( jsonData ) ; 
		}else{
			data = new HashMap<String, Object>( );
			Enumeration<String> parameterNames = request.getParameterNames();
			while(parameterNames.hasMoreElements()){
				String paramName = parameterNames.nextElement();
				data.put(paramName, request.getParameter(paramName) ) ;
			}
		}
		/*获取spring上下文中的bean*/
		Object bean = ContextUtils.getBean( "admin_" + paths[0] , context) ;
		if(null != bean){
			try {
				/*执行需要调用的方法*/
				Object invokeData = MethodUtils.invokeMethod(bean, paths[1], data) ;
				if(invokeData instanceof Pager){
					return JSON.toJSONStringWithDateFormat(invokeData,"yyyy-MM-dd HH:mm:ss");
				}
				resultMap.put("data", invokeData ) ; 
			} catch (Exception e) {
				resultMap.put("success", false );
				resultMap.put("msg", "出现错误：" + e.getMessage() );
				LogManager.err( String.format("ajax_invoke_error :%S", name)  , e );
			}
		}else{
			resultMap.put("success", false );
			resultMap.put("msg", "服务器内部错误" );
		}
		return JSON.toJSONString(resultMap);
	}
}
