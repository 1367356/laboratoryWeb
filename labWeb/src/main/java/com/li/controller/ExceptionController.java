package com.li.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理控制器
 */
@ControllerAdvice
public class ExceptionController {

    Logger logger = LogManager.getLogger(ExceptionController.class);
//    /**
//     * 异常处理
//     * @param ex
//     * @Description:
//     */
//    @ExceptionHandler(Exception.class)
//    @RequestMapping
//    public String handleException(Model model,Exception ex){
//
//        if (ex instanceof org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException){
//            logger.debug("捕获异常" + ex);
//            model.addAttribute("response","上传文件超过限制");
//            return "message/404";
//        } else if(ex instanceof org.springframework.web.multipart.MultipartException){
//            model.addAttribute("response","其它异常");
//            return "message/404";
//        }
//        return "message/404";
//    }

    //声明要捕获的异常

/*    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defultExcepitonHandler(Model model,Exception e) {
        logger.debug("捕获到了异常");
        return "{\"error\":\"error\"}";

//        return "error";
    }*/

//
//        public static final String UploadError_ERROR_VIEW = "error";
//        @ExceptionHandler(value =Exception.class)
//        public ModelAndView handlerSizeLimitExceededException() throws Exception {
//            ModelAndView mav = new ModelAndView();
//            mav.addObject("error", "上传文件过大");
//            mav.setViewName(UploadError_ERROR_VIEW);  //返回到error.html
//            return mav;
//    }

}
