package com.xhg.studyelement.controller;

import com.xhg.studyelement.pojo.User;
import com.xhg.studyelement.serivce.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class LoginAndRegisterController {

    private Logger logger = LoggerFactory.getLogger(LoginAndRegisterController.class);

    private UserService userService;

    @Autowired
    public LoginAndRegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Object login(User user) {
        logger.info("登录用户：" + user);
        User user1 = userService.findByUsername(user.getUsername());


        return "";
    }
}
