package com.suke.czx.modules.sys.dao;

import com.suke.czx.modules.sys.entity.SysGradeEntity;
import com.suke.czx.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 班级信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:46:16
 */
@Mapper
public interface SysGradeDao extends BaseDao<SysGradeEntity> {

	/**
	 * 根据key，查询value
	 */
	SysGradeEntity queryByKey(String paramKey);

	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);

	/**
	 * 根据类型，查询参数名和参数值
	 */
	List<SysGradeEntity> queryType(String type);


	List<SysUserEntity> queryUserList(Map<String,Object> map);

	int queryUserTotal(Map<String,Object> map);

	int updateGradeUser(Map<String,Object> map);
}
