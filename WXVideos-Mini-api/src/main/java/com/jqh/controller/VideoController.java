package com.jqh.controller;

import com.jqh.enums.VideoStatusEnum;
import com.jqh.pojo.Bgm;
import com.jqh.pojo.Comments;
import com.jqh.pojo.Users;
import com.jqh.pojo.Videos;
import com.jqh.service.BgmService;
import com.jqh.service.VideoService;
import com.jqh.utils.FetchVideoCover;
import com.jqh.utils.JSONResult;
import com.jqh.utils.MergeVideoMp3;
import com.jqh.utils.PageResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

@RestController
@Api(value = "视频相关的接口",tags = {"视频相关的controller"})
@RequestMapping("/video")
public class VideoController extends BaseController{

    @Autowired
    private BgmService bgmService ;

    @Autowired
    private VideoService videoService ;

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
        String coverPathDB = "/"+userId+"/video" ;
        FileOutputStream fileOutputStream = null ;
        InputStream inputStream = null ;
        String finalVideoPath = "";
        try {
            if (file != null ) {
                String fileName = file.getOriginalFilename();
                String fileNamePrefix = fileName.split("\\.")[0];

                if (!StringUtils.isEmpty(fileName)) {
                    finalVideoPath = fileSpace + uploadPathDB + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB += ("/"+fileNamePrefix+".jpg");
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

        // 判断bgm是否为空，开始合并音视频
        if(!StringUtils.isEmpty(bgmId)){
            Bgm bgm = bgmService.queryBgmById(bgmId);
            if(bgm != null){
                String mp3InputPath = baseConfig.getResBasePath() + bgm.getPath();
                MergeVideoMp3 tools = new MergeVideoMp3("ffmpeg");
                String videoInputPath = finalVideoPath ;

                String videoOutputName = UUID.randomUUID().toString()+".mp4";
                uploadPathDB = "/"+userId+"/video/"+videoOutputName;
                finalVideoPath = baseConfig.getResBasePath()+uploadPathDB;
                tools.convertor(videoInputPath,mp3InputPath,videoSeconds,finalVideoPath);
            }
        }
        System.out.println(uploadPathDB);
        System.out.println(finalVideoPath);

        // 生成截图
        FetchVideoCover videoCover = new FetchVideoCover("ffmpeg");
        videoCover.getCover(finalVideoPath,baseConfig.getResBasePath()+coverPathDB);
        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoSeconds((float)videoSeconds);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setLikeCounts(0);
        video.setCoverPath(coverPathDB);
        String videoId = videoService.saveVideo(video);


        return JSONResult.ok(videoId);
    }


    // 废弃
    @ApiOperation(value = "用户上传封面",notes = "用户上传封面")
    @ApiImplicitParams({
            @ApiImplicitParam(name="videoId",value = "视频id" ,required = true
                    ,dataType = "String", paramType = "form"),
            @ApiImplicitParam(name="userId",value = "用户id" ,required = true
                    ,dataType = "String", paramType = "form")
    })


    @PostMapping(value="/uploadCover", headers="content-type=multipart/form-data")
    public JSONResult uploadCover(String userId,String videoId,
                             @ApiParam(value="视频封面", required=true) MultipartFile file) throws  Exception{
        if(StringUtils.isEmpty(userId)){
            return JSONResult.errorMsg("用户id不能为空");
        }

        if(StringUtils.isEmpty(videoId)){
            return JSONResult.errorMsg("视频id不能为空");
        }
        //http://192.168.1.102/wxvideos/180601HCP7AXFXKP/face/wxf67373492f3bce91.o6zAJsz16WVof6bXd8lCMMadLBs4.eq6e6pNytu5Cee1acacaf765dab56e77cfcd85d4a082.jpeg
        String fileSpace = baseConfig.getResBasePath();
        String uploadPathDB = "/"+userId+"/video";
        FileOutputStream fileOutputStream = null ;
        InputStream inputStream = null ;
        String finalCoverPath = "";
        try {
            if (file != null ) {
                String fileName = file.getOriginalFilename();
                if (!StringUtils.isEmpty(fileName)) {
                    finalCoverPath = fileSpace + uploadPathDB + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalCoverPath);
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

        System.out.println(uploadPathDB);
        System.out.println(finalCoverPath);

        videoService.updateVideoCover(videoId,uploadPathDB);
        return JSONResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "分页查询视频",notes = "分页查询视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "第几页" ,required = false
                    ,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name="pageSize",value = "每页显示记录条数" ,required = false
                    ,dataType = "Integer", paramType = "query")
    })
    @GetMapping(value = "/showAll1")
    public JSONResult showAll1(Integer page , Integer pageSize) throws Exception{
        if(page == null)
            page = 1;
        if(pageSize == null)
            pageSize = 5 ;

        PageResult pageResult = videoService.getAllVideos(page,pageSize);
        return JSONResult.ok(pageResult);
    }

    @ApiOperation(value = "分页查询视频",notes = "分页查询视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "第几页" ,required = false
                    ,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name="pageSize",value = "每页显示记录条数" ,required = false
                    ,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name="isSaveRecord",value = "是否保存记录" ,required = false
                    ,dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/showAll")
    public JSONResult showAll(@RequestBody Videos video,Integer isSaveRecord ,Integer page , Integer pageSize) throws Exception{
        if(page == null)
            page = 1;
        if(pageSize == null)
            pageSize = 5 ;

        PageResult pageResult = videoService.getAllVideos(video,isSaveRecord,page,pageSize);
        return JSONResult.ok(pageResult);
    }

    @ApiOperation(value = "热搜词",notes = "热搜")
    @GetMapping(value = "/hot")
    public JSONResult hot() throws Exception{
        return JSONResult.ok(videoService.getHotWords());
    }

    @ApiOperation(value = "用户点赞喜欢视频",notes = "用户点赞喜欢视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "点赞用户的id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="videoId",value = "被点赞视频id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="videoCreateId",value = "被点赞视频创建的用户id" ,required = false
                    ,dataType = "String", paramType = "query")
    })
    @PostMapping(value="/userLike")
    public JSONResult userLike(String userId, String videoId, String videoCreateId)throws Exception{
        videoService.userLikeVideo(userId,videoId,videoCreateId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户取消点赞喜欢视频",notes = "用户取消点赞喜欢视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "取消点赞用户的id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="videoId",value = "被取消点赞视频id" ,required = false
                    ,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="videoCreateId",value = "被取消点赞视频创建的用户id" ,required = false
                    ,dataType = "String", paramType = "query")
    })
    @PostMapping(value="/userUnLike")
    public JSONResult userUnLike(String userId, String videoId, String videoCreateId)throws Exception{
        videoService.userUnLikeVideo(userId,videoId,videoCreateId);
        return  JSONResult.ok();
    }

    @PostMapping(value="/showMyLike")
    public JSONResult showMyLike(String userId , Integer page ,
                                 Integer pageSize) throws  Exception{
        if(page == null)
            page = 1 ;
        if(pageSize == null)
            pageSize = 5 ;
        PageResult videoList = videoService.queryMyLikeVideos(userId,page,pageSize);
        return JSONResult.ok(videoList);
    }

    @PostMapping(value="/showMyFollow")
    public JSONResult showMyFollow(String userId , Integer page ,
                                 Integer pageSize) throws  Exception{
        if(page == null)
            page = 1 ;
        if(pageSize == null)
            pageSize = 5 ;
        PageResult videoList = videoService.queryMyFollowVideos(userId,page,pageSize);
        return JSONResult.ok(videoList);
    }

    @PostMapping("/saveComment")
    public JSONResult saveComment(@RequestBody Comments comment) throws Exception{
        videoService.saveComment(comment);
        return JSONResult.ok();
    }


    @PostMapping(value="/getVideoComments")
    public JSONResult getVideoComments(String videoId , Integer page ,
                                 Integer pageSize) throws  Exception{
        if(page == null)
            page = 1 ;
        if(pageSize == null)
            pageSize = 5 ;
        PageResult commentList = videoService.getAllComments(videoId,page,pageSize);
        return JSONResult.ok(commentList);
    }

//    public JSONResult getVideoComments(String videoId, Integer page ,Integer pageSize) throws Exception{
//        if(StringUtils.isEmpty(videoId)){
//            return JSONResult.ok();
//        }
//        return
//    }



}
