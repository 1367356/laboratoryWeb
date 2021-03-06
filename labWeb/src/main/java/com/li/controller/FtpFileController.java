package com.li.controller;


import com.li.pojo.FtpFile;
import com.li.service.impl.FtpFileServiceImpl;
import com.li.utils.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RequestMapping("FtpFileController")
public class FtpFileController {

    Logger logger = LogManager.getLogger(FtpFileController.class);
    @Autowired
    FtpFileServiceImpl ftpFileService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FtpFileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;
    @Value("${ftp.basePath}")     //基础路径
    private String basePath;

    @Value("${ftpFile.publicFilePath}")     //公有文件位置
    private String publicFilePath;

    @Value("${ftpFile.privateFilePath}")     //私有文件位置
    private String privateFilePath;

    /**
     * 实验室私有文件上传
     *
     * @param file 文件
     * @throws IOException 遗传
     */
    @RequestMapping("/private/uploadFile")
    public String uploadPrivateFile(RedirectAttributes model, @RequestParam("file") MultipartFile file, FtpFile uploadParameter) throws IOException {

        if (uploadParameter.getDate() == null) {
            Date current_date = new Date();
            //设置日期格式化样式为：yyyy-MM-dd
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //格式化当前日期
            String date = SimpleDateFormat.format(current_date.getTime());
            uploadParameter.setDate(date);
        }
//        String filename=l.toString();
        String filename = file.getOriginalFilename();//文件名，作为主键
//        filename = URLEncoder.encode(filename, "UTF-8");
        uploadParameter.setFilename(filename);  //设置上传文件名
        int i1 = filename.lastIndexOf(".");
        String suffix = filename.substring(i1);
        String id = System.currentTimeMillis() + suffix;
//        ftp://192.168.100.91/public/vsftpd.conf
        uploadParameter.setId(id);  //设置上传文件名

        uploadParameter.setDownloadLink("ftp://" + username + ":" + password + "@" + host + ":" + port + privateFilePath + "/" + id);
//        uploadParameter.setId("ftp://"+host+":"+port+privateFilePath+"/"+id);  //设置上传文件名?
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        uploadParameter.setUploadUser(userName);
        InputStream in = file.getInputStream();
        boolean isSucesse = FtpUtil.uploadFile(host, port, username, password, basePath, privateFilePath, id, in);
        if (isSucesse) {
            try {
                int i = ftpFileService.uploadPrivateFileParam(uploadParameter);  //org.springframework.dao.DuplicateKeyException  出现异常
                model.addAttribute("page", "1");
//                return "message/200"; //上传成功页面
                return "redirect:/FtpFileController/private/queryFile";
            } catch (Exception e) {
                model.addAttribute("response", "出现异常" + e.toString());
                return "redirect:/front/error404"; //上传失败页面
//                return "出现异常";
            }
        }
        model.addAttribute("response", "私有文件上传失败");
        return "redirect:/front/error404"; //上传失败页面
    }


    /**
     * 文件上传
     *
     * @param file 文件
     * @throws IOException 遗传
     */
    @RequestMapping("/public/uploadFile")
    public String uploadPublicFile(RedirectAttributes model, @RequestParam("file") MultipartFile file, FtpFile uploadParameter) {
        if (uploadParameter.getDescription() == null) {
            uploadParameter.setDescription(file.getOriginalFilename());
        }
        if (file == null) {
            logger.debug("file==null");
            model.addAttribute("response", "文件不能为空");
            return "redirect:/front/error404"; //上传失败页面
//            return "文件不能为空";
        }
        if (uploadParameter.getDate() == null) {
            Date current_date = new Date();
            //设置日期格式化样式为：yyyy-MM-dd
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //格式化当前日期
            String date = SimpleDateFormat.format(current_date.getTime());
            uploadParameter.setDate(date);
        }
//        String filename=l.toString();
        String filename = file.getOriginalFilename();//文件名，作为主键
//        filename = URLEncoder.encode(filename, "UTF-8");

        uploadParameter.setFilename(filename);  //设置上传文件名

        int i1 = filename.lastIndexOf(".");
        if (i1 < 0) {
            model.addAttribute("response", "文件名命名不正确");
            return "redirect:/front/error404"; //上传失败页面
        }
        String suffix = filename.substring(i1);
        String id = System.currentTimeMillis() + suffix;
        uploadParameter.setId(id);  //设置上传id

        uploadParameter.setDownloadLink("ftp://" + username + ":" + password + "@" + host + ":" + port + publicFilePath + "/" + id);

        //通过Spring Security 获取登录用户名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        uploadParameter.setUploadUser(userName);  //上传人

        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isSucesse = FtpUtil.uploadFile(host, port, username, password, basePath, publicFilePath, id, in);
        logger.debug("上传成功");
        if (isSucesse) {
            try {
                //将参数添加到数据库
                int i = ftpFileService.uploadPublicFileParam(uploadParameter);  //org.springframework.dao.DuplicateKeyException  出现异常
//                model.addAttribute("response", "文件上传成功");
                model.addAttribute("page", "1");
                logger.debug("重定向");
                return "redirect:/ftp/public/queryFile"; //上传成功定位到哪里，将状态数据放回到哪里
//                return "文件上传成功";
            } catch (Exception e) {
                model.addAttribute("response", "出现异常" + e.toString());
                return "redirect:/front/error404"; //上传失败页面
//                return "出现异常";
            }
        }
        model.addAttribute("response", "文件上传失败");
        return "redirect:/front/error404"; //上传失败页面
//        return "文件上传失败";
    }

    /**
     * 根据给定的文件名进行删除
     * Description: 从FTP服务器删除公有文件
     *
     * @param id 要删除的文件名,11111.avi
     * @return
     */
    @RequestMapping("/deletePublicFile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePublicFile(RedirectAttributes model, String id,String page) throws IOException {

        boolean isSucess = false;
        try {
            isSucess = FtpUtil.deleteFile(host, port, username, password, publicFilePath, id);
        } catch (Exception e) {
            model.addAttribute("response", "删除公有文件失败");
//            return "message/manage/404";
            return "redirect:/backgrouond/error404";
        }
        if (isSucess) {
            //数据库删除
            int i = ftpFileService.deletePublicFile(id);
            model.addAttribute("page", page);
            return "redirect:/ftp/public/queryBackgroundPublicFile"; //上传成功定位到哪里，将状态数据放回到哪里
        }
        model.addAttribute("response", "删除公有文件失败");
//        return "message/manage/404";
//        return "删除文件失败";
        return "redirect:/backgrouond/error404";
    }

    @RequestMapping("/deletePrivateFile")
    public String deletePrivateFile(RedirectAttributes model, String id,String page) throws IOException {
        logger.debug("page"+page);
        boolean isSucess = false;
        try {
            isSucess = FtpUtil.deleteFile(host, port, username, password, privateFilePath, id);
        } catch (Exception e) {
            model.addAttribute("response", "删除文件失败");
//            return "message/404";
            return "redirect:/front/error404";
        }
        if (isSucess) {
            logger.debug("删除私有文件");
            //数据库删除
            int i = ftpFileService.deletePrivateFile(id);
            model.addAttribute("page", page);
            return "redirect:/FtpFileController/private/queryFile";   //            return "删除文件成功";

        }
        model.addAttribute("response", "删除文件失败");
//        return "message/404";
        return "redirect:/front/error404";
    }

    /**
     * 私有文件(我的文件)查询
     *
     * @param page
     * @return
     */
    @RequestMapping("/private/queryFile")
    public String queryPrivateFile(Model model, String page) {
        List<FtpFile> files = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        int count = 0;
        int totalpage = 0;
        try {
            int ipage = Integer.parseInt(page);
            files = ftpFileService.queryPrivateFile(ipage, username);
            count = ftpFileService.selectPrivateCount(username);
            totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("response", "查询私有文件失败");
            return "message/404";
        }
        model.addAttribute("response", files);
        model.addAttribute("totalpage", totalpage);
        model.addAttribute("page", page);
        return "front/privateFile";
    }
}