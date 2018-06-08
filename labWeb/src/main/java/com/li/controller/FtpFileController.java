package com.li.controller;


import com.li.pojo.FtpFile;
import com.li.service.impl.FtpFileServiceImpl;
import com.li.utils.FtpUtil;
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
     *实验室私有文件上传
     * @param file 文件
     * @throws IOException 遗传
     */
    @RequestMapping("/private/uploadFile")
    public String uploadPrivateFile(Model model,@RequestParam("file") MultipartFile file, FtpFile uploadParameter) throws IOException {

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
        String id=System.currentTimeMillis()+suffix;
//        ftp://192.168.100.91/public/vsftpd.conf
        uploadParameter.setId(id);  //设置上传文件名

        uploadParameter.setDownloadLink("ftp://uftp:1367356@"+host+":"+port+privateFilePath+"/"+id);
//        uploadParameter.setId("ftp://"+host+":"+port+privateFilePath+"/"+id);  //设置上传文件名?
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        uploadParameter.setUploadUser(userName);
        InputStream in = file.getInputStream();
        boolean isSucesse= FtpUtil.uploadFile(host,port,username,password,basePath,privateFilePath,id,in);
        if(isSucesse){
            try {
                 int i = ftpFileService.uploadPrivateFileParam(uploadParameter);  //org.springframework.dao.DuplicateKeyException  出现异常
                model.addAttribute("response", "文件上传成功");
                return "message/200"; //上传成功页面
            } catch (Exception e) {
                model.addAttribute("response", "出现异常"+e.toString());
                return "message/404";
            }
        }
        //将参数添加到数据库
//        System.out.println(isSucesse);
        model.addAttribute("response", "私有文件上传失败");
        return "message/404"; //上传成功页面

        //上传成功定位到哪里，将状态数据放回到哪里
    }


    /**
     *文件上传
     * @param file 文件
     * @throws IOException 遗传
     */
    @RequestMapping("/public/uploadFile")
    public String uploadPublicFile(Model model,@RequestParam("file") MultipartFile file, FtpFile uploadParameter) throws IOException {
        if (uploadParameter.getDescription() == null) {
            uploadParameter.setDescription(file.getOriginalFilename());
        }
        if(file == null){
            logger.debug("file==null");
            model.addAttribute("response", "文件不能为空");
            return "message/404";
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
            return "message/404";
        }
        String suffix = filename.substring(i1);
        String id=System.currentTimeMillis()+suffix;
        uploadParameter.setId(id);  //设置上传id

        uploadParameter.setDownloadLink("ftp://uftp:1367356@"+host+":"+port+publicFilePath+"/"+id);

        //通过Spring Security 获取登录用户名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        uploadParameter.setUploadUser(userName);  //上传人

        InputStream in = file.getInputStream();
        boolean isSucesse= FtpUtil.uploadFile(host,port,username,password,basePath,publicFilePath,id,in);

        if (isSucesse) {
            try {
                //将参数添加到数据库
                int i = ftpFileService.uploadPublicFileParam(uploadParameter);  //org.springframework.dao.DuplicateKeyException  出现异常
                model.addAttribute("response", "文件上传成功");
                return "message/200"; //上传成功定位到哪里，将状态数据放回到哪里
            }catch (Exception e){
                model.addAttribute("response", "出现异常" + e.toString());
                return "message/404";
            }
        }
        model.addAttribute("response", "文件上传失败");
        return "message/404"; //上传成功页面
    }

    /**
     * 根据给定的文件名进行删除
     * Description: 从FTP服务器删除公有文件
     * @param id 要删除的文件名,11111.avi
     * @return
     */
    @RequestMapping("/deletePublicFile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePublicFile(Model model,String id) throws IOException {
        logger.debug(basePath + publicFilePath);
        boolean isSucess = false;
        try {
            isSucess = FtpUtil.deleteFile(host, port, username, password,publicFilePath, id);
        } catch (Exception e) {
            model.addAttribute("response", "删除文件失败");
            return "message/404";
        }
        if (isSucess) {
            int i=ftpFileService.deletePublicFile(id);
            //数据库删除
            model.addAttribute("response","删除文件成功");
            return "message/200";
        }
        model.addAttribute("response", "删除文件失败");
        return "message/404";
    }

    @RequestMapping("/deletePrivateFile")
    public String deletePrivateFile(Model model,String id) throws IOException {
        boolean isSucess = false;
        try {
            isSucess = FtpUtil.deleteFile(host, port, username, password, privateFilePath, id);
        } catch (Exception e) {
            model.addAttribute("response", "删除文件失败");
            return "message/404";
        }
        if (isSucess) {
            int i=ftpFileService.deletePrivateFile(id);
            //数据库删除
            model.addAttribute("response","删除文件成功");
            return "message/200";  //返回到后台消息页面
        }
        model.addAttribute("response", "删除文件失败");
        return "message/404";
    }

    /**
     * 私有文件(我的文件)查询
     * @param page
     * @return
     */
    @RequestMapping("/private/queryFile")
    public String queryPrivateFile(Model model,String page) {
        List<FtpFile> files=null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        int count=0;
        int totalpage=0;
        try {
            int ipage = Integer.parseInt(page);
            files=ftpFileService.queryPrivateFile(ipage,username);
            count = ftpFileService.selectPrivateCount(username);
            totalpage = count % 10 == 0 ? count / 10 : count / 10 + 1;
        }catch (Exception e){
            model.addAttribute("response", "查询私有文件失败");
            return "message/404";
        }
        model.addAttribute("response", files);
        model.addAttribute("totalpage",totalpage);
        model.addAttribute("page", page);
        return "front/privateFile";
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
}
