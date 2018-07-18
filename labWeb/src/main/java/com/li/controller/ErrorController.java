package com.li.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: laboratoryWeb
 * @author: Yafei Li
 * @create: 2018-07-14 21:48
 **/
@Controller
public class ErrorController {

    @RequestMapping("/front/error404")
    public String Code404(Model model,String response) {
        model.addAttribute("response", response);
        return "message/404";
    }
    @RequestMapping("/backgrouond/error404")
    public String backgrouondCode404(Model model,String response) {
        model.addAttribute("response", response);
        return "message/manage/404";
    }
}
