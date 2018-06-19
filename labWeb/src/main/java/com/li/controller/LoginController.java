package com.li.controller;

import com.li.pojo.NewsList;
import com.li.service.impl.ForeServiceImpl;
import com.li.service.impl.ManageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LoginController {

    Logger logger = LogManager.getLogger(ManageController.class);

    @Autowired
    ManageServiceImpl manageService;
    @Autowired
    ForeServiceImpl foreService;


    /**
     * 管理员登录
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)  //登录成功，跳转到主页面
    @PreAuthorize("hasAuthority('ADMIN')")  //后台页面
    public String login(Model model) {
        List<NewsList> newsLists=null;
        try {
            newsLists = manageService.queryByCategory("2", "1", 1);
        } catch (Exception e) {
            return "message/403";
        }
//        logger.debug(newsLists.get(0).getHtmlid());
        model.addAttribute("response",newsLists);
        int count = manageService.selectCount("1", "1");
        int totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("page", 1);
        model.addAttribute("pid", 2);
        model.addAttribute("id", 1);

        return "background/manage_info";
    }

    // 用户登录 。

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String displayLoginPage(Model model) {
        System.out.println("进入");
        model.addAttribute("title", "用户登陆");
        return "front/login"; //登录界面，验证没通过。
    }

    /**
     * 登录成功，跳转的页面
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAdminPage(Model model) {
        try {
            List<NewsList> newsLists1 = manageService.queryByCategory("2", "3", 1);
            List<NewsList> newsLists2 = manageService.queryByCategory("2", "1", 1);
            List<NewsList> newsLists3 = manageService.queryByCategory("2", "2", 1);
            List<NewsList> newsLists4 = manageService.queryByCategory("6", "1", 1);
            model.addAttribute("response1", newsLists1);
            model.addAttribute("response2", newsLists2);
            model.addAttribute("response3", newsLists3);
            model.addAttribute("response4", newsLists4);
        } catch (Exception e) {
            model.addAttribute("response", "首页加载失败");
            return "message/404";
        }
        return "front/index";
    }

}
