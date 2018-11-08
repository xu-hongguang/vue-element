package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.safesoft.User1Excel;
import com.xhg.studyelement.dao.User1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class User1ExcelController {

    @Autowired
    private User1Repository user1Repository;

    @RequestMapping("/export/userExcel")
    public void toExcel(HttpServletResponse response){
        User1Excel userExcel = new User1Excel(user1Repository.findAll());
        userExcel.write(response,"userExcel");
    }

}
