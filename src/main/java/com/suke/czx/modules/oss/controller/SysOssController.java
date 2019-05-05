package com.suke.czx.modules.oss.controller;

import com.google.gson.Gson;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.*;
import com.suke.czx.common.validator.ValidatorUtils;
import com.suke.czx.common.validator.group.AliyunGroup;
import com.suke.czx.common.validator.group.QcloudGroup;
import com.suke.czx.common.validator.group.QiniuGroup;
import com.suke.czx.modules.oss.cloud.CloudStorageConfig;
import com.suke.czx.modules.oss.entity.SysAccessoryEntity;
import com.suke.czx.modules.oss.service.SysOssService;
import com.suke.czx.modules.sys.controller.AbstractController;
import com.suke.czx.modules.sys.service.SysConfigService;
import com.suke.czx.modules.sys.service.SysGrowUpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * 文件上传
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController extends AbstractController {
	@Autowired
	private SysOssService sysOssService;
	@Autowired
	private SysGrowUpService sysGrowUpService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysAccessoryEntity> sysAccList = sysOssService.queryAccList(query);
		int total = sysOssService.queryAccTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysAccList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return R.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public R saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}
		

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return R.ok();
	}
	

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		sysGrowUpService.saveFile(file,file.getOriginalFilename(),getUser().getUsername(),"");
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
		return R.ok();
	}


	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody long id){
		SysAccessoryEntity sysAcc=sysOssService.queryAccObject(id);
		File file=new File(sysAcc.getAccessoryAddress());
		if(file.exists()){
			file.delete();
		}
		sysOssService.delete(id);

		return R.ok();
	}

}
