package com.xhg.studyelement.shiro.web.controller;

import com.xhg.studyelement.common.controller.BaseController;
import com.xhg.studyelement.common.domain.Tree;
import com.xhg.studyelement.common.utils.R;
import com.xhg.studyelement.shiro.domain.Permission;
import com.xhg.studyelement.shiro.realm.PermissionName;
import com.xhg.studyelement.shiro.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eddy.Xu
 */
@RestController
public class PermissionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PermissionController.class);

    /**
     *  请求映射处理映射器
     *  springmvc在启动时候将所有贴有请求映射标签：RequestMapper方法收集起来封装到该对象中
     */
    private final RequestMappingHandlerMapping rmhm;

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(@Qualifier("rmhm") RequestMappingHandlerMapping rmhm, PermissionService permissionService) {
        this.rmhm = rmhm;
        this.permissionService = permissionService;
    }

    @RequestMapping("/reload")
    @RequiresPermissions("sys:reloadPermission")
    @PermissionName("加载所有权限")
    public R reload() throws  Exception{
        //将系统中所有权限表达式加载进入数据库
        //0：从数据库中查询出所有权限表达式，然后对比，如果已经存在了，跳过，不存在添加
        List<String> resourcesList = permissionService.getAllResources();
        //1:获取controller中所有带有@RequestMapper标签的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
        Collection<HandlerMethod> methods = handlerMethods.values();
        for (HandlerMethod method : methods) {
            //2：遍历所有方法，判断当前方法是否贴有@RequiresPermissions权限控制标签
            RequiresPermissions anno = method.getMethodAnnotation(RequiresPermissions.class);
            if(anno != null){
                //3：如果有，解析得到权限表达式，封装成Permission对象保存到Permission表中
                //权限表达式
                String resource = anno.value()[0];

                //去除重复的
                if(resourcesList.contains(resource)){
                    continue;
                }
                Permission p = new Permission();
                p.setResource(resource);
                //设置权限名称
                p.setName(Objects.requireNonNull(method.getMethodAnnotation(PermissionName.class)).value());
                //保存
                permissionService.save(p);
            }
        }
        return R.ok();
    }

    @RequestMapping("menu/nav")
    public R nav(){
        List<Permission> menuList = permissionService.getAllPermissionsByUserId(getUserId(), "1");

        Tree<Permission> treeTree = permissionService.getAllMenus(getUserId(),"1");

        logger.info("菜单列表: " + menuList);
        logger.info("菜单列表: " + treeTree);

//        return R.ok().put("treeTree",treeTree);
        return R.ok().put("menuList",menuList);
    }

}
