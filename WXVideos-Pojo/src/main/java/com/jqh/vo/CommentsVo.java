package com.jqh.vo;

import java.util.Date;

public class CommentsVo {

    /**
     * 全局唯一id
     */
    private String id;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 留言用户id
     */
    private String fromUserId;

    private Date createTime;

    /**
     * 留言信息
     */
    private String comment;
    /**
     * face image
     */
    private String faceImage ;
    /**
     * nick name
     */
    private String nickname ;

    private String toNickName ;

    private String timeAgoStr ;

    public String getTimeAgoStr() {
        return timeAgoStr;
    }

    public void setTimeAgoStr(String timeAgoStr) {
        this.timeAgoStr = timeAgoStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }
}
