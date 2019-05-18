package com.suke.czx.modules.sys.service;

import java.util.List;
import java.util.Map;

/**
 * 消息信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface SysMessageService {
	
	/**
	 * 保存消息
	 */
	public void save(Map<String,Object> map);

	/**
	 * 保存消息
	 */
	public void saveFile(Map<String,Object> map);
	/**
	 * 更新消息
	 */
	public void update(Map<String,Object> map);


	/**
	 * 删除信息
	 */
	public void delete(Long id);

	/**
	 * 删除图片
	 */
	public void deleteFile(Long id);
	
	/**
	 * 获取List列表
	 */
	public List<Map<String, Object>> queryList(Map<String, Object> map);
	/**
	 * 获取总记录数
	 */
	public int queryTotal(Map<String, Object> map);
	
	/*public SysConfigEntity queryObject(Long id);
	
	*//**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 *//*
	public String getValue(String key);
	
	*//**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 *//*
	public <T> T getConfigObject(String key, Class<T> clazz);

	*//**
	 * 根据type类型获取下拉框列表
	 *//*
	public List<SysConfigEntity> queryPullList(String type);*/
	
}
