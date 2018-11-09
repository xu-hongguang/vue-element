package com.xhg.studyelement.controller;

import com.google.gson.Gson;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.realm.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
public class PageController {
    private Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private User1Service userService;

    @RequestMapping("/userList/{pageNo}/{pageSize}")
    @RequiresPermissions("user:getUserList")
    @PermissionName("查询用户列表")
    public String userList(Map<String, Object> map,
                           @PathVariable("pageNo") Integer pageNo,
                           @PathVariable("pageSize") Integer pageSize, String username) {
        Page<User1> userPage = userService.findAllByUsername(pageNo, pageSize, username);

//        所有内容
        map.put("userList", userPage.getContent());
//        总记录数
        map.put("totalCount", (int) userPage.getTotalElements());
//        页码
        map.put("pageNo", pageNo);
//        当前页记录数（这里不需要向前端传）
        map.put("pageSize", pageSize);

        logger.info("获取的数据：" + userPage.getContent());

//        return map;
        return new Gson().toJson(map);
    }

    /**
     * 添加
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/add")
    @RequiresPermissions("user:addUser")
    @PermissionName("添加用户")
    public String add(@RequestBody User1 user) {
        logger.info("添加：" + user);

        if (user != null && userService.saveUser(user)) {
            return "suc";
        }

        return "err";
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/update")
    @RequiresPermissions("user:updateUser")
    @PermissionName("修改用户")
    public String update(@RequestBody User1 user) {
        logger.info("修改为：" + user);

        if (user != null && userService.updateUser(user)) {
            return "suc";
        }

        return "err";
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/user/delete")
    @RequiresPermissions("user:removeUser")
    @PermissionName("删除用户")
    public String delete(Integer id) {
        logger.info("删除：" + id);

        if ((id != null) && (id > 0)) {
            userService.deleteUser(id);
            return "suc";
        }

        return "err";
    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping("/user/batchRemove")
    @RequiresPermissions("user:batchRemoveUser")
    @PermissionName("批量删除用户")
    public String batchRemove(@RequestBody Integer[] ids) {
        logger.info("删除ids：" + Arrays.toString(ids));

        if (ids != null && ids.length > 0) {
            userService.deleteMap(ids);
            return "suc";
        }
        return "err";
    }

}
