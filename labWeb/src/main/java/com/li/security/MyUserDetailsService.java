package com.li.security;

import com.li.pojo.User;
import com.li.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService{

	Logger logger = LogManager.getLogger(MyUserDetailsService.class);
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userService.findUserByUserName(username);
		logger.debug(user.getUserName()+"密码"+user.getPassword());
		if(null == user) {
			throw new UsernameNotFoundException("Can't find user by username: " + username);
		}

		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		// grant roles to user
		for (String role : user.getRolesSet()) {
			logger.debug(role);
			grantedAuthorities.add(new SimpleGrantedAuthority(role));  //认证
		}
//		user.setGrantedAuthorities(authorities); //用于登录时 @AuthenticationPrincipal 标签取值
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);  //角色认证
	}
}
