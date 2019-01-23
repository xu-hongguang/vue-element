package com.xhg.studyelement.common.controller;

import com.xhg.studyelement.shiro.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class BaseController {

    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected User getCurrentUser() {
        return (User) getSubject().getPrincipal();
    }

    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected Long getUserId() {
        return getCurrentUser().getId();
    }

    protected String getUserName() {
        return getCurrentUser().getUsername();
    }

    protected void login(AuthenticationToken token) {
        getSubject().login(token);
    }


}
