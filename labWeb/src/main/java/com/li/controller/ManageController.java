package com.li.controller;

import com.li.pojo.*;
import com.li.service.ManageService;
import com.li.service.impl.ForeServiceImpl;
import com.li.service.impl.ManageServiceImpl;
import com.li.service.impl.ResearchTeamServiceImpl;
import com.li.utils.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    ResearchTeamServiceImpl researchTeamService;

    @Value("${ftp.imagesBasePath}")     //基础路径
    private String imagesBasePath;  //图片路径

    /**
     * 向数据库添加一条新闻
     */
//    @PreAuthorize("hasAuthority('SUPER')")  //需要管理员权限，才能执行该路径
    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public String insert(Model model,String pid,String id) {

        model.addAttribute("pid", pid);
        model.addAttribute("id",id);
        return "background/new_edit";
    }

    /**
     * 向数据库添加一条新闻
     * @param parameter 参数
     * @return           响应体
     */

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(Model model,InsertParameter parameter) {

        if (parameter.getTitleImage() != null && parameter.getTitleImage().length()!=0) {// 上传图片
            logger.debug("TitleImage"+parameter.getTitleImage());
            String imagepath = ImageUtil.GenerateImage(parameter.getTitleImage(), imagesBasePath);
            parameter.setTitleImage(imagepath);
        }

        if (parameter.getPid().equals("4")) {
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
                return "message/manage/404";
            }
            News news = foreService.queryNews(htmlid);
            model.addAttribute("response", news);
            return "front/news_content";
        }

        logger.debug(parameter.getAbstractText());
        if (parameter.getPublisher() == null) {
            parameter.setPublisher("admin");
        }

        if (parameter.getDate() == null) {
            Date current_date = new Date();
            //设置日期格式化样式为：yyyy-MM-dd
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //格式化当前日期
            String date = SimpleDateFormat.format(current_date.getTime());
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
            return "message/manage/404";
        }
        News news = foreService.queryNews(htmlid);
        model.addAttribute("response", news);
        return "front/news_content";
    }

    /**
     * 删除一条新闻
     * @return           响应体
     */
    @RequestMapping("/deleteNews")
    public String delete(RedirectAttributes model, String pid, String id, int page, String htmlid) {

        long lhtmlid = Long.parseLong(htmlid);
        int i= 0;
        try {
            i = manageService.delete(lhtmlid);
        } catch (Exception e) {
            model.addAttribute("response","删除失败");
            return "message/manage/404";
        }
        logger.debug("删除" + i);
        model.addAttribute("pid", pid);
        model.addAttribute("id", id);
        model.addAttribute("page", page);
        return "redirect:/background/querybyBackList";  //重定向
    }

    /**
     * 修改一条新闻
     * @return           响应体
     */
    @RequestMapping(value = "/updateNews",method = RequestMethod.GET)
    public String update(Model model, String htmlid,String pid,String id) {

        long lhtmlid = Long.parseLong(htmlid);
        News news = foreService.queryNews(lhtmlid);

        String titleImage=researchTeamService.queryByHtmlid(htmlid);
        logger.debug("titleImage"+titleImage);

        model.addAttribute("titleImage", titleImage);
        model.addAttribute("response",news);
        model.addAttribute("pid", pid);
        model.addAttribute("id", id);
        return "background/new_modify";
    }
    /**
     * 修改一条新闻
     * @return
     */
    @RequestMapping(value = "/updateNews",method = RequestMethod.POST)
    public String update(Model model, InsertParameter parameter) {

        if (parameter.getTitleImage() != null && !parameter.getTitleImage().startsWith("/displayImage")) {// 上传图片
            String imagepath = ImageUtil.GenerateImage(parameter.getTitleImage(), imagesBasePath);
            logger.debug("imagepath"+imagepath);
            parameter.setTitleImage(imagepath);
        }

        if(parameter.getPid().equals("4")){
            int i=researchTeamService.update(parameter);  //更新团队列表
            if (i != 1) {
                model.addAttribute("response","更新团队成员失败");
                return "message/manage/404";
            }
        }else {
            int i=manageService.updateNewsList(parameter);  //更新新闻列表
            if (i != 1) {
                model.addAttribute("response","更新团队成员失败");
                return "message/manage/404";
            }
        }
        int i=manageService.update(parameter);  //更新news
        if (i != 1) {
            return "message/manage/404";
        }
        model.addAttribute("response",parameter);
        model.addAttribute("pid", parameter.getPid());
        model.addAttribute("id", parameter.getId());
        return "front/news_content";
    }


    /**
     * 后台查询列表，就直接返回到列表界面
     * @param model
     * @param pid
     * @param id
     * @param page
     * @return
     */
    @RequestMapping("/querybyBackList")
    public String query(Model model,String pid,String id,String page) {
        List<NewsList> newsLists=null;
        if (pid == null || id == null || page == null) {
            model.addAttribute("response","查询新闻失败");
            return "message/manage/404";
        }
        try {
            int ipage = Integer.parseInt(page);
            newsLists = manageService.queryByCategory(pid, id, ipage);

        } catch (Exception e) {
            model.addAttribute("response","查询新闻失败");
            return "message/manage/403";
        }
            model.addAttribute("response",newsLists);
            int count = manageService.selectCount(pid, id);
            logger.debug("pid=" + pid + "id=" + id + "的总数count:" + count);
            int totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
            model.addAttribute("totalpage", totalpage);
            model.addAttribute("page", page);
            model.addAttribute("pid", pid);
            model.addAttribute("id", id);
            logger.debug("querybyBackList");

            return "background/manage_info";
    }
}
