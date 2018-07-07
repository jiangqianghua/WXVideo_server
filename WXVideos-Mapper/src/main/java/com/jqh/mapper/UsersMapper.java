package com.jqh.mapper;

import com.jqh.pojo.Users;
import com.jqh.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

    public void addReceiveLikeCount(String userId);

    public void reduceReceiveLikeCount(String userId);

    /**
     * 添加粉丝数量
     * @param userId
     */
    public void addFansCount(String userId);

    /**
     * 减少粉丝数量
     * @param userId
     */
    public void reduceFansCount(String userId);


    /**
     * 增加关注数量
     * @param userId
     */
    public void addFollersCount(String userId);

    /**
     * 减少关注数量
     * @param userId
     */
    public void reduceFollersCount(String userId);


}