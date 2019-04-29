package com.suke.czx.modules.app.controller;


import com.google.gson.Gson;
import com.suke.czx.common.utils.AppBaseResult;
import com.suke.czx.common.validator.Assert;
import com.suke.czx.modules.app.service.user.AppUserService;
import com.suke.czx.modules.sys.controller.AbstractController;
import com.suke.czx.modules.sys.entity.SysUserEntity;
import com.suke.czx.modules.sys.service.SysUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/app")
public class ApiRegisterController extends AbstractController {

    @Resource(name = "appUserService")
    private AppUserService appUserService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 注册
     */
    @PostMapping("register")
    public AppBaseResult register(@RequestBody AppBaseResult appBaseResult) throws Exception {
        System.out.println(appBaseResult.toString());
        HashMap<String,Object> pd = new Gson().fromJson(appBaseResult.toString(),HashMap.class);
        JSONObject jsonObject=JSONObject.fromObject(pd.get("data"));
        Assert.isNull(jsonObject.get("mobile"), "手机号不能为空");
        Assert.isNull(jsonObject.get("password"), "密码不能为空");
        SysUserEntity user =new SysUserEntity();
        user.setUsername(String.valueOf(jsonObject.get("username")));
        user.setPassword(String.valueOf(jsonObject.get("password")));
        user.setSex(jsonObject.getLong("sex"));
        user.setAge(jsonObject.getLong("age"));
        user.setEmail(String.valueOf(jsonObject.get("email")));
        user.setMobile(String.valueOf(jsonObject.get("mobile")));
        user.setStatus(1);
        //得到用戶角色信息
        Long roleId = jsonObject.getLong("roleId");
        List<Long> roleIdList= new ArrayList<Long>();
        roleIdList.add(roleId);
        user.setRoleIdList(roleIdList);
       // ValidatorUtils.validateEntity(user, AddGroup.class);

        //user.setCreateUserId(getUserId());
        sysUserService.save(user);
       // appUserService.save(pd);
        return AppBaseResult.success();
    }

    public static void main (String [] args) {
        //String s="data='{\"username\":\"lixia\",\"password\":\"312125\",\"sex\":\"0\",\"age\":\"12\",\"email\":\"522248881@qq.com\",\"mobile\":\"18336362519\",\"status\":\"1\",\"roleIdList\":\"['1']\"}'";
         List<String> list=new ArrayList<String>();
         list.add("1");
         Map<String,Object> map=new HashMap<String,Object>();
         map.put("role",list);
         System.out.println(JSONObject.fromObject(map).toString());
    }
}
