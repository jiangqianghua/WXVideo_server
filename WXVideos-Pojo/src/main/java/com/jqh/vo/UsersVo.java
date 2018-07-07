package com.jqh.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;

@ApiModel(value = "用户对象",description = "这是用户对象")
public class UsersVo {
    /**
     * 全局唯一id
     */
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(hidden = true)
    private String userToken ;

    /**
     * 是否被当前用户关注
     */
    private boolean isFollow ;

    /**
     * 用户名称
     */
    @ApiModelProperty(value="用户名",name="username",example = "jiangqianghua",required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码",name="password",example = "123456",required = true)
    @JsonIgnore
    private String password;

    /**
     * 头像连接
     */
    @ApiModelProperty(hidden = true)
    private String faceImage;

    /**
     * 昵称
     */
    @ApiModelProperty(hidden = true)
    private String nickname;

    /**
     * 粉丝数量
     */
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /**
     * 关注数量
     */
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /**
     * 关注作品的数量
     */
    @ApiModelProperty(hidden = true)
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}