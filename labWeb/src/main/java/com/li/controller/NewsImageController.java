package com.li.controller;

import com.li.utils.FtpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 图片上传控制器
 */
@Controller
public class NewsImageController {

    Logger logger = LogManager.getLogger(NewsImageController.class);

    private final ResourceLoader resourceLoader;

    @Autowired
    public NewsImageController(ResourceLoader resourceLoader) {
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

//    @Value("${ftp.imagesPath}")     //基础路径
//    private String imagePath;

    @Value("${ftp.imagesBasePath}")     //基础路径
    private String imagesBasePath;

    /**
     * 新闻上传图片
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage1(@RequestParam("upload") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("上传");
        String name = "";
        if (!file.isEmpty()) {
            try {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.setHeader("Cache-Control", "no-cache");
                //解决跨域问题
                //Refused to display 'http://localhost:8080/upload/mgmt/img?CKEditor=practice_content&CKEditorFuncNum=1&langCode=zh-cn' in a frame because it set 'X-Frame-Options' to 'DENY'.
                response.setHeader("X-Frame-Options", "SAMEORIGIN");
//                PrintWriter out = response.getWriter();  //最新版本的提示response has already call getWriter
                ServletOutputStream out = response.getOutputStream();

                String fileClientName = file.getOriginalFilename();
                String pathName = imagesBasePath + fileClientName;
                File newfile = new File(pathName);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newfile));
                stream.write(bytes);
                stream.close();

                // 组装返回url，以便于ckeditor定位图片
                String fileUrl = "/displayImage/" + fileClientName;


                // 将上传的图片的url返回给ckeditor
                String callback = request.getParameter("CKEditorFuncNum");
                logger.debug("callback"+callback+"fileUrl"+fileUrl);
                String script = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(" + callback + ", '" + fileUrl + "');</script>";
                out.println(script);
                out.flush();
                out.close();
            } catch (Exception e) {
//                logger.info("You failed to upload " + name + " => " + e.getMessage());
                e.printStackTrace();
            }
        } else {
//            logger.info("You failed to upload " + name + " because the file was empty.");
        }
        return "SUCCESS";
    }

//    int port = Integer.parseInt(sport);

//    /**
//     *图片上传
//     * @param file 文件
//     * @throws IOException 遗传
//     */
//    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
//    @ResponseBody
//    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//
//        Long l = System.currentTimeMillis();
////        String filename=l.toString();
//        String filename = file.getOriginalFilename();//文件名
//        int i = filename.indexOf(".");
//        String suffix = filename.substring(i);
////        String filename = request.getParameter("filename");
//
//        InputStream in = file.getInputStream();
//        boolean isSucesse= FtpUtil.uploadFile(host,port,username,password,imagesBasePath,imagePath,l+suffix,in);
//        System.out.println(isSucesse);
//
//        return "displayImage/"+l+suffix;
//
//        //上传成功定位到哪里，将状态数据放回到哪里
//    }


    /**
     * 图片加载
     * @param filename  文件名
     * @return  图片
     */
    //http://localhost:8080/avatar/avatar1.png
    //显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
    @RequestMapping(method = RequestMethod.GET, value = "/displayImage/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        System.out.println(filename );
        try {
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(basePath+imagePath, filename).toString()));
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(basePath+imagePath, filename).toString()));
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(imagesBasePath, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //上传的方法,成功，中英文都可以
    @RequestMapping(method = RequestMethod.POST, value = "/uploadNewsImage")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
//        System.out.println(request.getParameter("member"));
        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(imagesBasePath, file.getOriginalFilename()));
                return "/displayImage/"+file.getOriginalFilename();
//                redirectAttributes.addFlashAttribute("message",
//                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException|RuntimeException e) {
                    e.printStackTrace();
                    redirectAttributes.addAttribute("response","上传文件出现异常");
                    return "message/manage/404";
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }

//        return "redirect:/redirect";
        return "上传失败";
    }

}
