package com.dragon.admin.service;

import java.util.List;
import java.util.Map;


import org.dragon.utils.ConvertUtils;
import org.dragon.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dragon.service.SysPermissionService;

@Service("admin_permission")
public class AdminPermissionService {
	@Autowired
	protected SysPermissionService sysPermissionService;
	public Pager list(Map<String, Object> data){
		Pager pager = new Pager();
		List<Map<String, Object>> datas = sysPermissionService.queryAll() ;
		datas.forEach((item) -> {
			item.put("_parentId", item.get("pid") );
			int pid = ConvertUtils.toInt(item.get("pid"));
			if(pid == 0){
				item.remove("_parentId");
			}
		});
		pager.setRows(datas);
		pager.setTotal( datas.size() ) ; 
		return pager ;
	}
}
