package com.xhg.studyelement.shiro.service;

import com.xhg.studyelement.common.utils.PageUtils;
import com.xhg.studyelement.shiro.domain.User;

import java.util.Map;

public interface UserService {
    User getByUsername(String username);

    PageUtils<User> findUserAllPaging(Map<String,Object> map);
}
