package com.li.controller;


import com.li.pojo.FtpFile;
import com.li.service.impl.FtpFileServiceImpl;
import com.li.utils.FtpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("ftp")
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

    @Value("${ftpFile.privateFilePath}")     //公有文件位置
    private String privateFilePath;

    /**
     *实验室私有文件上传
     * @param file 文件
     * @throws IOException 遗传
     */
    @RequestMapping("/private/uploadFile")
    public String uploadPrivateFile(@RequestParam("file") MultipartFile file, FtpFile uploadParameter) throws IOException {

//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        uploadParameter.setDate(timestamp);

        if (uploadParameter.getDate() == null) {
            Date current_date = new Date();
            //设置日期格式化样式为：yyyy-MM-dd
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //格式化当前日期
            String date = SimpleDateFormat.format(current_date.getTime());

            //        Timestamp date = new Timestamp(System.currentTimeMillis());
            uploadParameter.setDate(date);
        }
//        String filename=l.toString();
        String filename = file.getOriginalFilename();//文件名，作为主键
//        filename = URLEncoder.encode(filename, "UTF-8");
        uploadParameter.setFilename(filename);  //设置上传文件名
        int i1 = filename.lastIndexOf(".");
        String suffix = filename.substring(i1);
        String id=System.currentTimeMillis()+suffix;
        ftp://192.168.100.91/public/vsftpd.conf
        uploadParameter.setId("ftp://"+host+":"+port+privateFilePath+"/"+id);  //设置上传文件名

        //通过Spring Security 获取登录用户名

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

//        String userName = "spring security";
        uploadParameter.setUploadUser(userName);


//        String filename = request.getParameter("filename");

        InputStream in = file.getInputStream();
        boolean isSucesse= FtpUtil.uploadFile(host,port,username,password,basePath,privateFilePath,id,in);
        if(isSucesse){
            try {
                 int i = ftpFileService.uploadFileParam(uploadParameter);  //org.springframework.dao.DuplicateKeyException  出现异常
                return "home"; //上传成功页面
            } catch (Exception e) {
                return "error/404";
            }
        }
        //将参数添加到数据库
//        System.out.println(isSucesse);

        return "error/404"; //上传成功页面

        //上传成功定位到哪里，将状态数据放回到哪里
    }


    /**
     *文件上传
     * @param file 文件
     * @throws IOException 遗传
     */
    @RequestMapping("/public/uploadFile")
    public String uploadPublicFile(@RequestParam("file") MultipartFile file, FtpFile uploadParameter) throws IOException {

        if (file == null || uploadParameter.getDescription() == null) {
            return "error/404";
        }
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        uploadParameter.setDate(timestamp);

        if (uploadParameter.getDate() == null) {
            Date current_date = new Date();
            //设置日期格式化样式为：yyyy-MM-dd
            SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //格式化当前日期
            String date = SimpleDateFormat.format(current_date.getTime());

            //        Timestamp date = new Timestamp(System.currentTimeMillis());
            uploadParameter.setDate(date);
        }
//        String filename=l.toString();
        String filename = file.getOriginalFilename();//文件名，作为主键
//        filename = URLEncoder.encode(filename, "UTF-8");

        uploadParameter.setFilename(filename);  //设置上传文件名

        int i1 = filename.lastIndexOf(".");
        String suffix = filename.substring(i1);
        String id=System.currentTimeMillis()+suffix;

//        uploadParameter.setId(id);  //设置上传文件名
        uploadParameter.setId("ftp://"+host+":"+port+publicFilePath+"/"+id);  //设置上传文件名

        //通过Spring Security 获取登录用户名
        String userName = "spring security";
        uploadParameter.setUploadUser(userName);


//        String filename = request.getParameter("filename");

        InputStream in = file.getInputStream();
        boolean isSucesse= FtpUtil.uploadFile(host,port,username,password,basePath,publicFilePath,id,in);

        if (isSucesse) {
            try {
                //将参数添加到数据库
                int i = ftpFileService.uploadFileParam(uploadParameter);  //org.springframework.dao.DuplicateKeyException  出现异常
                return "sucess/200"; //上传成功定位到哪里，将状态数据放回到哪里
            }catch (Exception e){
                return "error/404";
            }
        }

        return "error/404"; //上传成功页面


    }


    /**
     * 根据给定的文件名进行下载
     * Description: 从FTP服务器下载文件
     * @param filename 要下载的文件名
     * @param localpath 下载后保存到本地的路径
     * @return
     */
//    @RequestMapping("/downloadFile")
//    @ResponseBody
//    public String downloadFile(String filename,String localpath) throws IOException {
//        if (filename == null || localpath==null) {
//            return "参数名不对";
//        }
//        logger.debug("本地文件路径"+localpath+"文件名"+filename);
//        boolean isSucess = FtpUtil.downloadFile(host, port, username, password, basePath + publicFilePath, filename, localpath);
//        if (isSucess) {
//            return "下载成功";
//        }
//        return "下载失败";
//    }

    /**
     * 根据给定的文件名进行删除
     * Description: 从FTP服务器删除文件
     * @param filename 要删除的文件名
     * @return
     */
    @RequestMapping("/deleteFile")
    @ResponseBody
    public String deleteFile(String filename) throws IOException {
        boolean isSucess = FtpUtil.deleteFile(host, port, username, password, basePath + publicFilePath, filename);
        if (isSucess) {
            //数据库删除
        return "删除成功";

        }
        return "删除失败";
    }

    /**
     * 公有文件查询
     * @param page
     * @return
     */
    @RequestMapping("/public/queryFile")
    public String queryPublicFile(Model model,String page) {
        List<FtpFile> files=null;
        try {
            int ipage = Integer.parseInt(page);
            files=ftpFileService.queryPublicFile(ipage);
        }catch (Exception e){
            return "error/404";
        }
        model.addAttribute("response", files);
        return "/displayFile";
    }

    /**
     * 公有文件查询
     * @param page
     * @return
     */
    @RequestMapping("/private/queryFile")
    public String queryPrivateFile(Model model,String page) {
        List<FtpFile> files=null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            int ipage = Integer.parseInt(page);
            files=ftpFileService.queryPrivateFile(ipage,username);
        }catch (Exception e){
            return "error/404";
        }
        model.addAttribute("response", files);
        return "/displayFile";
    }

    /**
     * 我的文件，用户自己的私有文件
     * @return 展示html
     */
    @RequestMapping("/myFile")
    public String myFile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        //根据用户名获取该用户的文件

        return "home";
    }


}
