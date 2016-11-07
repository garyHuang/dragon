package com.dragon.entity;

import java.util.Date;

import org.dragon.models.EntityBase;

public class SysPermission extends EntityBase<Integer> {

	private static final long serialVersionUID = 5551954807650147407L;

	private String menuname;

	private String icon;

	private String url;

	private Integer pid;

	private Date createDate;

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
