package com.suke.czx.modules.app.controller;

import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.common.utils.Tools;
import com.suke.czx.modules.app.service.appUpdate.AppUpdateService;
import com.suke.czx.modules.oss.entity.SysAccessoryEntity;
import com.suke.czx.modules.oss.service.SysOssService;
import com.suke.czx.modules.sys.controller.AbstractController;
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
import java.text.SimpleDateFormat;
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
public class AppAccController extends AbstractController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "appUpdateService")
	private AppUpdateService appUpdateService;

	@Autowired
	private SysUserService sysUserService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

	@Autowired
	private SysGradeService sysGradeService;

	@Autowired
	private SysOssService sysOssService;
	
	/**
	 * 文件列表
	 */
    @ApiOperation(value="列表", notes="列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appFile/list")
	public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{
		//查询列表数据
		HashMap<String,Object> params = new HashMap<String,Object>();
		HashMap<String,Object> data = new HashMap<String,Object>();
		params.put("page","1");
		params.put("limit","10000");
		Query query = new Query(params);
		List<SysAccessoryEntity> sysAccList = sysOssService.queryAccList(query);
		int total = sysOssService.queryAccTotal(query);
		PageUtils pageUtil = new PageUtils(sysAccList, total, query.getLimit(), query.getPage());
		data.put("sysAccList",sysAccList);
		data.put("total",pageUtil.getTotalCount());
        return AppBaseResult.success().setEncryptData(data);
	}


	/**
	 * 获取文件详情
	 */
	@ApiOperation(value="获取文件详情", notes="获取文件详情")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appFile/getFile")
	public AppBaseResult getAdvice(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		String accessoryAddress=String.valueOf(jsonObject.get("accessoryAddress"));
		String address="D:\\upfile\\transferFile\\"+accessoryAddress;
		SysAccessoryEntity sysAcc=sysOssService.queryAccDetil(address);
		JSONObject json=JSONObject.fromObject(sysAcc);
		String content=Tools.readTxtFile(address);
		json.put("content",content);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		json.put("createTime",sdf.format(sysAcc.getCreateTime()));
		return AppBaseResult.success().setEncryptData(json);
	}
}
