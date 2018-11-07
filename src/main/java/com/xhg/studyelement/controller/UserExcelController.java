package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.safesoft.UserExcel;
import com.xhg.studyelement.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserExcelController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/export/userExcel")
    public void toExcel(HttpServletResponse response){
        UserExcel userExcel = new UserExcel(userRepository.findAll());
        userExcel.write(response,"userExcel");
    }

}
