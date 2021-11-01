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
    public int insertUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(int id) {
        return 0;
    }
}
