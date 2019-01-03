package com.xhg.studyelement.controller;

import com.xhg.studyelement.common.utils.R;
import com.xhg.studyelement.pojo.User1;
import com.xhg.studyelement.serivce.User1Service;
import com.xhg.studyelement.shiro.realm.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author 16033
 */
@RestController
public class PageController {
    private Logger logger = LoggerFactory.getLogger(PageController.class);

    private final User1Service user1Service;

    @Autowired
    public PageController(User1Service user1Service) {
        this.user1Service = user1Service;
    }

    @RequestMapping("/userList/{pageNo}/{pageSize}")
    @RequiresPermissions("user:getUserList")
    @PermissionName("查询用户列表")
    public R userList(@PathVariable("pageNo") Integer pageNo,
                      @PathVariable("pageSize") Integer pageSize, String username) {
        Page<User1> userPage = user1Service.findAllByUsername(pageNo, pageSize, username);

        R r = new R();
//        所有内容
        r.put("userList", userPage.getContent());
//        总记录数
        r.put("totalCount", (int) userPage.getTotalElements());
//        页码
        r.put("pageNo", pageNo);
//        总页数
        r.put("totalPage", userPage.getTotalPages());
//        当前页记录数（这里不需要向前端传）
        r.put("pageSize", pageSize);

        logger.info("获取的数据：" + userPage.getContent());

        return r;
//        return new Gson().toJson(map);
    }

    @RequestMapping("/userOne")
    @RequiresPermissions("user:userOne")
    @PermissionName("查询用户")
    public R userOne(String username,Integer id) {
        User1 user1 = user1Service.findByUsername(id,username);

        logger.info("user1: " + user1);

        return R.ok().put("user1", user1);
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

        if (user != null && user1Service.saveUser(user) != null) {
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

        if (user != null && user1Service.updateUser(user) != null) {
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
            user1Service.deleteUser(id);
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
            user1Service.deleteMap(ids);
            return "suc";
        }
        return "err";
    }

}
