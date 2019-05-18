package com.suke.czx.modules.sys.entity;


import java.util.Date;

/**
 * 系统配置信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:43:36
 */
public class SysMessageEntity {
	private String id;

	private String headline;
	private String accessoryAddress;
	private String accessoryName;
	private String accessorySuffix;

	public String getAccessoryAddress() {
		return accessoryAddress;
	}

	public void setAccessoryAddress(String accessoryAddress) {
		this.accessoryAddress = accessoryAddress;
	}

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	public String getAccessorySuffix() {
		return accessorySuffix;
	}

	public void setAccessorySuffix(String accessorySuffix) {
		this.accessorySuffix = accessorySuffix;
	}

	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	private String content;

	private String createUser;
	private Date createTime;

}
