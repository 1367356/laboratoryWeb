package com.li.controller;

import com.li.pojo.*;
import com.li.service.ManageService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//当返回前台传过来的参数时，直接在model中添加该参数即可。
@Controller
@RequestMapping("/background")
@PreAuthorize("hasAuthority('ADMIN')")
public class ManageController {

    Logger logger = LogManager.getLogger(ManageController.class);

    @Autowired
    ManageServiceImpl manageService;
    @Autowired
    ForeServiceImpl foreService;

    /**
     * 向数据库添加一条新闻
     */
//    @PreAuthorize("hasAuthority('SUPER')")  //需要管理员权限，才能执行该路径
    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public String insert(Model model) {
        return "background/addNews";
    }

    /**
     * 向数据库添加一条新闻
     * @param parameter 参数
     * @return           响应体
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(Model model,InsertParameter parameter) {

        logger.debug(parameter.getAbstractText());

        if (parameter.getDate() == null) {
            Date current_date = new Date();
            //设置日期格式化样式为：yyyy-MM-dd
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //格式化当前日期
            String date = SimpleDateFormat.format(current_date.getTime());

    //        Timestamp date = new Timestamp(System.currentTimeMillis());
            parameter.setDate(date);
        }
        long htmlid = System.currentTimeMillis();
        parameter.setHtmlid(htmlid);

        boolean insert = false;
        try {
            insert = manageService.insert(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!insert) {
            return "message/404";
        }
        News news = foreService.queryNews(htmlid);
        model.addAttribute("response", news);
        return "front/news_content";
    }

    /**
     * 删除一条新闻
     * @return           响应体
     */
    @RequestMapping("/delete")
    public String delete(Model model, String pid,String id,int page,String htmlid) {

        long lhtmlid = Long.parseLong(htmlid);
        int i=manageService.delete(lhtmlid);
        if (i != 1) {
            return "message/404";
        }
        List<NewsList> list=manageService.queryByCategory(pid,id,page);
        model.addAttribute("response",list);
        return "background/backdisplay";
    }

    /**
     * 修改一条新闻
     * @return           响应体
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(Model model, String htmlid) {

        long lhtmlid = Long.parseLong(htmlid);
        News news = manageService.backQuery(lhtmlid);
        model.addAttribute("response",news);
        return "background/updateNews";
    }
    /**
     * 修改一条新闻
     * @return           响应体
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(Model model, InsertParameter parameter) {

        int i=manageService.update(parameter);
        if (i != 1) {
            return "message/404";
        }
        model.addAttribute("response",parameter);
        return "background/backUpdate";
    }


    /**
     * 后台查询列表，就直接返回到列表界面
     * @param model
     * @param pid
     * @param id
     * @param page
     * @return
     */
    @RequestMapping("/queryBackList")
    public String query(Model model,String pid,String id,String page) {
        List<NewsList> newsLists=null;
        if (pid == null || id == null || page == null) {
            return "message/404";
        }
        try {
            int ipage = Integer.parseInt(page);
            newsLists = manageService.queryByCategory(pid, id, ipage);

        } catch (Exception e) {

            return "message/403";
        }
            model.addAttribute("response",newsLists);
            return "background/back_new_list";

//        NewsList newsList = newsLists.get(0);
//        long htmlid = newsList.getHtmlid();
//        News news = foreService.queryNews(htmlid);
//        model.addAttribute("response", news);
//        return "front/displayNews";
//        return newsLists.toString();
    }

    /**
     * 后台查询一条新闻
     * @return           响应体
     */
    @RequestMapping(value = "/backQuery",method = RequestMethod.GET)
    public String query(Model model,String htmlid) {

        long lhtmlid = Long.parseLong(htmlid);
        logger.debug(lhtmlid);
        News news = manageService.backQuery(lhtmlid);


//        if (i != 1) {
//            return "message/404";
//        }

        model.addAttribute("response",news);
        return "front/displayNews";
//        logger.debug(news.getContent());
//        return news.toString();
    }
}
