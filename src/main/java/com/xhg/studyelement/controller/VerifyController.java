package com.xhg.studyelement.controller;

import com.xhg.studyelement.pojo.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 验证码验证
 */
@RestController
public class VerifyController {

    private Logger logger = LoggerFactory.getLogger(VerifyController.class);

    @PostMapping("/login/verify")
    public JsonUtil verify(String input, HttpSession session) {
        JsonUtil jsonUtil;
        System.out.println("验证码：" + input);

        String verify = (String) session.getAttribute("verify");
        if (verify.equalsIgnoreCase(input)) {
            jsonUtil = new JsonUtil();
            jsonUtil.setIsSuc("suc");
//            session.removeAttribute("verify");
        } else {
            jsonUtil = new JsonUtil();
        }

        return jsonUtil;

    }

    /**
     * 将验证码保存到session中
     *
     * @param verify
     * @param session
     */
    @RequestMapping("/saveVerify")
    public String saveVerify(@RequestParam("verify") String verify, HttpSession session) {
        session.setAttribute("verify", verify);

        logger.info("验证码保存成功");

        return "suc";
    }
}
