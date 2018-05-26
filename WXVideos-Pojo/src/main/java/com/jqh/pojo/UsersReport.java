package com.jqh.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "users_report")
public class UsersReport {
    /**
     * 全局唯一id
     */
    @Id
    private String id;

    /**
     * 被举报的用户id
     */
    @Column(name = "deal_user_id")
    private String dealUserId;

    /**
     * 被举报的视频id
     */
    @Column(name = "deal_video_id")
    private String dealVideoId;

    /**
     * 举报主题
     */
    private String title;

    /**
     * 举报内容
     */
    private String content;

    /**
     * 举报人的id
     */
    private String userid;

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
     * 获取被举报的用户id
     *
     * @return deal_user_id - 被举报的用户id
     */
    public String getDealUserId() {
        return dealUserId;
    }

    /**
     * 设置被举报的用户id
     *
     * @param dealUserId 被举报的用户id
     */
    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    /**
     * 获取被举报的视频id
     *
     * @return deal_video_id - 被举报的视频id
     */
    public String getDealVideoId() {
        return dealVideoId;
    }

    /**
     * 设置被举报的视频id
     *
     * @param dealVideoId 被举报的视频id
     */
    public void setDealVideoId(String dealVideoId) {
        this.dealVideoId = dealVideoId;
    }

    /**
     * 获取举报主题
     *
     * @return title - 举报主题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置举报主题
     *
     * @param title 举报主题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取举报内容
     *
     * @return content - 举报内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置举报内容
     *
     * @param content 举报内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取举报人的id
     *
     * @return userid - 举报人的id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置举报人的id
     *
     * @param userid 举报人的id
     */
    public void setUserid(String userid) {
        this.userid = userid;
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