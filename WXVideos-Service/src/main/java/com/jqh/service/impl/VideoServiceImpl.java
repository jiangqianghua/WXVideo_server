package com.jqh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jqh.mapper.*;
import com.jqh.pojo.Comments;
import com.jqh.pojo.SearchRecords;
import com.jqh.pojo.UsersLikeVideos;
import com.jqh.pojo.Videos;
import com.jqh.service.VideoService;
import com.jqh.utils.PageResult;
import com.jqh.utils.TimeAgoUtils;
import com.jqh.vo.CommentsVo;
import com.jqh.vo.VideosVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom ;

    @Autowired
    private Sid sid ;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper ;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper ;

    @Autowired
    private UsersMapper usersMapper ;

    @Autowired
    private CommentsMapper commentsMapper ;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {
        video.setId(sid.nextShort());
        videosMapper.insertSelective(video);
        return video.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideoCover(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PageResult getAllVideos(Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);
        List<VideosVo> list = videosMapperCustom.queryAllVideos1();
        PageInfo<VideosVo> pageList = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setPage(page);
        pageResult.setTotal(pageList.getPages());
        pageResult.setRows(list);
        pageResult.setRecords(pageList.getTotal());
        return pageResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PageResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if(isSaveRecord != null && isSaveRecord == 1
                && !StringUtils.isEmpty(desc)){
            // 保存查询记录
            SearchRecords recode = new SearchRecords();
            recode.setId(sid.nextShort());
            recode.setConent(desc);
            searchRecordsMapper.insert(recode);
        }

        PageHelper.startPage(page,pageSize);
        List<VideosVo> list = videosMapperCustom.queryAllVideos(desc,userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setPage(page);
        pageResult.setTotal(pageList.getPages());
        pageResult.setRows(list);
        pageResult.setRecords(pageList.getTotal());
        return pageResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotWords(){
        return searchRecordsMapper.getHotWords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreateId) {
        String likeId = sid.nextShort();
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setId(likeId);
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        usersLikeVideosMapper.insert(usersLikeVideos);

        videosMapperCustom.addVideoLikeCount(videoId);

        usersMapper.addReceiveLikeCount(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreateId) {
        // 删除关系
        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("videoId",videoId);
        usersLikeVideosMapper.deleteByExample(example);

        videosMapperCustom.reduceVideoLikeCount(videoId);

        usersMapper.reduceReceiveLikeCount(userId);
    }

    @Override
    public PageResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VideosVo> list = videosMapperCustom.queryMyLikeVideos(userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(list);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageList.getPages());
        pageResult.setRows(list);
        pageResult.setPage(page);
        pageResult.setRecords(pageList.getTotal());
        return pageResult;
    }

    @Override
    public PageResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VideosVo> list = videosMapperCustom.queryMyFollowVideos(userId);
        PageInfo<VideosVo> pageList = new PageInfo<>(list);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(pageList.getPages());
        pageResult.setRows(list);
        pageResult.setPage(page);
        pageResult.setRecords(pageList.getTotal());
        return pageResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentsMapper.insert(comment);
    }


    @Override
    public PageResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<CommentsVo> list = commentsMapper.queryComments(videoId);

        for(CommentsVo commentsVo : list){
            String timeAgo = TimeAgoUtils.format(commentsVo.getCreateTime());
            commentsVo.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVo> pageList = new PageInfo<>(list);
        PageResult grid = new PageResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
