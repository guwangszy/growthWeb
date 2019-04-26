package com.suke.czx.modules.sys.entity;


import java.util.Date;

/**
 * 发布表信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:43:36
 */
public class SysIssueEntity {
	private Long id;
	private Long adviceId;
	private Long userId;
	private String doneContent;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdviceId() {
		return adviceId;
	}

	public void setAdviceId(Long adviceId) {
		this.adviceId = adviceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDoneContent() {
		return doneContent;
	}

	public void setDoneContent(String doneContent) {
		this.doneContent = doneContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
