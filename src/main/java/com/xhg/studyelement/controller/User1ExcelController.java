package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.safesoft.User1Excel;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.realm.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class User1ExcelController {

    private final User1Service user1Service;

    @Autowired
    public User1ExcelController(User1Service user1Service) {
        this.user1Service = user1Service;
    }

    @RequestMapping("/export/userExcel")
    @RequiresPermissions("export:exportUser1List")
    @PermissionName("导出用户列表Excel")
    public void toExcel(HttpServletResponse response){
        User1Excel userExcel = new User1Excel(user1Service.findAll(), "export/user.xlsx");
        userExcel.write(response,"userExcel");
    }

}
