package com.li.controller;

import com.li.pojo.FtpFile;
import com.li.service.impl.FtpFileServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ftp")
public class FtpController {

    Logger logger = LogManager.getLogger(FtpFileController.class);
    @Autowired
    FtpFileServiceImpl ftpFileService;

    @RequestMapping("/upload")
    public String upload(Model model) {
        return "background/upload";
    }

    /**
     * 公有文件查询
     * @param page
     * @return
     */
    @RequestMapping("/public/queryFile")
    public String queryPublicFile(Model model,String page) {
        List<FtpFile> files=null;

        int count=0;
        int totalpage=0;

        try {
            int ipage = Integer.parseInt(page);
            logger.debug("ipage"+ipage);
            files=ftpFileService.queryPublicFile(ipage);
            count = ftpFileService.selectPublicCount();
            totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("response","查询公共文件出现异常");
            return "message/404";
        }
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("page", page);
        model.addAttribute("response", files);
        return "front/publicFile";  //展示公有文件的页面
    }
    /**
     * 后台系统公有文件查询
     * @param page
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/public/queryBackgroundPublicFile")
    public String queryBackgroundPublicFile(Model model,String page) {
        List<FtpFile> files=null;

        int count=0;
        int totalpage=0;

        try {
            int ipage = Integer.parseInt(page);
            logger.debug("ipage"+ipage);
            files=ftpFileService.queryPublicFile(ipage);
            count = ftpFileService.selectPublicCount();
            totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("response","查询公共文件出现异常");
            return "message/404";
        }
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("page", page);
        model.addAttribute("response", files);
        return "background/publicFile";  //展示公有文件的页面
    }
}
