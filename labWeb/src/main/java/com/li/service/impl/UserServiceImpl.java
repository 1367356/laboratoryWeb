package com.li.service.impl;

import com.li.dao.UserMapper;
import com.li.pojo.User;
import com.li.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public String registerUserAccount(User userDto) {
        Map<String, Object> attributes = new HashMap<>();
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));  //保存时应该将密码编码

        user.setUserName(userDto.getUserName());
        user.setRoles("USER");

        int affectedRow = userMapper.save(user);
        String registrationResult = affectedRow == 1 ? "success" : "failure";
//        attributes.put("userRegistrationResult", registrationResult);
        return registrationResult;
    }

    @Override
    public int deleteUser(int uid) {
        return userMapper.deleteUser(uid);
    }

    @Override
    public List<User> selectUserList(RowBounds rowBounds) {
        return userMapper.selectUserList(rowBounds);
    }
}
