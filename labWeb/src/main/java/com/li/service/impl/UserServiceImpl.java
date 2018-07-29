package com.li.service.impl;

import com.li.dao.UserMapper;
import com.li.pojo.User;
import com.li.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LogManager.getLogger(UserServiceImpl.class);

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
//        logger.debug("getPassword"+user.getPassword());
        if(userDto.getDescription()!=null){
            logger.debug("添加用户描述");
            user.setDescription(userDto.getDescription());
        }
        if (userDto.getRoles() == null) {
            user.setRoles("USER");
        }else {

            user.setRoles(userDto.getRoles());  //设置用户权限
            logger.debug(user.getRoles());
        }
        int affectedRow = userMapper.save(user);
        String registrationResult = affectedRow == 1 ? "success" : "failure";
//        attributes.put("userRegistrationResult", registrationResult);
        return registrationResult;
    }

//    @CacheEvict(cacheNames = {"selectUserList"},allEntries=true)
    public int deleteUser(int uid) {
        return userMapper.deleteUser(uid);
    }

//    @Cacheable(cacheNames = {"selectUserList"})
    public List<User> selectUserList(RowBounds rowBounds) {
        return userMapper.selectUserList(rowBounds);
    }

    @Override
    public int modifyPassword(String username, String newPassword) throws Exception{
        newPassword = bCryptPasswordEncoder.encode(newPassword);   //对密码进行编码
        return userMapper.modifyPassword(username,newPassword);

    }

    @Override
    public int selectCount() {
        return userMapper.selectCount();
    }
}
