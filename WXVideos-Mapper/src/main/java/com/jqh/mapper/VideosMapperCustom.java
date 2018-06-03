package com.jqh.mapper;

import com.jqh.pojo.Videos;
import com.jqh.utils.MyMapper;
import com.jqh.vo.VideosVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {

    public List<VideosVo> queryAllVideos1();

    public List<VideosVo> queryAllVideos(@Param("videoDesc") String videoDesc);
}