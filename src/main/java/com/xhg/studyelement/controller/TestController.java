package com.xhg.studyelement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author 16033
 */
@Controller
public class TestController {

    @GetMapping("/verify")
    public String first(){
        return "util/verify";
    }

    @GetMapping("/user")
    public String user(){
        return "util/excel";
    }

    @RequestMapping("/table")
    public String second(){
        return "element/table";
    }

    @RequestMapping(value = "/third",method = {GET})
    public String third(){
        return "element/hello";
    }

}
