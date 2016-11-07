package com.dragon.entity;

import java.util.Date;

import org.dragon.annotations.Table;
import org.dragon.models.EntityBase;

@Table(name="sys_user")
public class SysUser extends EntityBase<Integer>{
	
	private static final long serialVersionUID = 9098597195251616515L;
	
	private String loginName;
	private String pwd;
	private String phone;
	private String email;
	private Date createDate;
	private Integer isAdmin ;
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
