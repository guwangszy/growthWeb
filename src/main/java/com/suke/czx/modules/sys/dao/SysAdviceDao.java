package com.suke.czx.modules.sys.dao;

import com.suke.czx.modules.sys.entity.SysAdviceEntity;
import com.suke.czx.modules.sys.entity.SysCommentEntity;
import com.suke.czx.modules.sys.entity.SysIssueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 班级圈信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:46:16
 */
@Mapper
public interface SysAdviceDao extends BaseDao<SysAdviceEntity> {

	/**
	 * 根据key，查询value
	 */
	SysAdviceEntity queryByKey(String paramKey);

	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);

	/**
	 * 根据类型，查询参数名和参数值
	 */
	List<SysAdviceEntity> queryType(String type);


	List<SysAdviceEntity> queryUserList(Map<String, Object> map);

	/*int queryUserTotal(Map<String, Object> map);

	int updateGradeUser(Map<String, Object> map);*/

	void saveComment(SysCommentEntity comment);

	void saveIssue(SysIssueEntity issue);

	List <Map<String, Object>> queryCycleList(Map<String, Object> map);

	List <Map<String, Object>> queryCommentList(Long id);

	List <Map<String, Object>> queryAdviceList(Map<String, Object> map);

	List <Map<String, Object>> queryWorkList(Map<String, Object> map);

	void updateWork(SysIssueEntity issue);
}
