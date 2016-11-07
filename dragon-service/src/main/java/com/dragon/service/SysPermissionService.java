package com.dragon.service;

import java.util.List;
import java.util.Map;

import org.dragon.service.BaseService;

import com.dragon.entity.SysPermission;

public interface SysPermissionService extends BaseService<SysPermission, Integer>{
	
	
	public List<Map<String, Object>> queryUserPer(Integer userid) ;
	
	

	public List<Map<String, Object>> queryAll() ;
}
