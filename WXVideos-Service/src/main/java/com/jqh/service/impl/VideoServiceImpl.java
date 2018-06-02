package com.jqh.service.impl;

import com.jqh.mapper.VideosMapper;
import com.jqh.pojo.Videos;
import com.jqh.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid ;

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
}
