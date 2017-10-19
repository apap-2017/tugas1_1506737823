package com.example.apap.tugas1.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController{

    @RequestMapping("/")
    public String index(){
        return "view/index";
    }

    @RequestMapping("/confirmation")
    public String konfirmasi(){
        return "view/confirmation";
    }

    @RequestMapping("404")
    String get404ErrorPage() {
        return "/view/error";
    }

    @RequestMapping("401")
    String get401ErrorPage() {
        return "/view/error";
    }
}
