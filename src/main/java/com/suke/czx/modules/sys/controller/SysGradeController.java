package com.suke.czx.modules.sys.controller;

import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.common.utils.R;
import com.suke.czx.common.validator.ValidatorUtils;
import com.suke.czx.modules.sys.entity.SysGradeEntity;
import com.suke.czx.modules.sys.entity.SysUserEntity;
import com.suke.czx.modules.sys.service.SysGradeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 班级信息
 * 
 * @author guohao
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/grade")
public class SysGradeController extends AbstractController {
	@Autowired
	private SysGradeService sysGradeService;
	
	/**
	 * 班级
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:grade:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysGradeEntity> gradeList = sysGradeService.queryList(query);

		int total = sysGradeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(gradeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 班级信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:grade:info")
	public R info(@PathVariable("id") Long id){
		SysGradeEntity grade = sysGradeService.queryObject(id);
		
		return R.ok().put("grade", grade);
	}
	
	/**
	 * 保存班级
	 */
	@SysLog("保存班级")
	@RequestMapping("/save")
	@RequiresPermissions("sys:grade:save")
	public R save(@RequestBody SysGradeEntity grade){
		ValidatorUtils.validateEntity(grade);

		sysGradeService.save(grade);
		
		return R.ok();
	}
	
	/**
	 * 修改班级
	 */
	@SysLog("修改班级")
	@RequestMapping("/update")
	@RequiresPermissions("sys:grade:update")
	public R update(@RequestBody SysGradeEntity grade){
		ValidatorUtils.validateEntity(grade);

		sysGradeService.update(grade);
		
		return R.ok();
	}
	
	/**
	 * 删除班级
	 */
	@SysLog("删除班级")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:grade:delete")
	public R delete(@RequestBody Long[] ids){
		sysGradeService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 获取班级成员List列表
	 */
	@SysLog("获取班级成员List列表")
	@RequestMapping("/userList/{id}")
	@RequiresPermissions("sys:grade:userList")
	public R userList(@PathVariable("id") Long id,@RequestParam Map<String, Object> params){
		if(id !=null){
			SysGradeEntity grade = sysGradeService.queryObject(id);
			params.put("areaId",grade.getAreaId());
			params.put("schoolId",grade.getSchoolId());
			params.put("gradeId",grade.getGradeId());
			params.put("classId",grade.getClassId());
		}
		//查询列表数据
		params.put("id",id);
		Query query = new Query(params);
		List<SysUserEntity> userList = sysGradeService.queryUserList(query);

		int total = sysGradeService.queryUserTotal(query);

		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 获取用户成员List列表
	 */
	@SysLog("获取班级成员List列表")
	@RequestMapping("/userList")
	@RequiresPermissions("sys:grade:userList")
	public R userList(@RequestParam Map<String, Object> params){
		params.put("id","");
		Query query = new Query(params);
		List<SysUserEntity> userList = sysGradeService.queryUserList(query);

		int total = sysGradeService.queryUserTotal(query);

		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 更新班级成员
	 */
	@SysLog("更新班级成员")
	@RequestMapping("/updateGradeUser")
	@RequiresPermissions("sys:grade:updateGradeUser")
	public R update(HttpServletRequest request , HttpServletResponse response,@RequestBody String param){

		JSONObject jsonObject = JSONObject.fromObject(param);
		long id=jsonObject.getInt("id");
		JSONArray user=jsonObject.getJSONArray("userIds");
		String s=StringUtils.join(user,",");
		List<String> list=Arrays.asList(s.split(","));

		SysGradeEntity grade = sysGradeService.queryObject(id);
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("userIds",list);
		params.put("gradeClassId",grade.getId());
		params.put("areaId",grade.getAreaId());
		params.put("schoolId",grade.getSchoolId());
		params.put("gradeId",grade.getGradeId());
		params.put("classId",grade.getClassId());
        sysGradeService.updateGradeUser(params);

		return R.ok();
	}


	/**
	 * 去除班级成员
	 */
	@SysLog("去除班级成员")
	@RequestMapping("/removeGradeUser")
	@RequiresPermissions("sys:grade:removeGradeUser")
	public R deleteUser(@RequestBody Long[] userIds){
		Map<String, Object>params=new HashMap<String, Object>();
		params.put("userIds",userIds);
		sysGradeService.updateGradeUser(params);

		return R.ok();
	}
}
