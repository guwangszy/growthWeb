package com.suke.czx.modules.oss.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 文件上传
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-25 12:13:26
 */
public class SysAccessoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;

	private String accessoryName;
	private String accessorySuffix;
	private String accessoryAddress;
	private String fkId;
	private String createUser;

	private Date createTime;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getAccessoryAddress() {
		return accessoryAddress;
	}

	public void setAccessoryAddress(String accessoryAddress) {
		this.accessoryAddress = accessoryAddress;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
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
}
