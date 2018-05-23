package com.li.dao;

import com.li.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public User findByUserName(String username);

    public int save(@Param("user") User user);

    int deleteUser(int uid);

    List<User> selectUserList(int page);
}
