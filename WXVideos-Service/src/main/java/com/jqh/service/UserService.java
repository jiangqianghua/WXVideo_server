package com.jqh.service;

import com.jqh.pojo.Users;
import com.jqh.pojo.UsersReport;

public interface UserService {
    /**
     * 查询用户名是否存储在
     * @param userName
     * @return
     */
    public boolean queryUserNameIsExist(String userName);

    /**
     * 保存用户对象
     * @param user
     */
    public void save(Users user);

    public Users queryUserForLogin(String username,String password);

    /**
     * 修改用户信息
     * @param users
     */
    public void updateUserInfo(Users users);

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserById(String userId);


    /**
     * 查询用户是否喜欢视频
     * @param userId
     * @param videoId
     * @return
     */
    public  boolean isUserLikeVideo(String userId , String videoId);

    /**
     * 添加用户和粉丝的关系
     * @param userId
     * @param fanId
     */
    public void saveUserFanRelation(String userId , String fanId);

    /**
     * 取消关注
     * @param userId
     * @param fanId
     */
    public void deleteUserFanRelation(String userId , String fanId);

    /**
     * 查询是否被关注
     * @param userId
     * @param fanId
     */
    public boolean queryIfFollow(String userId ,String fanId);

    /**
     * 举报用户
     * @param usersReport
     */
    public void reportUser(UsersReport usersReport);
}
