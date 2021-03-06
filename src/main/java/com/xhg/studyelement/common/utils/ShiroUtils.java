package com.xhg.studyelement.common.utils;

import com.xhg.studyelement.common.exception.RRException;
import com.xhg.studyelement.shiro.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static User getUserEntity() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUserId() {
        return getUserEntity().getId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        removeSessionAttribute(key);
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static void removeSessionAttribute(Object key) {
        getSession().removeAttribute(key);
    }

    /**
     * 判断是否登录
     * @return boolean
     */
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout(Object key) {
        removeSessionAttribute(key);
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取验证码
     * @param key 验证码key
     * @return string
     */
    public static String getKaptcha(String key) {
        String kaptcha = (String) getSessionAttribute(key);
        if (kaptcha == null || "".equals(kaptcha)) {
            throw new RRException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha;
    }

}
