package com.sdut.community.service.impl;

import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.domain.User;
import com.sdut.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public User selectUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        if(userMapper.updateUser(user)!=0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserHead(User user) {

        if(userMapper.updateUserHead(user)!=0){
            return true;
        }
        return false;
    }

    @Override
    public int deleteUser(int id) {
        return 0;
    }
}
