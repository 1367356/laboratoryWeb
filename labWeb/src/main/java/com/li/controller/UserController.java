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
 * ftp文件用户控制层：注册，修改,修改密码。
 */
@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * 用户注册,跳转到用户添加页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        System.out.println("registrationGet");
        model.addAttribute("userDto", new User());
        return "admin/user-registration";  //注册页面
    }

//    @PreAuthorize("hasAuthority('SUPER')")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)  //提交注册
    public String registerNewUser(@ModelAttribute("userDto")User user, Model model) {

        logger.debug("username"+user.getUserName());
        logger.debug("password:"+user.getPassword());

        System.out.println("registrationPost");
        String result = this.userService.registerUserAccount(user);
//        model.addAllAttributes("response",result);
        model.addAttribute("response", result);
        return "admin/user-registration-result";
    }


//    @PreAuthorize("hasAuthority('SUPER')")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)  //提交注册
    public String deleteUser(Model model,int uid) {

        int i=userService.deleteUser(uid);
//        System.out.println("registrationPost");
//        String result = this.userService.registerUserAccount(user);
//        model.addAllAttributes("response",result);
//        model.addAttribute("response", result);
        return "admin/user-registration-result";
    }

    /**
     * 用户列表
     * @param model
     * @param page
     * @param pageSize
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)  //提交注册
    public String selectUserList(Model model,int page,int pageSize) {
        pageSize=10;
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<User> users = userService.selectUserList(rowBounds);
        return "admin/user-registration-result";
    }

    /**
     * 修改密码
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
    public String modifyPassword(Model model) {
        return "front/modifyPassword";
    }

    /**
     * 修改密码
     * @param model
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public String modifyPassword(Model model,String newPassword) {
        //数据库修改密码
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            int i=userService.modifyPassword(username,newPassword);
        } catch (Exception e) {
            model.addAttribute("response","修改密码失败");
            return "message/404";
        }
        model.addAttribute("response","修改密码成功");
        return "message/200";
    }


    /**
     * 修改密码
     */


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
