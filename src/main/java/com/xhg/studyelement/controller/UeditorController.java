package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.ueditor.ActionEnter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 用于处理ueditor插件相关请求
 * @author xhg
 */
@RestController
@CrossOrigin
@RequestMapping("/ueditor")
public class UeditorController {
    @RequestMapping(value = "/exec")
    public String exec(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
//        response.setHeader("Content-Type" , "text/html");
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Headers","X-Requested-With,X-Requested_With");
        String rootPath = request.getServletContext().getRealPath("/");
        return new ActionEnter(request, rootPath).exec();
    }
}
