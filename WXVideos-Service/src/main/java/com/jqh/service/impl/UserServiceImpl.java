package com.jqh.service.impl;

import com.jqh.mapper.UsersFansMapper;
import com.jqh.mapper.UsersLikeVideosMapper;
import com.jqh.mapper.UsersMapper;
import com.jqh.pojo.Users;
import com.jqh.pojo.UsersFans;
import com.jqh.pojo.UsersLikeVideos;
import com.jqh.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper ;

    @Autowired
    private UsersFansMapper usersFansMapper ;

    @Autowired
    private Sid sid ;


    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id",user.getId());
        userMapper.updateByExampleSelective(user,userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserById(String userId) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id",userId);
        return userMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {

        if(StringUtils.isEmpty(userId) ||StringUtils.isEmpty(videoId))
            return false ;
        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("videoId",videoId);
        List<UsersLikeVideos> likeVideosList =  usersLikeVideosMapper.selectByExample(example);
        if(likeVideosList != null && likeVideosList.size() > 0){
            return true ;
        }
        return false;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserFanRelation(String userId, String fanId) {
        String resId = sid.nextShort();
        UsersFans usersFans = new UsersFans();
        usersFans.setFanId(fanId);
        usersFans.setUserId(userId);
        usersFans.setId(resId);
        usersFansMapper.insert(usersFans);

        userMapper.addFansCount(userId);
        userMapper.addFollersCount(fanId);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserFanRelation(String userId, String fanId) {
        Example example = new Example(UsersFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("fanId",fanId);

        usersFansMapper.deleteByExample(example);

        userMapper.reduceFansCount(userId);
        userMapper.reduceFollersCount(fanId);
    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {
        Example example = new Example(UsersFans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("fanId",fanId);
        List<UsersFans> usersFansList = usersFansMapper.selectByExample(example);
        if(usersFansList != null && usersFansList.size() > 0)
            return true ;
        return false ;
    }
}
