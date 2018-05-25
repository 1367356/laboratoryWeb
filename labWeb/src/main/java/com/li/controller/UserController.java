package com.li.controller;

import com.li.pojo.User;
import com.li.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * ftp文件用户控制层：注册，修改
 */
@Controller
@RequestMapping("/ftp")
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String login() {
//        System.out.println("home");
//        return "forum/home";  //主页
//    }

   // 用户添加，删除 。

    @RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public String displayLoginPage(Model model) {
        System.out.println("进入");
        model.addAttribute("title", "用户登陆");
        return "forum/user-login"; //登录界面，验证没通过。
    }

    /**
     * 用户注册,跳转到用户添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        System.out.println("registrationGet");
        model.addAttribute("userDto", new User());
        return "ftp/user-registration";  //注册页面
    }

    @PreAuthorize("hasAuthority('SUPER')")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)  //提交注册
    public String registerNewUser(@ModelAttribute("userDto") User user, Model model) {
        System.out.println("registrationPost");
        String result = this.userService.registerUserAccount(user);
//        model.addAllAttributes("response",result);
        model.addAttribute("response", result);
        return "forum/user-registration-result";
    }


    @PreAuthorize("hasAuthority('SUPER')")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)  //提交注册
    public String deleteUser(Model model,int uid) {

        int i=userService.deleteUser(uid);
//        System.out.println("registrationPost");
//        String result = this.userService.registerUserAccount(user);
//        model.addAllAttributes("response",result);
//        model.addAttribute("response", result);
        return "forum/user-registration-result";
    }

//    @PreAuthorize("hasAuthority('SUPER')")
    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)  //提交注册
    public String selectUserList(Model model,int page,int pageSize) {

        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<User> users = userService.selectUserList(rowBounds);
//        int i=userService.deleteUser(uid);
//        System.out.println("registrationPost");
//        String result = this.userService.registerUserAccount(user);
//        model.addAllAttributes("response",result);
//        model.addAttribute("response", result);
        return "forum/user-registration-result";
    }


//    @PreAuthorize("hasAuthority('SUPER')")  //需要SUPER用户才能通过该路径，第一步通过配置验证，没有用户登录，将会拦截，让用户登录，登录成功，访问该路径时进行角色验证。
//    @RequestMapping(value = "/admin/admin", method = RequestMethod.GET)
//    public String deletePost() {
//        return "admin/admin";
//    }



//    //获取登录用户名
//    public void getUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//    }

}
