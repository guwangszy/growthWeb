package com.suke.czx.modules.oss.service.impl;

import com.suke.czx.modules.oss.dao.SysOssDao;
import com.suke.czx.modules.oss.entity.SysAccessoryEntity;
import com.suke.czx.modules.oss.entity.SysOssEntity;
import com.suke.czx.modules.oss.service.SysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("sysOssService")
public class SysOssServiceImpl implements SysOssService {
	@Autowired
	private SysOssDao sysOssDao;
	
	@Override
	public SysOssEntity queryObject(Long id){
		return sysOssDao.queryObject(id);
	}
	
	@Override
	public List<SysOssEntity> queryList(Map<String, Object> map){
		return sysOssDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysOssDao.queryTotal(map);
	}
	
	@Override
	public void save(SysOssEntity sysOss){
		sysOssDao.save(sysOss);
	}
	
	@Override
	public void update(SysOssEntity sysOss){
		sysOssDao.update(sysOss);
	}
	
	@Override
	public void delete(Long id){
		sysOssDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysOssDao.deleteBatch(ids);
	}

	@Override
	public void saveAcc(SysAccessoryEntity sysacc){
		sysOssDao.saveAcc(sysacc);
	}

	@Override
	public List<SysAccessoryEntity> queryAccList(Map<String, Object> map){
		return sysOssDao.queryAccList(map);
	}

	@Override
	public int queryAccTotal(Map<String, Object> map){
		return sysOssDao.queryAccTotal(map);
	}

	@Override
	public SysAccessoryEntity queryAccObject(Long id){
		return sysOssDao.queryAccObject(id);
	}

	@Override
	public SysAccessoryEntity queryAccDetil(String accessoryAddress){
		return sysOssDao.queryAccDetil(accessoryAddress);
	}
}
