package com.suke.czx.modules.sys.service.impl;

import com.suke.czx.modules.sys.dao.SysAdviceDao;
import com.suke.czx.modules.sys.entity.*;
import com.suke.czx.modules.sys.service.SysAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("SysAdviceService")
public class SysAdviceServiceImpl implements SysAdviceService {
	@Autowired
	private SysAdviceDao sysAdviceDao;

	@Override
	@Transactional
	public void save(SysAdviceEntity grade) {
		sysAdviceDao.save(grade);
	}

	public void saveComment(SysCommentEntity comment) {
		sysAdviceDao.saveComment(comment);
	}

	public void saveIssue(SysIssueEntity issue) {
		sysAdviceDao.saveIssue(issue);
	}

	@Override
	public List<SysAdviceEntity> queryList(Map<String, Object> map) {
		return sysAdviceDao.queryList(map);
	}

	@Override
	public List<Map<String, Object>> queryAdviceList(Map<String, Object> map) {
		return sysAdviceDao.queryAdviceList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysAdviceDao.queryTotal(map);
	}

	@Override
	public SysAdviceEntity queryObject(Long id) {
		return sysAdviceDao.queryObject(id);
	}
	@Override
	public List<Map<String, Object>> queryCycleList(Map<String, Object> map) {
		return sysAdviceDao.queryCycleList(map);
	}

	@Override
	public List<Map<String, Object>> queryCommentList(Long id) {
		return sysAdviceDao.queryCommentList(id);
	}
}
