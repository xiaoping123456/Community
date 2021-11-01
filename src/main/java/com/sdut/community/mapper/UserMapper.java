package com.sdut.community.mapper;

import com.sdut.community.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User selectUserByEmail(String email);

    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUser(int id);

}
