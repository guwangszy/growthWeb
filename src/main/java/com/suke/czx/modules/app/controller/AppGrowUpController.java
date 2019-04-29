package com.suke.czx.modules.app.controller;


import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.utils.BASE64DecodedMultipartFile;
import com.suke.czx.common.utils.PageUtils;
import com.suke.czx.common.utils.Query;
import com.suke.czx.common.validator.Assert;
import com.suke.czx.modules.sys.controller.AbstractController;
import com.suke.czx.modules.sys.entity.SysAdviceEntity;
import com.suke.czx.modules.sys.service.SysGrowUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Api(value = "API - AppGradeController ", description = "APP版本管理")
@RestController
@RequestMapping("/app")
public class AppGrowUpController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysGrowUpService sysGrowUpService;


    /**
     * 成长册列表
     */
    @ApiOperation(value="成长册列表", notes="成长册列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
    @PostMapping("/growUp/list")
    public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{

        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
        JSONObject jsonObject=JSONObject.fromObject(params.get("data"));
        //查询列表数据
        Query query = new Query(jsonObject);
        query.isPaging(true);
        List<Map<String,Object>> growUpList = sysGrowUpService.queryList(query);

        /*List<HashMap<String,Object>> appUpdateList = appUpdateService.queryList(query);*/
        PageUtils pageUtil = new PageUtils(growUpList, query.getTotle(), query.getLimit(), query.getPage());
        return AppBaseResult.success().setEncryptData(pageUtil);
    }

    /**
     * 创建班级
     */
    @ApiOperation(value="新增成长册", notes="新增成长册")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
    @PostMapping("/growUp/save")
    public AppBaseResult save(@RequestBody AppBaseResult appBaseResult)throws Exception{
        HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
        JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
        String fileCode =jsonObject.getString("fileCode");
        String fileName =jsonObject.getString("fileName");
        if(StringUtils.isNotEmpty(fileCode)&&StringUtils.isNotEmpty(fileName)) {
            //BASE64DecodedMultipartFile.base64ToMultipart();

        }
        sysGrowUpService.save(jsonObject);
        return AppBaseResult.success();
    }

}
