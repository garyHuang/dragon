package com.dragon.service.impl;

import java.util.List;
import java.util.Map;

import org.dragon.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.dragon.entity.SysPermission;
import com.dragon.service.SysPermissionService;

@Service("sysPermissionService")
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, Integer> implements
	SysPermissionService {

	 @Override
	public List<Map<String, Object>> queryUserPer(Integer userid) {
		
		List<Map<String, Object>> datas = queryAll( "select * from sys_permission order by pid" ) ;
		 
		return datas ;
	}
	 
	 
	 @Override
	public List<Map<String, Object>> queryAll() {
		 List<Map<String, Object>> datas = queryAll( "select * from sys_permission order by pid" ) ;
		 
		return datas ;
	}
}
