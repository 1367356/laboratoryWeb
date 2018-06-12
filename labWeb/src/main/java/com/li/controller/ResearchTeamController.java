package com.li.controller;

import com.li.pojo.InsertParameter;
import com.li.pojo.News;
import com.li.pojo.ResearchTeam;
import com.li.service.ResearchTeamService;
import com.li.service.impl.ForeServiceImpl;
import com.li.service.impl.ManageServiceImpl;
import com.li.service.impl.ResearchTeamServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.invoke.MethodType;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 科研团队controller
 */
@Controller
@RequestMapping("researchTeam")
public class ResearchTeamController {
    Logger logger = LogManager.getLogger(ManageController.class);
    @Autowired
    ForeServiceImpl foreService;
    @Autowired
    ResearchTeamServiceImpl researchTeamService;

    @Autowired
    ManageServiceImpl manageService;

    /**
     * 添加科研团队成员
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/insert")
    public String insert(Model model,InsertParameter parameter) {

        if (parameter.getDate() == null) {
            Date current_date = new Date();
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = SimpleDateFormat.format(current_date.getTime());
            parameter.setDate(date);
        }
        long htmlid = System.currentTimeMillis();
        parameter.setHtmlid(htmlid);

        boolean insert = false;
        try {
            insert = researchTeamService.insert(parameter);
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/delete")
    public String delete(RedirectAttributes model, String pid, String id, String htmlid) {

        long lhtmlid = Long.parseLong(htmlid);
        int i= 0;
        try {
            i = researchTeamService.delete(lhtmlid);
        } catch (Exception e) {
            model.addAttribute("response","删除失败");
            return "message/404";
        }
        logger.debug("删除" + i);
        model.addAttribute("pid", pid);
        model.addAttribute("id", id);
        return "redirect:/researchTeam/query";  //重定向
    }
    @RequestMapping(value = "/update",method= RequestMethod.GET)
    public String update(Model model,String htmlid,String pid,String id) {
        long lhtmlid = Long.parseLong(htmlid);
        News news = manageService.backQuery(lhtmlid);
        model.addAttribute("response",news);
        model.addAttribute("pid", pid);
        model.addAttribute("id", id);
        return "background/updateNews";
    }


    /**
     * 修改一条新闻
     * @return           响应体
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(Model model, InsertParameter parameter) {
        int i=researchTeamService.update(parameter);
        if (i != 1) {
            return "message/404";
        }
        model.addAttribute("response",parameter);
        return "background/backUpdate";
    }


    @RequestMapping("/query")
    public String query(Model model, String pid,String id) {

        // select distinct title from news;  ,选择某个字段共有几类
        List<String> typeList = new ArrayList<>();
        typeList=researchTeamService.query(id);

        Iterator<String> iterator = typeList.iterator();

        Map<String,List> map = new HashMap<>();

        while (iterator.hasNext()) {
            String next=iterator.next();
            List<ResearchTeam> listGroup = researchTeamService.queryGroup(next,id);
            map.put(next, listGroup);
        }
        logger.debug(map);
        model.addAttribute("response", map);
        return "map";
    }
}
