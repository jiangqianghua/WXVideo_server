package com.jqh.service;

import com.jqh.pojo.Videos;

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
}
