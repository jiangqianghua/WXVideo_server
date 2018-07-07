package com.jqh.mapper;

import com.jqh.pojo.Users;
import com.jqh.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

    public void addReceiveLikeCount(String userId);

    public void reduceReceiveLikeCount(String userId);
}