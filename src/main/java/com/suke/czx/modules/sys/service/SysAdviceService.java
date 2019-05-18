package com.suke.czx.modules.sys.service;


import com.suke.czx.modules.sys.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 班级圈信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface SysAdviceService {
	
	/**
	 * 保存通知表信息
	 */
	public void save(SysAdviceEntity advice);

	/**
	 * 保存评论表信息
	 */
	public void saveComment(SysCommentEntity comment);

	/**
	 * 保存发布表信息
	 */
	public void saveIssue(SysIssueEntity issue);

	/**
	 * 获取通知List列表
	 */
	public List<SysAdviceEntity> queryList(Map<String, Object> map);
	/**
	 * 获取通知总记录数
	 */
	public int queryTotal(Map<String, Object> map);
	/**
	 * 获取单条通知信息
	 */
	public SysAdviceEntity queryObject(Long id);

	/**
	 * 获取发布List列表
	 */
	public List<Map<String, Object>> queryCycleList(Map<String, Object> map);

	/**
	 * 获取评论List列表
	 */
	public List<Map<String, Object>> queryCommentList(Long id);

	/**
	 * 获取通知List列表
	 */
	public List<Map<String, Object>> queryAdviceList(Map<String, Object> map);

	/**
	 * 获取通知List列表
	 */
	public List<Map<String, Object>> queryWorkList(Map<String, Object> map);

	/**
	 * 修改
	 */
	public void updateWork(SysIssueEntity issue);
}
