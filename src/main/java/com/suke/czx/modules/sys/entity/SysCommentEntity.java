package com.suke.czx.modules.sys.entity;


import java.util.Date;

/**
 * 评论表信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:43:36
 */
public class SysCommentEntity {
	private Long id;
	private Long issueId;
	private Long type;
	private Long userId;
	private String commentContent;
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
