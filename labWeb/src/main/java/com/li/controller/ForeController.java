package com.li.controller;

import com.li.pojo.News;
import com.li.pojo.NewsList;
import com.li.pojo.ResearchTeam;
import com.li.service.ForeService;
import com.li.service.ManageService;
import com.li.service.ResearchTeamService;
import com.li.service.impl.ForeServiceImpl;
import com.li.service.impl.ManageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 页面展示控制器
 */
//@RequestMapping("main")
@Controller
public class ForeController {

    Logger logger = LogManager.getLogger(ForeController.class);
    @Autowired
    ManageServiceImpl manageService;
    @Autowired
    ForeServiceImpl foreService;

    @Autowired
    ResearchTeamService researchTeamService;

    @RequestMapping("/querybycategory")
    public String query(Model model, String pid, String id, String page) {
        logger.debug(page);
        List<NewsList> newsLists = null;
        int ipage = Integer.parseInt(page);
        if (pid == null || id == null || page == null) {
            return "message/404";
        }

        try {
            newsLists = manageService.queryByCategory(pid, id, ipage);
            if (pid.equals("1") && id.equals("1")) {
                long htmlid = newsLists.get(0).getHtmlid();
                News news=foreService.queryNews(htmlid);  //查询新闻
                model.addAttribute("response", news.getContent());
                logger.debug("实验室简介");
                return "front/intro";
            }
        } catch (Exception e) {
            model.addAttribute("response", "实验室简介为空");
            return "message/403";
        }
        int count = manageService.selectCount(pid, id);
        logger.debug("pid=" + pid + "id=" + id + "的总数count:" + count);
        int totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        model.addAttribute("response", newsLists);
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("page", page);
        model.addAttribute("pid", pid);
        model.addAttribute("id", id);
//        if(pid.equals("4")){
//            logger.debug("返回到科研团队");
//            return "front/team_info";
//        }
        logger.debug("返回到news_info");
        return "front/news_info";
//        }
    }

    @RequestMapping("/queryNews")
    public String queryNews(Model model,long htmlid){

        News news=foreService.queryNews(htmlid);  //查询新闻
        int count = news.getCount();
        count=count+1;
        foreService.updateCount(htmlid, count);
        model.addAttribute("response", news);

        return "front/news_content";
    }


    @RequestMapping("/index")
    public String index(Model model) {


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

            if (newsLists1.size() > 8) {
                newsLists1 = newsLists1.subList(1, 8);
            }
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
            model.addAttribute("response", "首页加载失败");
            return "message/404";
        }
        return "front/index";
    }
}
