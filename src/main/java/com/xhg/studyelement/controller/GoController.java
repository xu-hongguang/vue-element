package com.xhg.studyelement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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

    @GetMapping("/toUpdate")
    public String update(String username,Integer pageNo, Map<String,Object> map){

        System.out.println(username);
        map.put("username", username);
        map.put("pageNo", pageNo);

        return "element/updateUser";
    }

}
