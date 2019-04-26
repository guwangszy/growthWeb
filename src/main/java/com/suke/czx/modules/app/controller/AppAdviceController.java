package com.suke.czx.modules.app.controller;

import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.modules.app.service.appUpdate.AppUpdateService;
import com.suke.czx.modules.sys.controller.AbstractController;
import com.suke.czx.modules.sys.entity.SysAdviceEntity;
import com.suke.czx.modules.sys.entity.SysCommentEntity;
import com.suke.czx.modules.sys.entity.SysIssueEntity;
import com.suke.czx.modules.sys.service.SysAdviceService;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * APP版本管理
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2018-01-05 15:28:57
 */
@Api(value = "API - AppAdviceController ", description = "APP版本管理")
@RestController
@RequestMapping("/app")
public class AppAdviceController extends AbstractController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "appUpdateService")
	private AppUpdateService appUpdateService;

	@Autowired
	private SysAdviceService sysAdviceService;
	
	/**
	 * 任务列表
	 */
    @ApiOperation(value="任务列表", notes="任务列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/list")
	public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppAdviceController 列表",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(params.get("data"));
		//查询列表数据
        Query query = new Query(jsonObject);
        query.isPaging(true);
		List<SysAdviceEntity> appUpdateList = sysAdviceService.queryList(query);
		/*List<HashMap<String,Object>> appUpdateList = appUpdateService.queryList(query);*/
		PageUtils pageUtil = new PageUtils(appUpdateList, query.getTotle(), query.getLimit(), query.getPage());
        return AppBaseResult.success().setEncryptData(pageUtil);
	}
	/**
	 * 新增通知
	 */
    @ApiOperation(value="新增通知", notes="新增通知")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/save")
	public AppBaseResult save(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		SysAdviceEntity advice =new SysAdviceEntity();
		advice.setTitle((String)jsonObject.get("title"));
		advice.setContent((String)jsonObject.get("content"));
		advice.setFrequency(Long.parseLong((String)jsonObject.get("frequency")));
		int cycle=Integer.parseInt((String)jsonObject.get("cycle"));
		//获取结束时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, cycle);
		advice.setEndTime(calendar.getTime());
		advice.setCreateUser((String)jsonObject.get("createUser"));
		sysAdviceService.save(advice);
		return AppBaseResult.success();
	}

	/**
	 * 新增发布
	 */
	@ApiOperation(value="新增发布", notes="新增发布")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/saveIssue")
	public AppBaseResult saveIssue(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		SysIssueEntity issue =new SysIssueEntity();
		issue.setAdviceId(Long.parseLong((String)jsonObject.get("adviceId")));
		issue.setUserId(Long.parseLong((String)jsonObject.get("userId")));
		issue.setDoneContent((String)jsonObject.get("doneContent"));
		sysAdviceService.saveIssue(issue);
		return AppBaseResult.success();
	}

	/**
	 * 新增评论
	 */
	@ApiOperation(value="新增评论", notes="新增评论")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/saveComment")
	public AppBaseResult saveComment(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		SysCommentEntity comment =new SysCommentEntity();
		comment.setIssueId(Long.parseLong((String)jsonObject.get("issueId")));
		comment.setType(Long.parseLong((String)jsonObject.get("type")));
		comment.setUserId(Long.parseLong((String)jsonObject.get("userId")));
		comment.setCommentContent((String)jsonObject.get("commentContent"));
		sysAdviceService.saveComment(comment);
		return AppBaseResult.success();
	}

/*	public static  void  main(String []args){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(sdf.format(calendar.getTime())));

	}*/
}
