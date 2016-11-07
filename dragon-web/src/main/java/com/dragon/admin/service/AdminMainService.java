package com.dragon.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dragon.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dragon.entity.SysUser;
import com.dragon.service.SysPermissionService;
import com.dragon.utils.ContextUtils;

@Component("admin_main")
@SuppressWarnings("unchecked")
public class AdminMainService {
	
	@Autowired
	protected SysPermissionService sysPermissionService ;
	
	public String menu(Map<String,Object> data){
		
		SysUser sysUser = (SysUser) ContextUtils.getSession().getAttribute("sysUser");
		
		List<Map<String, Object>> datas = sysPermissionService.queryUserPer( sysUser.getId() ) ; 
		Map<Integer , Map<String,Object>> parents = new HashMap<Integer, Map<String,Object>>();
		datas.stream().forEach((item) -> {
			Integer pid = ConvertUtils.toInt( item.get("pid") ) ;
			item.remove("createDate");
			if(pid == 0){
				parents.put(ConvertUtils.toInt( item.get("id") )  , item ) ; 
			}else{
				List<Map<String, Object>> subData = (List<Map<String, Object>>) parents.get(pid).get("menus");
				if(null == subData){
					subData = new ArrayList<Map<String, Object>>();
					parents.get(pid).put( "menus" , subData); 
				}
				subData.add( item ) ;
			}
		});
		
		return JSON.toJSONString( parents.values() ) ;
	}
}
