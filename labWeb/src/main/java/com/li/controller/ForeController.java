package com.li.controller;

import com.li.pojo.News;
import com.li.pojo.NewsList;
import com.li.service.ForeService;
import com.li.service.ManageService;
import com.li.service.impl.ForeServiceImpl;
import com.li.service.impl.ManageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 测试各个html文件用。
     * @param model
     * @return
     */
    @RequestMapping("home")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/querybycategory")
    public String query(Model model, String pid, String id, String page) {
        logger.debug(page);
        List<NewsList> newsLists = null;
        if (pid == null || id == null || page == null) {
            return "message/404";
        }
        try {
            int ipage = Integer.parseInt(page);
            newsLists = manageService.queryByCategory(pid, id, ipage);
            if (pid.equals("1") && id.equals("1")) {
                long htmlid = newsLists.get(0).getHtmlid();
                News news=foreService.queryNews(htmlid);  //查询新闻
                model.addAttribute("response", news.getContent());
                logger.debug("实验室简介");
                return "front/intro";
            }
        } catch (Exception e) {
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
//    @ResponseBody
    public String index(Model model) {
        List<NewsList> newsLists1 = manageService.queryByCategory("2", "3", 1);
        List<NewsList> newsLists2 = manageService.queryByCategory("1", "2", 1);
        List<NewsList> newsLists3 = manageService.queryByCategory("2", "2", 1);
        List<NewsList> newsLists4 = manageService.queryByCategory("6", "1", 1);
        model.addAttribute("response1", newsLists1);
        model.addAttribute("response2", newsLists2);
        model.addAttribute("response3", newsLists3);
        model.addAttribute("response4", newsLists4);

        return "front/index";
    }
}
