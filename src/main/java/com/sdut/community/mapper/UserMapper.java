package com.sdut.community.mapper;

import com.sdut.community.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    public User selectUserByEmail(String email);

    public User selectUserByUsername(String username);

    public User selectUserById(int id);

    public int insertUser(User user);

    public int updateUser(User user);

    public int updateUserHead(User user);

    public int deleteUser(int id);

    /**
     * 获取用户头像
     * @param uid
     * @return
     */
    @Select("select pic from user where id=#{uid}")
    public String getHeadByUid(int uid);


}
