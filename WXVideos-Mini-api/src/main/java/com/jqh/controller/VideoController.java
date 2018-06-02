package com.jqh.controller;

import com.jqh.pojo.Users;
import com.jqh.utils.JSONResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
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
@Api(value = "视频相关的接口",tags = {"视频相关的controller"})
@RequestMapping("/video")
public class VideoController extends BaseController{

    @ApiOperation(value = "用户上传视频",notes = "用户上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id" ,required = true
                    ,dataType = "String", paramType = "form"),
            @ApiImplicitParam(name="bgmId",value = "背景音乐id" ,required = false
                    ,dataType = "String", paramType = "form"),
            @ApiImplicitParam(name="videoSeconds",value = "视频长度" ,required = true
                    ,dataType = "Double", paramType = "form"),
            @ApiImplicitParam(name="videoWidth",value = "视频宽度" ,required = true
                    ,dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name="videoHeight",value = "视频高度" ,required = true
                    ,dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name="desc",value = "描述" ,required = false
                    ,dataType = "String", paramType = "form"),

    })

    @PostMapping(value="/upload", headers="content-type=multipart/form-data")
    public JSONResult upload(String userId,
                                 String bgmId,double videoSeconds, int videoWidth, int videoHeight,
                                 String desc,
                             @ApiParam(value="短视频", required=true) MultipartFile file) throws  Exception{
        if(StringUtils.isEmpty(userId)){
            return JSONResult.errorMsg("用户id不能为空");
        }
        //http://192.168.1.102/wxvideos/180601HCP7AXFXKP/face/wxf67373492f3bce91.o6zAJsz16WVof6bXd8lCMMadLBs4.eq6e6pNytu5Cee1acacaf765dab56e77cfcd85d4a082.jpeg
        String fileSpace = baseConfig.getResBasePath();
        String uploadPathDB = "/"+userId+"/video";
        FileOutputStream fileOutputStream = null ;
        InputStream inputStream = null ;
        try {
            if (file != null ) {
                String fileName = file.getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)) {
                    String finalVideoPath = fileSpace + uploadPathDB + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null ||
                            !outFile.getParentFile().isDirectory()) {
                        // 创建目录
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
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

        return JSONResult.ok(uploadPathDB);

    }
}
