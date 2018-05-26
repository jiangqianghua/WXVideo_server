package com.jqh.pojo;

import javax.persistence.Column;
import javax.persistence.Id;

public class Users {
    /**
     * 全局唯一id
     */
    @Id
    private String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像连接
     */
    @Column(name = "face_image")
    private String faceImage;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 粉丝数量
     */
    @Column(name = "fans_counts")
    private Integer fansCounts;

    /**
     * 关注数量
     */
    @Column(name = "follow_counts")
    private Integer followCounts;

    /**
     * 关注作品的数量
     */
    @Column(name = "receive_like_counts")
    private Integer receiveLikeCounts;

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
     * 获取用户名称
     *
     * @return username - 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名称
     *
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取头像连接
     *
     * @return face_image - 头像连接
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * 设置头像连接
     *
     * @param faceImage 头像连接
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取粉丝数量
     *
     * @return fans_counts - 粉丝数量
     */
    public Integer getFansCounts() {
        return fansCounts;
    }

    /**
     * 设置粉丝数量
     *
     * @param fansCounts 粉丝数量
     */
    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    /**
     * 获取关注数量
     *
     * @return follow_counts - 关注数量
     */
    public Integer getFollowCounts() {
        return followCounts;
    }

    /**
     * 设置关注数量
     *
     * @param followCounts 关注数量
     */
    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    /**
     * 获取关注作品的数量
     *
     * @return receive_like_counts - 关注作品的数量
     */
    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    /**
     * 设置关注作品的数量
     *
     * @param receiveLikeCounts 关注作品的数量
     */
    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }
}