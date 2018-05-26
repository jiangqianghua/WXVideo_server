package com.jqh.service.impl;

import com.jqh.mapper.UsersMapper;
import com.jqh.pojo.Users;
import com.jqh.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {

        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        Users result = userMapper.selectOneByExample(userExample);
        return result ;
    }
}
