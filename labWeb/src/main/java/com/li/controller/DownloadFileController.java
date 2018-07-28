package com.li.controller;

import com.li.service.impl.FtpFileServiceImpl;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @program: laboratoryWeb
 * @author: Yafei Li
 * @create: 2018-07-11 19:06
 *
 *  下载ftp文件，  本来可以用html中的download属性下载。但是浏览器直接解析
 *   于是后台先将文件读取，转化为octet-stream形式，这样浏览器就解析不了了。使用隐藏表单提交下载
 **/
@Controller
@RequestMapping("downloadFileController")
public class DownloadFileController {
    Logger logger = LogManager.getLogger(FtpFileController.class);
    @Autowired
    FtpFileServiceImpl ftpFileService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public DownloadFileController(ResourceLoader resourceLoader) {
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
     * 根据给定的文件名进行下载
     * Description: 从FTP服务器下载文件
     * @param filename 要下载的文件名
     * @return
     */
    @RequestMapping("/downloadPublicFileByOutputStream")
    public String downloadPublicFileByOutputStream(Model model,HttpServletResponse response, String filename) throws IOException {
        logger.debug("filename:"+filename+"     "+"id:");
        logger.debug("下载ByOutputStream");
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();  //将ftp设置为被动模式。否则不成功。
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                model.addAttribute("response", "连接ftp服务器失败");
                return "message/404";
            }
            ftp.changeWorkingDirectory(publicFilePath);// 转移到FTP服务器目录
            logger.debug("远程路径" + publicFilePath);
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                logger.debug("远程文件名" + ff.getName());
                if (ff.getName().equals(filename)) {
                    InputStream in = ftp.retrieveFileStream(ff.getName());
                    logger.debug(in.toString());
                    int len = 0;
                    byte[] buff = new byte[1024*1024*2];

                    response.reset();
                    response.setContentType("application/octet-stream");
                    //Name the file
                    response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//
                    // response.addHeader("Content-Length", out..ToString());
                    OutputStream out=response.getOutputStream();
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
//                    OutputStream out = new PipedOutputStream();
                    while((len=in.read(buff))!=-1){
                        logger.debug("以字节流形式写出OutPutStream");
                        out.write(buff, 0, len);
                        out.flush();
                    }
                    in.close();
                    bufferedOutputStream.close();
                    out.close();  //包装类不关，会自动关。
                    return "";
                }
            }
            ftp.logout();
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        model.addAttribute("response", "下载失败");
        return "message/404";
    }
    /**
     * 根据给定的文件名进行下载
     * Description: 从FTP服务器下载文件
     * @param filename 要下载的文件名
     * @return
     */
    @RequestMapping("/downloadPrivateFileByOutputStream")
    public String downloadPrivateFileByOutputStream(Model model,HttpServletResponse response, String filename) throws IOException {
        logger.debug("下载私有文件");
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();  //将ftp设置为被动模式。否则不成功。
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                model.addAttribute("response", "连接ftp服务器失败");
                return "message/404";
            }
            ftp.changeWorkingDirectory(privateFilePath);// 转移到FTP服务器目录
            logger.debug("远程路径" + privateFilePath);
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                logger.debug("远程文件名" + ff.getName());
                if (ff.getName().equals(filename)) {
                    InputStream in = ftp.retrieveFileStream(ff.getName());
                    logger.debug(in.toString());
                    int len = 0;
                    byte[] buff = new byte[1024*1024*2];

                    response.reset();
                    response.setContentType("application/octet-stream");
                    //Name the file
                    response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//                    response.addHeader("Content-Length", out..ToString());
                    OutputStream out=response.getOutputStream();
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
//                    OutputStream out = new PipedOutputStream();
                    while((len=in.read(buff))!=-1){
                        logger.debug("以字节流形式写出OutPutStream");
                        out.write(buff, 0, len);
                        out.flush();
                    }
                    in.close();
                    out.close();  //包装类不关，会自动关。
                    return "";
                }
            }

            ftp.logout();
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        model.addAttribute("response", "下载失败");
        return "message/404";
    }
}
