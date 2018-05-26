package com.jqh.controller;

import com.jqh.pojo.Users;
import com.jqh.service.UserService;
import com.jqh.utils.JSONResult;
import com.jqh.utils.MD5Utils;
import com.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "用户注册和登录的接口",tags = {"注册和登录的controller"})
public class RegistLoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册",notes = "用户注册的接口", protocols = "200 表示成功\n 500 表示错误，错误信息在msg\n 501 bean验证错误，错误在map中返回\n 502 拦截到用户token错误\n 502 拦截到用户token错误\n 抛出异常信息")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody Users user){
        if(StringUtils.isEmpty(user.getUsername()) ||
                StringUtils.isEmpty(user.getPassword())){
            return JSONResult.errorMsg("用户名和密码不能为空");
        }

        boolean usernameIsExist = userService.queryUserNameIsExist(user.getUsername());

        if(!usernameIsExist){
            try {
                user.setNickname(user.getUsername());
                user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
                user.setFansCounts(0);
                user.setFollowCounts(0);
                user.setReceiveLikeCounts(0);
                userService.save(user);
            }catch (Exception e){
                return JSONResult.errorException(e.getMessage());
            }
        }else{
            return JSONResult.errorMsg("用户名已经存在");
        }

        user.setPassword("");
        return JSONResult.ok(user);
    }
}
