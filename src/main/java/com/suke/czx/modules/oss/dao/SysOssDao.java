package com.suke.czx.modules.oss.dao;

import com.suke.czx.modules.oss.entity.SysAccessoryEntity;
import com.suke.czx.modules.oss.entity.SysOssEntity;
import com.suke.czx.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-25 12:13:26
 */
@Mapper
public interface SysOssDao extends BaseDao<SysOssEntity> {

    void saveAcc(SysAccessoryEntity sysacc);

    List<SysAccessoryEntity> queryAccList(Map<String, Object> map);

    int queryAccTotal(Map<String, Object> map);

    SysAccessoryEntity queryAccObject(Long id);

    SysAccessoryEntity queryAccDetil(String accessoryAddress);

}
