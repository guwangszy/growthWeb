package com.suke.czx.modules.sys.service.impl;

import com.suke.czx.modules.sys.dao.SysGrowUpDao;
import com.suke.czx.modules.sys.entity.SysLogEntity;
import com.suke.czx.modules.sys.service.SysGrowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("SysGrowUpServiceImpl")
public class SysGrowUpServiceImpl implements SysGrowUpService{

    @Autowired
    private SysGrowUpDao sysGrowUpDao;
    /**
     * 保存成长册信息
     */
    public void save(Map<String,Object> map){
        String uuid = UUID.randomUUID().toString();

        Map<String,Object> growUp =new HashMap<String,Object>();

        map.put("id",uuid);
        //   保存成长册信息
        sysGrowUpDao.save(map);
        map.put("fk_id",uuid);
        //   保存附件
        sysGrowUpDao.saveFile(map);
    }

    @Override
    public List<Map<String,Object>> queryList(Map<String, Object> map) {
        return (List)sysGrowUpDao.queryList(map);
    }
}
