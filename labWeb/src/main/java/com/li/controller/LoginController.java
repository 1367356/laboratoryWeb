package com.li.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    /**
     * 管理员登录
     */
    Logger logger = LogManager.getLogger(UserController.class);
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)  //登录成功，跳转到主页面
    @PreAuthorize("hasAuthority('ADMIN')")  //后台页面
    public String login() {
        System.out.println("home");
        return "background/background";  //后台主页
//        return "home";  //主页
    }

    // 用户登录 。

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String displayLoginPage(Model model) {
        System.out.println("进入");
        model.addAttribute("title", "用户登陆");
        return "user-login"; //登录界面，验证没通过。
    }

    /**
     * 登录成功，跳转的页面
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAdminPage() {
//        return "admin/user-login";
		return "front/index";
    }
}
