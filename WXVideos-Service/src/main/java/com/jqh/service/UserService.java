package com.jqh.service;

import com.jqh.pojo.Users;

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
}
