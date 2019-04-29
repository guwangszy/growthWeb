package com.suke.czx.modules.sys.service.impl;

import com.suke.czx.common.utils.ConfigConstant;
import com.suke.czx.modules.sys.dao.SysGrowUpDao;
import com.suke.czx.modules.sys.entity.SysLogEntity;
import com.suke.czx.modules.sys.service.SysGrowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
        //   保存成长册信息
        sysGrowUpDao.save(map);
    }
    /**
     * 保存成长册信息
     */
    public void saveFile(MultipartFile multipartFile, String userName,String uuid){
        try {
            if(multipartFile!=null){
                String filename=multipartFile.getName();
                String suffix =filename.substring(filename.lastIndexOf("."));
                File files=new File(ConfigConstant.FILE_BASE_PATH);
                if(!files.exists()){
                    files.mkdirs();
                }
                File file = new File(ConfigConstant.FILE_BASE_PATH+filename);
                multipartFile.transferTo(file);
                Map<String,Object> params =new HashMap<String,Object>();
                params.put("accessoryName",filename);
                params.put("accessorySuffix",suffix);
                params.put("accessoryAddress",ConfigConstant.FILE_BASE_PATH+filename);
                params.put("id",uuid==null?"":uuid);
                params.put("createUser",userName);
                sysGrowUpDao.saveFile(params);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String,Object>> queryList(Map<String, Object> map) {
        return (List)sysGrowUpDao.queryList(map);
    }
}
