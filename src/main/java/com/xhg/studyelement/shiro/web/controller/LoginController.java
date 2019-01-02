package com.xhg.studyelement.shiro.web.controller;

import com.xhg.studyelement.common.utils.MD5Utils;
import com.xhg.studyelement.common.utils.R;
import com.xhg.studyelement.common.utils.ShiroUtils;
import com.xhg.studyelement.common.utils.vcode.Captcha;
import com.xhg.studyelement.common.utils.vcode.GifCaptcha;
import com.xhg.studyelement.shiro.domain.User;
import com.xhg.studyelement.shiro.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 16033
 */
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final String V_CODE = "v_code";

    @Autowired
    private UserService userService;

    /**
     * 将验证码保存到session中(前端js生成的验证码)
     *
     * @param // verify
     */
    /*@RequestMapping("/saveVerify")
    public String saveVerify(@RequestParam("verify") String verify) {

        //验证码保存成功
        ShiroUtils.setSessionAttribute("verify", verify);

        logger.info("验证码保存成功");

        return "suc";
    }*/

    /**
     * 使用java生成的验证码
     * @param response
     * @param request
     */
    @GetMapping("/saveVerify")
    public void saveVerifyImg(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");

            Captcha captcha = new GifCaptcha(146, 42, 4);
            captcha.out(response.getOutputStream());
            ShiroUtils.setSessionAttribute(V_CODE, captcha.text().toLowerCase());

            logger.info("验证码保存成功! ");
        } catch (Exception e) {
            logger.error("图形验证码生成失败", e);
        }
    }

    @PostMapping(value = "/login")
    public Map<String, Object> login(String username, String password, String verify, Boolean rememberMe) {


        if (StringUtils.isBlank(verify)) {
            return R.error(2, "验证码不可为空");
        }
//        String kaptcha = ShiroUtils.getKaptcha("verify");
        String kaptcha = ShiroUtils.getKaptcha(V_CODE);
        if (!verify.equalsIgnoreCase(kaptcha)) {
            return R.error(2, "验证码不正确");
        }
        User user = userService.getByUsername(username);

        if (user == null) {
            return R.error(2, "用户名不正确!");
        } else if (!user.getPassword().equals(MD5Utils.md5(password, username, 3))) {
            return R.error(2, "用户名或密码错误，请重试！");
        }
        //账号锁定
        if (!"1".equals(user.getStatus())) {
            return R.error(2, "账号已被锁定或不可用,请联系管理员");
        }

        logger.info("rememberMe:" + rememberMe);


        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            Subject subject = ShiroUtils.getSubject();
            if (subject != null) {
                subject.logout();
            }
            assert subject != null;
            subject.login(token);
            return R.ok();
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            return R.error(2, "用户名或密码错误，请重试！");
        } catch (AuthenticationException e) {
            return R.error(2, "认证失败！");
        }

    }

}
