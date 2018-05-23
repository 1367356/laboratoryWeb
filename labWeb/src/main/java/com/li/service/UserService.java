package com.li.service;


import com.li.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User findUserByUserName(String userName);
    public String registerUserAccount(User user);

    int deleteUser(int uid);

    List<User> selectUserList(int page);
}
