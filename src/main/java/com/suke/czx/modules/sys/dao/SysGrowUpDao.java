package com.suke.czx.modules.sys.dao;

import com.suke.czx.modules.sys.entity.SysGradeEntity;
import com.suke.czx.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysGrowUpDao extends BaseDao<Object> {

    /**
     * 根据key，查询value
     */
    void saveFile(Map<String,Object> map);

    List<Map<String,Object>> queryFileList(Map<String, Object> map);
}
