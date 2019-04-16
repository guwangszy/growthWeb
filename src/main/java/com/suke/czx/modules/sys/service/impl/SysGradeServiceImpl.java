package com.suke.czx.modules.sys.service.impl;

import com.suke.czx.modules.sys.dao.SysGradeDao;
import com.suke.czx.modules.sys.entity.SysGradeEntity;
import com.suke.czx.modules.sys.entity.SysUserEntity;
import com.suke.czx.modules.sys.service.SysGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("SysGradeService")
public class SysGradeServiceImpl implements SysGradeService {
	@Autowired
	private SysGradeDao sysGradeDao;

	@Override
	@Transactional
	public void save(SysGradeEntity grade) {
		sysGradeDao.save(grade);
	}

	@Override
	@Transactional
	public void update(SysGradeEntity grade) {
		sysGradeDao.update(grade);
	}


	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		sysGradeDao.deleteBatch(ids);
	}

	@Override
	public List<SysGradeEntity> queryList(Map<String, Object> map) {
		return sysGradeDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysGradeDao.queryTotal(map);
	}

	@Override
	public SysGradeEntity queryObject(Long id) {
		return sysGradeDao.queryObject(id);
	}

	@Override
	public List<SysUserEntity> queryUserList(Map<String, Object> map) {
			return sysGradeDao.queryUserList(map);
	}

	@Override
	public int queryUserTotal(Map<String, Object> map) {

			return sysGradeDao.queryUserTotal(map);
	}

	@Override
	public void updateGradeUser(Map<String, Object> map) {
		sysGradeDao.updateGradeUser(map);
	}


}
