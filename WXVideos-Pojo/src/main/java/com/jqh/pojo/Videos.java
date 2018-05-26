package com.jqh.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Videos {
    /**
     * 全局唯一id
     */
    @Id
    private String id;

    /**
     * 创建视频的用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 背景音乐的id
     */
    @Column(name = "audio_id")
    private String audioId;

    /**
     * 视频描述
     */
    @Column(name = "video_desc")
    private String videoDesc;

    /**
     * 视频地址
     */
    @Column(name = "video_path")
    private String videoPath;

    /**
     * 视频播放秒数
     */
    @Column(name = "video_seconds")
    private Float videoSeconds;

    /**
     * 视频宽度
     */
    @Column(name = "video_width")
    private Integer videoWidth;

    /**
     * 视频高度
     */
    @Column(name = "video_height")
    private Integer videoHeight;

    /**
     * 封面
     */
    @Column(name = "cover_path")
    private String coverPath;

    /**
     * 点赞数量
     */
    @Column(name = "like_counts")
    private Integer likeCounts;

    /**
     * 状态 1 发布成功  2 禁止播放
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取全局唯一id
     *
     * @return id - 全局唯一id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置全局唯一id
     *
     * @param id 全局唯一id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取创建视频的用户id
     *
     * @return user_id - 创建视频的用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置创建视频的用户id
     *
     * @param userId 创建视频的用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取背景音乐的id
     *
     * @return audio_id - 背景音乐的id
     */
    public String getAudioId() {
        return audioId;
    }

    /**
     * 设置背景音乐的id
     *
     * @param audioId 背景音乐的id
     */
    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    /**
     * 获取视频描述
     *
     * @return video_desc - 视频描述
     */
    public String getVideoDesc() {
        return videoDesc;
    }

    /**
     * 设置视频描述
     *
     * @param videoDesc 视频描述
     */
    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    /**
     * 获取视频地址
     *
     * @return video_path - 视频地址
     */
    public String getVideoPath() {
        return videoPath;
    }

    /**
     * 设置视频地址
     *
     * @param videoPath 视频地址
     */
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    /**
     * 获取视频播放秒数
     *
     * @return video_seconds - 视频播放秒数
     */
    public Float getVideoSeconds() {
        return videoSeconds;
    }

    /**
     * 设置视频播放秒数
     *
     * @param videoSeconds 视频播放秒数
     */
    public void setVideoSeconds(Float videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    /**
     * 获取视频宽度
     *
     * @return video_width - 视频宽度
     */
    public Integer getVideoWidth() {
        return videoWidth;
    }

    /**
     * 设置视频宽度
     *
     * @param videoWidth 视频宽度
     */
    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    /**
     * 获取视频高度
     *
     * @return video_height - 视频高度
     */
    public Integer getVideoHeight() {
        return videoHeight;
    }

    /**
     * 设置视频高度
     *
     * @param videoHeight 视频高度
     */
    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    /**
     * 获取封面
     *
     * @return cover_path - 封面
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     * 设置封面
     *
     * @param coverPath 封面
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     * 获取点赞数量
     *
     * @return like_counts - 点赞数量
     */
    public Integer getLikeCounts() {
        return likeCounts;
    }

    /**
     * 设置点赞数量
     *
     * @param likeCounts 点赞数量
     */
    public void setLikeCounts(Integer likeCounts) {
        this.likeCounts = likeCounts;
    }

    /**
     * 获取状态 1 发布成功  2 禁止播放
     *
     * @return status - 状态 1 发布成功  2 禁止播放
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1 发布成功  2 禁止播放
     *
     * @param status 状态 1 发布成功  2 禁止播放
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}