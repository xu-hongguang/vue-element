package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.safesoft.User1Excel;
import com.xhg.studyelement.dao.User1Repository;
import com.xhg.studyelement.shiro.realm.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class User1ExcelController {

    @Autowired
    private User1Repository user1Repository;

    @RequestMapping("/export/userExcel")
    @RequiresPermissions("export:exportUser1List")
    @PermissionName("导出用户列表Excel")
    public void toExcel(HttpServletResponse response){
        User1Excel userExcel = new User1Excel(user1Repository.findAll());
        userExcel.write(response,"userExcel");
    }

}
