package com.xhg.studyelement.shiro.web.controller;

import com.xhg.studyelement.shiro.realm.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @RequestMapping("")
    @RequiresPermissions("department:list")
    @PermissionName("部门列表")
    public String index() throws  Exception{
        System.out.println("执行了部门列表....");
        return "jsp/department.jsp";
    }
    @RequestMapping("/save")
    @RequiresPermissions("department:save")
    @PermissionName("部门保存")
    public String save() throws Exception{
        System.out.println("执行了部门保存....");
        return "jsp/department.jsp";
    }

    @RequestMapping("/edit")
    @RequiresPermissions("department:edit")
    @PermissionName("部门编辑")
    public String edit() throws  Exception{
        System.out.println("执行了部门编辑....");
        return "jsp/department.jsp";
    }

    @RequestMapping("/delete")
    @RequiresPermissions("department:delete")
    @PermissionName("部门删除")
    public String delete() throws  Exception{
        System.out.println("执行了部门删除....");
        return "jsp/department.jsp";
    }
}
