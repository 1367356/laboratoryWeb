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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)  //提交注册
    public String registerNewUser(User user, RedirectAttributes model) {
//        @ModelAttribute("userDto")
        logger.debug(user.getRoles());
        logger.debug(user.getDescription());
        logger.debug(user.getPassword());
        logger.debug(user.getUserName());

        String result = this.userService.registerUserAccount(user);
        if (result.equals("success")) {
            model.addAttribute("page", 1);
            return "redirect:/user/selectUser";
        }
        model.addAttribute("response","添加用户失败");
        return "message/manage/404";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)  //提交注册
    public String deleteUser(Model model,int uid) {
        int i=userService.deleteUser(uid);
        if (i == 1) {
            model.addAttribute("response","删除用户成功");
            return "message/manage/200";
        }
        model.addAttribute("response", "删除用户失败");
        return "message/manage/404";
    }

    /**
     * 用户列表
     * @param model
     * @param page
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)  //提交注册
    public String selectUserList(Model model,int page) {
        int pageSize=10;
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<User> users = userService.selectUserList(rowBounds);
        int count=userService.selectCount();
        int totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        model.addAttribute("response", users);
        model.addAttribute("page", page);
        model.addAttribute("totalpage", totalpage);
        return "background/userManage";
    }

    /**
     * 修改密码
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
    public String modifyPassword(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        model.addAttribute("response", userName);

        return "front/changePwd";
    }

    /**
     * 修改密码
     * @param model
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public String modifyPassword(Model model,String newPassword) {
        logger.debug(newPassword);
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

}
