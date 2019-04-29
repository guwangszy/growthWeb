package com.suke.czx.modules.sys.entity;


import java.util.Date;

/**
 * 通知信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:43:36
 */
public class SysAdviceEntity {
	private Long id;
	private String title;
	private String content;
	private Long frequency;
	private Date startTime;
	private Date endTime;
	private String createUser;
	private Date createTime;
	private Long gradeId;

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
