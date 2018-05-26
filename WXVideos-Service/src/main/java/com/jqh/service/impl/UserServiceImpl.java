package com.jqh.service.impl;

import com.jqh.mapper.UsersMapper;
import com.jqh.pojo.Users;
import com.jqh.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper ;

    @Autowired
    private Sid sid ;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String userName) {
        Users user = new Users() ;
        user.setUsername(userName);
        Users result = userMapper.selectOne(user);
        return result==null?false:true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Users user) {
        String userId = sid.nextShort();
        user.setId(userId);
        userMapper.insert(user);
    }
}
