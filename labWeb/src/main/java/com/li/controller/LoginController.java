package com.li.controller;

import com.li.pojo.News;
import com.li.pojo.NewsList;
import com.li.pojo.ResearchTeam;
import com.li.service.ResearchTeamService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    Logger logger = LogManager.getLogger(ManageController.class);

    @Autowired
    ManageServiceImpl manageService;
    @Autowired
    ForeServiceImpl foreService;
    @Autowired
    ResearchTeamService researchTeamService;


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
        int count = manageService.selectCount("2", "1");
        int totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        logger.debug("totalpage"+totalpage);
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

        List<NewsList> newsLists = null;
        try {
            newsLists=manageService.queryByCategory("1", "1", 1);
            if (newsLists.size()==0) {
                News news=new News();
                model.addAttribute("introduction", news);
            }else {
                long htmlid = newsLists.get(0).getHtmlid();
                News news=foreService.queryNews(htmlid);  //查询简介
                model.addAttribute("introduction", news);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("response", "首页加载失败");
            return "message/404";
        }

        List<ResearchTeam> listGroup = researchTeamService.queryGroup("团队负责人","1");
        try {
            List<ResearchTeam> listGroup2 = researchTeamService.queryGroup("教授","2");
            List<ResearchTeam> listGroup3 = researchTeamService.queryGroup("副教授","2");
            List<ResearchTeam> listGroup4 = researchTeamService.queryGroup("讲师","2");
            List<ResearchTeam> listGroup5 = researchTeamService.queryGroup("教授","3");
            List<ResearchTeam> listGroup6 = researchTeamService.queryGroup("副教授","3");
            List<ResearchTeam> listGroup7 = researchTeamService.queryGroup("讲师","3");

            List<ResearchTeam> listAll = new ArrayList<>();
            listAll.addAll(listGroup);
            listAll.addAll(listGroup2);
            listAll.addAll(listGroup3);
            listAll.addAll(listGroup4);

            listAll.addAll(listGroup5);
            listAll.addAll(listGroup6);
            listAll.addAll(listGroup7);
            model.addAttribute("response", listAll);
        }catch (Exception e){
            model.addAttribute("response", "首页加载失败");
            return "message/404";
        }


//        List<ResearchTeam> listGroup = researchTeamService.queryGroup("团队负责人","1");
        if (listGroup.size()!=0) {
            ResearchTeam researchTeam = listGroup.get(0);
            long htmlid = researchTeam.getHtmlid();
            model.addAttribute("htmlid", htmlid);
        }

        try {
            List<NewsList> newsLists1 = manageService.queryByCategory("3", "2", 1);
            List<NewsList> newsLists2 = manageService.queryByCategory("2", "1", 1);
            List<NewsList> newsLists3 = manageService.queryByCategory("2", "2", 1);
            List<NewsList> newsLists4 = manageService.queryByCategory("6", "1", 1);

//            if (newsLists1.size() > 8) {
//                newsLists1 = newsLists1.subList(1, 8);
//            }
            if (newsLists2.size() > 8) {
                newsLists2 = newsLists2.subList(0, 8);
            }
            if (newsLists3.size() > 8) {
                newsLists3 = newsLists3.subList(0, 8);
            }
            if (newsLists4.size() > 8) {
                newsLists4 = newsLists4.subList(0, 8);
            }
            model.addAttribute("response1", newsLists1);
            model.addAttribute("response2", newsLists2);
            model.addAttribute("response3", newsLists3);
            model.addAttribute("response4", newsLists4);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("response", "首页加载失败");
            return "message/404";
        }
        return "front/index";
    }

}
