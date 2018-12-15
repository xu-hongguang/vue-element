package com.xhg.studyelement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 16033
 */
@Controller
public class GoController {

    @GetMapping("/verify")
    public String first(){
        return "util/verify.html";
    }

    @GetMapping("/user")
    public String user(){
        return "util/excel.html";
    }

    @RequestMapping("/easyPOI")
    public String easy(){
        return "util/excelPOI.html";
    }

    @RequestMapping("/table")
    public String userTable(){
        return "element/table.html";
    }

    @GetMapping("/login")
    public String view(){
        return "login.html";
    }

}
