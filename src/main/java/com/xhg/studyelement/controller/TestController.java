package com.xhg.studyelement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author 16033
 */
@Controller
public class TestController {

    @GetMapping("/verify")
    public String first(){
        return "util/verify.html";
    }

    @GetMapping("/user")
    public String user(){
        return "util/excel.html";
    }

    @RequestMapping("/table")
    public String second(){
        return "element/table.html";
    }

    @RequestMapping(value = "/third",method = {GET})
    public String third(){
        return "element/hello.html";
    }

    @RequestMapping("/views")
    public String view(){
        return "login.html";
    }

}
