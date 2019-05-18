package com.suke.czx.modules.app.controller;

import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.utils.BASE64DecodedMultipartFile;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.modules.app.service.appUpdate.AppUpdateService;
import com.suke.czx.modules.sys.controller.AbstractController;
import com.suke.czx.modules.sys.entity.SysAdviceEntity;
import com.suke.czx.modules.sys.entity.SysCommentEntity;
import com.suke.czx.modules.sys.entity.SysIssueEntity;
import com.suke.czx.modules.sys.service.SysAdviceService;
import com.suke.czx.modules.sys.service.SysGrowUpService;
import com.suke.czx.modules.sys.service.SysMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


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

	@Autowired
	private SysGrowUpService sysGrowUpService;


	@Autowired
	private SysMessageService sysMessageService;
	
	/**
	 * 任务列表
	 */
    @ApiOperation(value="任务列表", notes="任务列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/list")
	public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(params.get("data"));
		//查询列表数据
        Query query = new Query(jsonObject);
        query.isPaging(true);
		List<Map<String, Object>> appUpdateList = sysAdviceService.queryAdviceList(query);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Map<String, Object> map :appUpdateList){
			map.put("create_time",sdf.format(map.get("create_time")));
		}
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
		advice.setTitle(jsonObject.getString("title"));
		advice.setContent(jsonObject.getString("content"));
		if(!StringUtils.isBlank(jsonObject.getString("frequency"))){
			advice.setFrequency(jsonObject.getLong("frequency"));
		}
		int cycle=0;
		if(!StringUtils.isBlank(jsonObject.getString("cycle"))){
			cycle=jsonObject.getInt("cycle");
		}
		advice.setGradeId(jsonObject.getLong("gradeId"));
		//获取结束时间
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, cycle);
		advice.setEndTime(calendar.getTime());
		advice.setCreateUser(jsonObject.getString("createUser"));
		sysAdviceService.save(advice);
		return AppBaseResult.success();
	}

	/**
	 * 新增发布/作业
	 */
	@ApiOperation(value="新增发布", notes="新增发布")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/saveIssue")
	public AppBaseResult saveIssue(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		//新增图片
		MultipartFile multipartFile =null;
		String uuid = UUID.randomUUID().toString();
		if(jsonObject!=null&&jsonObject.containsKey("file")){
			JSONArray jsonArray=jsonObject.getJSONArray("file");
			String username =jsonObject.getString("username");
			if(jsonArray!=null){
				for (Object files:jsonArray) {
					JSONObject file=(JSONObject) files;
					String fileCode =file.getString("fileCode");
					String fileName =file.getString("fileName");
					if(StringUtils.isNotEmpty(fileCode)&&StringUtils.isNotEmpty(fileName)) {
						multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(fileCode);
						sysGrowUpService.saveFile(multipartFile,fileName,username,uuid);
					}
				}
			}
		}
		SysIssueEntity issue =new SysIssueEntity();
		issue.setId(uuid);
		issue.setAdviceId(jsonObject.getLong("adviceId"));
		issue.setUserId(jsonObject.getLong("userId"));
		issue.setGradeId(jsonObject.getLong("gradeId"));
		if(jsonObject.containsKey("doneContent")){
			issue.setDoneContent(jsonObject.getString("doneContent"));
		}
		sysAdviceService.saveIssue(issue);
		return AppBaseResult.success();
	}

	/**
	 * 修改作业
	 */
	@ApiOperation(value="修改作业", notes="修改作业")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/updateWork")
	public AppBaseResult updateWork(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		//先删除图片
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",jsonObject.getString("fkId"));
		sysMessageService.deleteFile(params);
		//新增图片
		MultipartFile multipartFile =null;
		String uuid = jsonObject.getString("fkId");
		if(jsonObject!=null&&jsonObject.containsKey("file")){
			JSONArray jsonArray=jsonObject.getJSONArray("file");
			String username =jsonObject.getString("username");
			if(jsonArray!=null){
				for (Object files:jsonArray) {
					JSONObject file=(JSONObject) files;
					String fileCode =file.getString("fileCode");
					String fileName =file.getString("fileName");
					if(StringUtils.isNotEmpty(fileCode)&&StringUtils.isNotEmpty(fileName)) {
						multipartFile = BASE64DecodedMultipartFile.base64ToMultipart(fileCode);
						sysGrowUpService.saveFile(multipartFile,fileName,username,uuid);
					}
				}
			}
		}
		SysIssueEntity issue =new SysIssueEntity();
		issue.setId(uuid);
		issue.setAdviceId(jsonObject.getLong("adviceId"));
		issue.setUserId(jsonObject.getLong("userId"));
		issue.setGradeId(jsonObject.getLong("gradeId"));
		if(jsonObject.containsKey("doneContent")){
			issue.setDoneContent(jsonObject.getString("doneContent"));
		}
		sysAdviceService.updateWork(issue);
		return AppBaseResult.success();
	}

	/**
	 * 作业列表
	 */
	@ApiOperation(value="作业列表", notes="作业列表")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/workList")
	public AppBaseResult workList(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> params = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(params.get("data"));
		//查询列表数据
		Query query = new Query(jsonObject);
		query.isPaging(true);
		List<Map<String, Object>> appUpdateList = sysAdviceService.queryAdviceList(query);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		for(Map<String, Object> map :appUpdateList){
			map.put("create_time",sdf.format(map.get("create_time")));
		}
		PageUtils pageUtil = new PageUtils(appUpdateList, query.getTotle(), query.getLimit(), query.getPage());
		return AppBaseResult.success().setEncryptData(pageUtil);
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
		comment.setIssueId(jsonObject.getString("issueId"));
		comment.setType(jsonObject.getLong("type"));
		comment.setUserId(jsonObject.getLong("userId"));
		if(jsonObject.containsKey("commentContent")){
			comment.setCommentContent(jsonObject.getString("commentContent"));
		}
		sysAdviceService.saveComment(comment);
		return AppBaseResult.success();
	}

	/**
	 * 获取通知详情
	 */
	@ApiOperation(value="获取通知详情", notes="获取通知详情")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/getAdvice")
	public AppBaseResult getAdvice(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
		Long id=jsonObject.getLong("id");
		SysAdviceEntity advice=sysAdviceService.queryObject(id);
		JSONObject json=JSONObject.fromObject(advice);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		json.put("createTime",sdf.format(advice.getCreateTime()));
		json.put("startTime",sdf.format(advice.getStartTime()));
		json.put("endTime",sdf.format(advice.getEndTime()));
		return AppBaseResult.success().setEncryptData(json);
	}

	/**
	 * 班级圈列表
	 */
	@ApiOperation(value="班级圈列表", notes="班级圈列表")
	@ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appAdvice/gradeCycleList")
	public AppBaseResult gradeCycleList(@RequestBody AppBaseResult appBaseResult)throws Exception{
		HashMap<String,Object> params = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
		JSONObject jsonObject=JSONObject.fromObject(params.get("data"));
		//查询列表数据
		Query query = new Query(jsonObject);
		query.isPaging(true);
		List<Map<String,Object>> cycleList = sysAdviceService.queryCycleList(query);
		for(Map<String,Object> map:cycleList){
		Long issueId=Long.parseLong(String.valueOf(map.get("id")));
		List<Map<String,Object>> commentList=sysAdviceService.queryCommentList(issueId);
		map.put("commentList",commentList);
		}
		PageUtils pageUtil = new PageUtils(cycleList, query.getTotle(), query.getLimit(), query.getPage());
//		Map<String,Object> list=new HashMap<String,Object>();
//		list.put("cycleList",cycleList);
//		list.put("total",pageUtil.getTotalCount());
//		list.put("page",pageUtil.getCurrPage());
		return AppBaseResult.success().setEncryptData(pageUtil);
	}
/*	public static  void  main(String []args){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(sdf.format(calendar.getTime())));

	}*/
}
