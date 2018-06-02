package com.jqh.controller;

import com.jqh.pojo.Users;
import com.jqh.service.UserService;
import com.jqh.utils.JSONResult;
import com.jqh.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @ApiImplicitParam(name="userId",value = "用户id" ,required = true
            ,dataType = "String", paramType = "query")
    @PostMapping("/query")
    public JSONResult query(String userId) throws  Exception{
        if(StringUtils.isEmpty(userId)){
            return JSONResult.errorMsg("用户id不能为空");
        }

        Users users = userService.queryUserById(userId);
        if(users == null){
            return JSONResult.errorMsg("用户查询失败");
        }
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(users,usersVo);

        return JSONResult.ok(usersVo);

    }
}
