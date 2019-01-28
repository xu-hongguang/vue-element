package com.xhg.studyelement.shiro.web.controller;

import com.xhg.studyelement.common.utils.MD5Utils;
import com.xhg.studyelement.common.utils.R;
import com.xhg.studyelement.common.utils.ShiroUtils;
import com.xhg.studyelement.common.utils.VerifyCodeUtils;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 16033
 */
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final String V_CODE = "v_code";

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

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
     * 使用java生成的动态验证码
     *
     * @param response
     */
    @GetMapping("/saveVerify")
    public void saveVerifyImg(HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");

            Captcha captcha = new GifCaptcha(146, 42, 4);
            //输出图片
            ServletOutputStream out = response.getOutputStream();
            captcha.out(out);
            out.flush();
            //存入Shiro会话session
            ShiroUtils.setSessionAttribute(V_CODE, captcha.text().toLowerCase());

            logger.info("验证码保存成功! ");
        } catch (Exception e) {
            logger.error("图形验证码生成失败", e);
        }
    }

    /**
     * 获取静态验证码
     *
     * @param response
     */
//     @RequestMapping(value="saveVerify",method= RequestMethod.GET)
    public void getVCode(HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //存入Shiro会话session
            ShiroUtils.setSessionAttribute(V_CODE, verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 42;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

            logger.info("验证码保存成功! ");
        } catch (Exception e) {
            logger.error("获取验证码异常：%s", e.getMessage());
        }
    }

    @PostMapping(value = "/login")
    public Map<String, Object> login(String username, String password, String verify, Boolean rememberMe) {


        if (StringUtils.isBlank(verify)) {
            return R.error(2, "验证码不可为空");
        }
        String kaptcha = ShiroUtils.getKaptcha(V_CODE);
        if (!verify.equalsIgnoreCase(kaptcha)) {
            return R.error(2, "验证码不正确");
        }
        User user = userService.getByUsername(username);

        if (user == null) {
            return R.error(2, "用户名不存在!");
        } else if (!user.getPassword().equals(MD5Utils.md5(password, username))) {
            return R.error(2, "用户名或密码错误，请重试！");
        }
        //账号锁定
        if (!User.STATUS_VALID.equals(user.getStatus())) {
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
