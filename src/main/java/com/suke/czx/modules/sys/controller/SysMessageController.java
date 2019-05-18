package com.suke.czx.modules.sys.controller;

import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.sys.entity.SysMessageEntity;
import com.suke.czx.modules.sys.service.SysGrowUpService;
import com.suke.czx.modules.sys.service.SysMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/message")
public class SysMessageController extends AbstractController {
	@Autowired
	private SysMessageService sysMessageService;

	@Autowired
	private SysGrowUpService sysGrowUpService;
	/**
	 * 所有消息列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:message:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> messageList = sysMessageService.queryList(query);
		int total = sysMessageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(messageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 配置信息
	 *//*
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.queryObject(id);
		
		return R.ok().put("config", config);
	}*/
	
	/**
	 * 保存消息
	 */
	@SysLog("保存消息")
	@RequestMapping("/save")
	@RequiresPermissions("sys:message:save")
	public R save(@RequestBody SysMessageEntity message){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",message.getUuid());
		params.put("headline",message.getHeadline());
		params.put("content",message.getContent());
		params.put("create_user",getUserId());
		sysMessageService.save(params);
		
		return R.ok();
	}

	/**
	 * 上传图片
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:message:upload")
	public R upload(@RequestParam("file") MultipartFile file,@RequestParam("uuid") String uuid) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		sysGrowUpService.saveFile(file,file.getOriginalFilename(),getUser().getUsername(),uuid);
	/*	//getUser().getUsername();
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);
*/
		return R.ok().put("filename",file.getOriginalFilename());
	}


	/**
	 * 修改消息
	 */
	@SysLog("修改消息")
	@RequestMapping("/update")
	@RequiresPermissions("sys:message:update")
	public R update(@RequestParam Map<String, Object> params){
		sysMessageService.update(params);
		
		return R.ok();
	}
	
	/**
	 * 删除消息
	 */
	@SysLog("删除消息")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:message:delete")
	public R delete(@RequestBody String id){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		sysMessageService.delete(params);
		sysMessageService.deleteFile(params);
		return R.ok();
	}

	/**
	 * 删除图片
	 */
	@SysLog("删除图片")
	@RequestMapping("/deleteFile")
	@RequiresPermissions("sys:message:deleteFile")
	public R deleteFile(@RequestBody String id){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		sysMessageService.deleteFile(params);

		return R.ok();
	}

}
