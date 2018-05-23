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

import java.util.List;

/**
 * 页面展示控制器
 */
@Controller
@RequestMapping("main/fore")
public class ForeController {

    Logger logger = LogManager.getLogger(ForeController.class);
    @Autowired
    ManageServiceImpl manageService;
    @Autowired
    ForeServiceImpl foreService;

    @RequestMapping("/querybycategory")
    public String query(Model model,String pid,String id,String page) {
        List<NewsList> newsLists=null;
        if (pid == null || id == null || page == null) {
            return "error/404";
        }
        try {
            int ipage = Integer.parseInt(page);
            newsLists = manageService.queryByCategory(pid, id, ipage);

        } catch (Exception e) {
            return "error/403";
        }

        if (newsLists.size() != 1) {
            model.addAttribute("response",newsLists);
            return "fore/new_list";
//            return newsLists.toString();
        }

        NewsList newsList = newsLists.get(0);
        long htmlid = newsList.getHtmlid();
        News news = foreService.queryNews(htmlid);
        model.addAttribute("response", news);
        return "fore/displayNews";
//        return newsLists.toString();
    }

    @RequestMapping("/queryNews")
    public String queryNews(Model model,long htmlid){

        News news=foreService.queryNews(htmlid);  //查询新闻
        int count = news.getCount();
        count=count+1;
        foreService.updateCount(htmlid, count);
        model.addAttribute("response", news);
        return "fore/displayNews";
    }


    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
