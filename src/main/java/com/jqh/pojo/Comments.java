package com.jqh.pojo;

import java.util.Date;
import javax.persistence.*;

public class Comments {
    /**
     * 全局唯一id
     */
    @Id
    private String id;

    /**
     * 父留言的id
     */
    @Column(name = "father_comment_id")
    private String fatherCommentId;

    /**
     * 给谁回复
     */
    @Column(name = "to_user_id")
    private String toUserId;

    /**
     * 视频id
     */
    @Column(name = "video_id")
    private String videoId;

    /**
     * 留言用户id
     */
    @Column(name = "from_user_id")
    private String fromUserId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 留言信息
     */
    private String comment;

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
     * 获取父留言的id
     *
     * @return father_comment_id - 父留言的id
     */
    public String getFatherCommentId() {
        return fatherCommentId;
    }

    /**
     * 设置父留言的id
     *
     * @param fatherCommentId 父留言的id
     */
    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    /**
     * 获取给谁回复
     *
     * @return to_user_id - 给谁回复
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * 设置给谁回复
     *
     * @param toUserId 给谁回复
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * 获取视频id
     *
     * @return video_id - 视频id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * 设置视频id
     *
     * @param videoId 视频id
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * 获取留言用户id
     *
     * @return from_user_id - 留言用户id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置留言用户id
     *
     * @param fromUserId 留言用户id
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
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

    /**
     * 获取留言信息
     *
     * @return comment - 留言信息
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置留言信息
     *
     * @param comment 留言信息
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}