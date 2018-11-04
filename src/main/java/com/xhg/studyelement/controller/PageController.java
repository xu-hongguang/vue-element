package com.xhg.studyelement.controller;

import com.google.gson.Gson;
import com.xhg.common.utils.R;
import com.xhg.studyelement.pojo.User;
import com.xhg.studyelement.serivce.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PageController {
    Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/userList/{pageNo}/{pageSize}")
    public String userList(Map<String, Object> map,
                           @PathVariable("pageNo") Integer pageNo,
                           @PathVariable("pageSize") Integer pageSize, String username) {
        Page<User> userPage = userService.findAllByUsername(pageNo, pageSize, username);

//        所有内容
        map.put("userList", userPage.getContent());
//        总记录数
        map.put("totalCount", (int) userPage.getTotalElements());
//        页码
        map.put("pageNo", pageNo);
//        当前页记录数
        map.put("pageSize", pageSize);

        logger.info("获取的数据：" + userPage.getContent());


//        return map;
        return new Gson().toJson(map);
    }

}
