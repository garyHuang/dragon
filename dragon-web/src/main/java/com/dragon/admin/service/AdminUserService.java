package com.dragon.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dragon.sqls.Condition;
import org.dragon.sqls.Condition.Comparison;
import org.dragon.utils.ConvertUtils;
import org.dragon.utils.DateUtils;
import org.dragon.utils.Pager;
import org.dragon.utils.SqlUtils;
import org.dragon.utils.SqlUtils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.dragon.entity.SysUser;
import com.dragon.service.SysUserService;
import com.dragon.utils.ContextUtils;

@Component("admin_user")
public class AdminUserService {
	
	@Autowired
	protected SysUserService sysUserService ;
	
	public Pager list(Map<String,Object> data){
		Integer pageId = ConvertUtils.toInt( data.get("page") ) ;
		pageId=pageId<1?1:pageId;
		Pager pager = new Pager();
		pager.setPageId(pageId);
		
		String loginName = ConvertUtils.toStr(data.get("loginName"));
		if(!StringUtils.isBlank(loginName)){
			pager.addConditions(new Condition("loginName" , Comparison.Like,  SqlUtils.appendLikeValue(loginName 
					, Position.PRE_END ) ));
		}
		
		String email = ConvertUtils.toStr(data.get("email"));
		if(!StringUtils.isBlank(email)){
			pager.addConditions(new Condition("email" , Comparison.Like,  SqlUtils.appendLikeValue(email 
					, Position.PRE_END ) ));
		}
		String stime = ConvertUtils.toStr(data.get("stime")); 
		String etime = ConvertUtils.toStr(data.get("etime")); 
		if(!StringUtils.isBlank(stime)){
			pager.addConditions(new Condition("createDate" , Comparison.Ge,  DateUtils.parseYYYYMMDD0(stime)));
		}
		
		if(!StringUtils.isBlank(etime)){
			pager.addConditions(new Condition("createDate" , Comparison.Le,  DateUtils.parseYYYYMMDD0(etime)));
		}
		int rows = ConvertUtils.toInt(data.get("rows"));
		pager.setPageSize(rows<15?15:rows);
		pager = sysUserService.queryPage(pager) ;
		
		return pager ;
	}
	
	public Map<String, Object> login(Map<String,Object> data){
		Map<String, Object> result = new HashMap<String, Object>();
		String name = ConvertUtils.toStr(data.get("uname"));
		String pwd = ConvertUtils.toStr(data.get("pwd"));
		if(!StringUtils.isBlank(name)){
			SysUser sysUser = sysUserService.queryItem(new Condition( "loginName" , name ));
			if(sysUser == null){
				result.put("code", "1");
				result.put("msg", "账号不存在");
			}else if(!pwd.equals(sysUser.getPwd())){
				result.put("code", "2");
				result.put("msg", "密码错误");
			}else{
				WebUtils.setSessionAttribute(ContextUtils.getRequest()
						, "sysUser" ,  sysUser ) ;
				result.put("code", "0");
				result.put("msg", "成功");
			}
		}else{
			result.put("code", "3");
			result.put("msg", "登录失败");
		}
		return result ; 
	}
}
