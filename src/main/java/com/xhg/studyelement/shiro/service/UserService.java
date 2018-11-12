package com.xhg.studyelement.shiro.service;

import com.xhg.studyelement.shiro.domain.User;

public interface UserService {
    User getByUsername(String username);
}
