package com.suke.czx.modules.sys.service;


import com.suke.czx.modules.sys.entity.SysGradeEntity;
import com.suke.czx.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 班级信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface SysGradeService {
	
	/**
	 * 保存班级信息
	 */
	public void save(SysGradeEntity grade);

	/**
	 * 更新配置信息
	 */
	public void update(SysGradeEntity grade);

	
	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);
	
	/**
	 * 获取List列表
	 */
	public List<SysGradeEntity> queryList(Map<String, Object> map);
	/**
	 * 获取总记录数
	 */
	public int queryTotal(Map<String, Object> map);
	/**
	 * 获取单条信息
	 */
	public SysGradeEntity queryObject(Long id);

	/**
	 * 获取班级成员List列表
	 */
	public List<SysUserEntity> queryUserList(Map<String, Object> map);

	/**
	 * 获取总记录数
	 */
	public int queryUserTotal(Map<String, Object> map);

	/**
	 * 更新班级成员
	 */
	public void updateGradeUser(Map<String, Object> map);
	
}
