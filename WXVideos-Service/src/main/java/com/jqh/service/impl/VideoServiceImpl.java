package com.jqh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jqh.mapper.SearchRecordsMapper;
import com.jqh.mapper.VideosMapper;
import com.jqh.mapper.VideosMapperCustom;
import com.jqh.pojo.SearchRecords;
import com.jqh.pojo.Videos;
import com.jqh.service.VideoService;
import com.jqh.utils.PageResult;
import com.jqh.vo.VideosVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        if(isSaveRecord != null && isSaveRecord == 1
                && !StringUtils.isEmpty(desc)){
            // 保存查询记录
            SearchRecords recode = new SearchRecords();
            recode.setId(sid.nextShort());
            recode.setConent(desc);
            searchRecordsMapper.insert(recode);
        }
        PageHelper.startPage(page,pageSize);
        List<VideosVo> list = videosMapperCustom.queryAllVideos(desc);
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
}
