package com.dragon.service.impl;

import org.dragon.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.dragon.entity.SysUser;
import com.dragon.service.SysUserService;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements
	SysUserService	 {

}
