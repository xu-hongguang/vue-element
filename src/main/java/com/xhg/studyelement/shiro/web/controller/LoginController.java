package com.xhg.studyelement.shiro.web.controller;

import com.xhg.studyelement.common.utils.MD5Utils;
import com.xhg.studyelement.common.utils.R;
import com.xhg.studyelement.common.utils.ShiroUtils;
import com.xhg.studyelement.shiro.domain.User;
import com.xhg.studyelement.shiro.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author 16033
 */
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 将验证码保存到session中
     *
     * @param verify
     */
    @RequestMapping("/saveVerify")
    public String saveVerify(@RequestParam("verify") String verify) {

        //验证码保存成功
        ShiroUtils.setSessionAttribute("verify", verify);

        logger.info("验证码保存成功");

        return "suc";
    }

    @PostMapping(value = "/login")
    public Map<String, Object> login(String username, String password, String verify, Boolean rememberMe) {


        if(StringUtils.isBlank(verify)){
            return R.error(2, "验证码不可为空");
        }
        String kaptcha = ShiroUtils.getKaptcha("verify");
        if (!verify.equalsIgnoreCase(kaptcha)) {
            return R.error(2,"验证码不正确");
        }
        User user = userService.getByUsername(username);

        if (user!=null && !user.getPassword().equals(MD5Utils.md5(password,username,3))){
            return R.error(2,"用户名或密码错误，请重试！");
        }
        //账号锁定
        assert user != null;
        if (!"1".equals(user.getStatus())) {
            return R.error(2,"账号已被锁定或不可用,请联系管理员");
        }


        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        logger.info("加密密码：" + password + username);
        try {
            Subject subject = ShiroUtils.getSubject();
            if (subject != null) {
                subject.logout();
            }
            assert subject != null;
            subject.login(token);
            return R.ok();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            return R.error(2,"用户名或密码错误，请重试！");
        } catch (AuthenticationException e) {
            return R.error(2,"认证失败！");
        }

    }

}
