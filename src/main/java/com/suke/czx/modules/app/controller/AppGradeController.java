package com.suke.czx.modules.app.controller;

import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.modules.app.service.appUpdate.AppUpdateService;
import com.suke.czx.modules.sys.controller.AbstractController;
import com.suke.czx.modules.sys.entity.SysGradeEntity;
import com.suke.czx.modules.sys.service.SysGradeService;
import com.suke.czx.modules.sys.service.SysUserService;
import com.suke.czx.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * APP版本管理
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2018-01-05 15:28:57
 */
@Api(value = "API - AppGradeController ", description = "APP版本管理")
@RestController
@RequestMapping("/app")
public class AppGradeController extends AbstractController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "appUpdateService")
	private AppUpdateService appUpdateService;

	@Autowired
	private SysUserService sysUserService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

	@Autowired
	private SysGradeService sysGradeService;
	
	/**
	 * 列表
	 */
    @ApiOperation(value="列表", notes="列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appGrade/list")
	public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppGradeController 列表",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(params.get("data"));
		//查询列表数据
        Query query = new Query(jsonObject);
        query.isPaging(true);
		List<HashMap<String,Object>> appUpdateList = appUpdateService.queryList(query);
		PageUtils pageUtil = new PageUtils(appUpdateList, query.getTotle(), query.getLimit(), query.getPage());
        return AppBaseResult.success().setEncryptData(pageUtil);
	}
	/**
	 * 创建班级
	 */
    @ApiOperation(value="创建班级", notes="创建班级")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appGrade/save")
	public AppBaseResult save(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		SysGradeEntity grade =new SysGradeEntity();
		grade.setAreaId(Long.parseLong((String)jsonObject.get("areaId")));
		grade.setSchoolId(Long.parseLong((String)jsonObject.get("schoolId")));
		grade.setGradeId(Long.parseLong((String)jsonObject.get("gradeId")));
		grade.setClassId(Long.parseLong((String)jsonObject.get("classId")));
		sysGradeService.save(grade);
		return AppBaseResult.success();
	}
}
