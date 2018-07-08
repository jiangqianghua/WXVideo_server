package com.jqh.controller;

import com.jqh.pojo.Users;
import com.jqh.pojo.UsersReport;
import com.jqh.service.UserService;
import com.jqh.utils.JSONResult;
import com.jqh.vo.PublisherVideo;
import com.jqh.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@Api(value = "用户的接口",tags = {"用户的controller"})
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像",notes = "用户上传头像")
    @ApiImplicitParam(name="userId",value = "用户id" ,required = true
            ,dataType = "String", paramType = "query")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(String userId,
                                 @RequestParam("file") MultipartFile[] files) throws  Exception{
        if(StringUtils.isEmpty(userId)){
            return JSONResult.errorMsg("用户id不能为空");
        }
        //http://192.168.1.102/wxvideos/180601HCP7AXFXKP/face/wxf67373492f3bce91.o6zAJsz16WVof6bXd8lCMMadLBs4.eq6e6pNytu5Cee1acacaf765dab56e77cfcd85d4a082.jpeg
        //http://192.168.1.102:8081/180601HCP7AXFXKP/face/wxf67373492f3bce91.o6zAJsz16WVof6bXd8lCMMadLBs4.eq6e6pNytu5Cee1acacaf765dab56e77cfcd85d4a082.jpeg
        String fileSpace = baseConfig.getResBasePath();
        String uploadPathDB = "/"+userId+"/face";
        FileOutputStream fileOutputStream = null ;
        InputStream inputStream = null ;
        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)) {
                    String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null ||
                            !outFile.getParentFile().isDirectory()) {
                        // 创建目录
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            }else{
                //return JSONResult.errorMsg("上传出错...");
            }
        }catch (Exception e){
            e.printStackTrace();
            return JSONResult.errorMsg(e.getMessage());
        }finally {
            if(fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }
        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);
        return JSONResult.ok(uploadPathDB);

    }


    @ApiOperation(value = "用户查询",notes = "用户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true
                    , dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "哪个用户来查询", required = true
                    , dataType = "String", paramType = "query")
    })
    @PostMapping("/query")
    public JSONResult query(String userId,String fanId) throws  Exception{
        if(StringUtils.isEmpty(userId)){
            return JSONResult.errorMsg("用户id不能为空");
        }

        Users users = userService.queryUserById(userId);
        if(users == null){
            return JSONResult.errorMsg("用户查询失败");
        }
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users,usersVo);

        boolean isFollow = userService.queryIfFollow(userId,fanId);
        usersVo.setFollow(isFollow);

        return JSONResult.ok(usersVo);

    }
    @ApiOperation(value = "查询发布者信息",notes = "查询发布者信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="loginUserId",value = "取消点赞用户的id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="videoId",value = "被取消点赞视频id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="publishUserId",value = "被取消点赞视频创建的用户id" ,required = false
                    ,dataType = "String", paramType = "query")
    })
    @PostMapping("/queryPublisher")
    public JSONResult queryPublisher(String loginUserId ,String videoId,
                                     String publishUserId) throws Exception{
        // 未登陆也能查询
        if(StringUtils.isEmpty(publishUserId)){
            return JSONResult.errorMsg("参数不能为空");
        }

        Users users = userService.queryUserById(publishUserId);
        UsersVo publisher = new UsersVo();
        BeanUtils.copyProperties(users,publisher);
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId,videoId);

        PublisherVideo bean = new PublisherVideo();
        bean.setUsersVo(publisher);
        bean.setUserLikeVideo(userLikeVideo);

        return JSONResult.ok(bean);
    }

    @ApiOperation(value = "关注用户",notes = "关注用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "被关注人的id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="userId",value = "关注人的id" ,required = false
                    ,dataType = "String", paramType = "query"),
    })
    @PostMapping("/beyourfans")
    public JSONResult beyourfans(String userId,String fanId) throws Exception{
        if(StringUtils.isEmpty(userId) ||
                StringUtils.isEmpty(fanId)){
            return JSONResult.errorMsg("");
        }

        userService.saveUserFanRelation(userId,fanId);
        return JSONResult.ok("关注成功");
    }

    @ApiOperation(value = "取消关注用户",notes = "取消关注用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "被取消关注人的id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="userId",value = "取消关注人的id" ,required = false
                    ,dataType = "String", paramType = "query"),
    })
    @PostMapping("/dontbeyourfans")
    public JSONResult dontbeyourfans(String userId,String fanId) throws Exception{
        if(StringUtils.isEmpty(userId) ||
                StringUtils.isEmpty(fanId)){
            return JSONResult.errorMsg("");
        }

        userService.deleteUserFanRelation(userId,fanId);
        return JSONResult.ok("取消关注成功");
    }

    /**
     * 举报用户
     * @param usersReport
     * @return
     * @throws Exception
     */
    @PostMapping("/reportUser")
    public JSONResult reportUser(@RequestBody UsersReport usersReport) throws  Exception{
        userService.reportUser(usersReport);
        return JSONResult.ok("举报成功");
    }

}
