package com.li.controller;

import com.li.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 图片上传控制器
 */
@Controller
@RequestMapping("main")
public class NewsImageController {

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
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
//        System.out.println(request.getParameter("member"));
        if (!file.isEmpty()) {

            try {
                Files.copy(file.getInputStream(), Paths.get(imagesBasePath, file.getOriginalFilename()));
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            } catch (IOException|RuntimeException e) {
                redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
        }

//        return "redirect:/redirect";
        return "home";
    }

}
