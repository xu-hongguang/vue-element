package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.utils.R;
import com.xhg.studyelement.shiro.domain.Permission;
import com.xhg.studyelement.shiro.realm.PermissionName;
import com.xhg.studyelement.shiro.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Eddy.Xu
 */
@Slf4j
@Controller
public class MenuController {

    private final PermissionService permissionService;

    @Autowired
    public MenuController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping("/menu")
    public String toMenu(){
        return "system/menu/menu";
    }

    @RequestMapping("/sys/menuList")
    @RequiresPermissions("sys:menuList")
    @PermissionName("菜单列表")
    @ResponseBody
    public List<Permission> getMenuList(@RequestParam Map<String,Object> params){
        log.info("params = " + params);
        List<Permission> allPermiss = permissionService.findAllPermiss(params);

        log.info("allPermiss = " + allPermiss);
        return allPermiss;
    }

    @RequestMapping("/sys/menu/add")
    @ResponseBody
    public R addMenuPer(Permission permission){

        permissionService.save(permission);

        return R.ok();

    }
}
