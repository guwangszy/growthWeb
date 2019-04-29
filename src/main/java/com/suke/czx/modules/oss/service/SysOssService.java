package com.suke.czx.modules.oss.service;

import com.suke.czx.modules.oss.entity.SysAccessoryEntity;
import com.suke.czx.modules.oss.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-25 12:13:26
 */
public interface SysOssService {
	
	SysOssEntity queryObject(Long id);
	
	List<SysOssEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysOssEntity sysOss);
	
	void update(SysOssEntity sysOss);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void saveAcc(SysAccessoryEntity sysacc);

	List<SysAccessoryEntity> queryAccList(Map<String, Object> map);

	int queryAccTotal(Map<String, Object> map);

	SysAccessoryEntity queryAccObject(Long id);
}
