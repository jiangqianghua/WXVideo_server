package com.jqh.service;

import com.jqh.pojo.Videos;
import com.jqh.utils.PageResult;

import java.util.List;

public interface VideoService {

    /**
     * 保存视频
     * @param video
     */
    public String saveVideo(Videos video);

    /**
     * 修改封面
     * @param videoId
     * @param coverPath
     * @return
     */
    public void updateVideoCover(String videoId,String coverPath);

    /**
     *  获取视频列表,分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult getAllVideos(Integer page,Integer pageSize);


    /**
     *  获取视频列表,分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public PageResult getAllVideos(Videos video,Integer isSaveRecord,Integer page,Integer pageSize);

    /**
     * 获取热搜词汇
     * @return
     */
    public List<String> getHotWords();
}
