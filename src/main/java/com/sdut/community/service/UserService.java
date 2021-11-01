package com.sdut.community.service;

import com.sdut.community.model.domain.User;

public interface UserService {

    public User selectUserByEmail(String email);

    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUser(int id);


}
