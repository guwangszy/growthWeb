package com.suke.czx.modules.sys.service.impl;

import com.suke.czx.modules.sys.dao.SysMessageDao;
import com.suke.czx.modules.sys.service.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("SysMessageService")
public class SysMessageServiceImpl implements SysMessageService {
	@Autowired
	private SysMessageDao sysMessageDao;

   //保存消息
	@Override
	public void save(Map<String, Object> map) {
		sysMessageDao.save(map);
	}
	//保存图片
	@Override
	public void saveFile(Map<String, Object> map) {

	}
	//修改消息
	@Override
	public void update(Map<String, Object> map) {
		sysMessageDao.update(map);
	}
	//删除消息
	@Override
	public void delete(Map<String,Object> map) {
		sysMessageDao.delete(map);
	}
	//删除图片
	@Override
	public void deleteFile(Map<String,Object> map) {
		sysMessageDao.deleteFile(map);
	}
	//获取消息列表
	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return (List)sysMessageDao.queryList(map);
	}

	//获取消息列表总数
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysMessageDao.queryTotal(map);
	}
}
