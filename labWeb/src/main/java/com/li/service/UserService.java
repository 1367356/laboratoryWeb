package com.li.service;


import com.li.pojo.User;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User findUserByUserName(String userName);
    public String registerUserAccount(User user);

    int deleteUser(int uid);

    List<User> selectUserList(RowBounds rowBounds);

    int modifyPassword(String username, String newPassword) throws Exception;

    int selectCount();
}
