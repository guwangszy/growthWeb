package com.suke.czx.modules.sys.service;

import com.suke.czx.modules.sys.entity.SysLogEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysGrowUpService {

    /**
     * 保存成长册信息
     */
    void save(Map<String,Object> map);
    /**
     * 保存文件
     */
    void saveFile(MultipartFile multipartFile, String username, String uuid);

    List<Map<String,Object>> queryList(Map<String,Object> map);
}
