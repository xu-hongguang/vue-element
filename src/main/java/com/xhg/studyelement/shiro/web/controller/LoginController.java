package com.xhg.studyelement.shiro.web.controller;


import com.xhg.studyelement.common.utils.MD5Utils;
import com.xhg.studyelement.common.utils.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 16033
 */
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping(value = "/login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password,
                                     Boolean rememberMe) {
        // 密码 MD5 加密
//        password = MD5Utils.md5(password,username,3);
        logger.info("加密密码：" + password + username);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                subject.logout();
            }
            assert subject != null;
            subject.login(token);
            return R.ok();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            return R.error(e.getMessage());
        } catch (AuthenticationException e) {
            return R.error("认证失败！");
        }

        /*//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        R r = new R();
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        if (exceptionClassName != null) {
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
//                没有该用户
                return r.put("err","no");
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
//                用户名或密码错误
                return r.put("err","err");
            } else {
                //最终在异常处理器生成未知错误.
                return r.put("err","other");
            }
        }
        return r.put("suc","suc");*/

    }

}
