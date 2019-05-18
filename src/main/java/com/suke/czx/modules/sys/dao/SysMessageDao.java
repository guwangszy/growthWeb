package com.suke.czx.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 消息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysMessageDao extends BaseDao<Object> {

    /**
     * 根据key，查询value
     */
    void saveFile(Map<String, Object> map);

    int delete(Map<String, Object> map);

    int deleteFile(Map<String, Object> map);

    List<Map<String,Object>> queryFileList(Map<String, Object> map);
}
