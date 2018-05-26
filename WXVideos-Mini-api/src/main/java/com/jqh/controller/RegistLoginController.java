package com.jqh.controller;

import com.jqh.pojo.Users;
import com.jqh.service.UserService;
import com.jqh.utils.JSONResult;
import com.jqh.utils.MD5Utils;
import com.jqh.vo.UsersVo;
import com.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户注册和登录的接口",tags = {"注册和登录的controller"})
public class RegistLoginController extends BaseController{

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册",notes = "用户注册的接口")
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

        //  保存到redis中
//        String uniqueToken = UUID.randomUUID().toString();
//        redis.set(USER_REDIS_SESSION+":"+user.getId(),uniqueToken,1000*60*30);// 30分钟
//
//        UsersVo usersVo = new UsersVo();
//        BeanUtils.copyProperties(user,usersVo);
//        usersVo.setUserToken(uniqueToken);

        UsersVo usersVo = setUserRedisSessionToken(user);
        return JSONResult.ok(usersVo);
    }

    public UsersVo setUserRedisSessionToken(Users user){
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION+":"+user.getId(),uniqueToken,1000*60*30);// 30分钟

        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user,usersVo);
        usersVo.setUserToken(uniqueToken);
        return usersVo;
    }

    @ApiOperation(value = "用户登录",notes = "用户登录的接口")
    @PostMapping("/login")
    public JSONResult login(@RequestBody Users user) throws Exception{
        if(StringUtils.isEmpty(user.getUsername()) ||
                StringUtils.isEmpty(user.getPassword())){
            return JSONResult.errorMsg("登录失败,用户名和密码不能为空");
        }

        Users userResult = userService.queryUserForLogin(user.getUsername(),
                MD5Utils.getMD5Str(user.getPassword()));

        if(userResult != null){
            userResult.setPassword("");

            UsersVo usersVo = setUserRedisSessionToken(user);

            return JSONResult.ok(usersVo);
        }else{
            return JSONResult.errorMsg("用户或密码不正确");
        }

    }
}
