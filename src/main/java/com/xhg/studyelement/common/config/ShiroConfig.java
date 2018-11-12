package com.xhg.studyelement.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xhg.studyelement.common.listener.ShiroSessionListener;
import com.xhg.studyelement.shiro.realm.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Configuration
public class ShiroConfig {

    /**
     * 请求映射处理映射器
     * springmvc在启动时候将所有贴有请求映射标签：RequestMapper方法收集起来封装到该对象中
     *
     * @return
     */
    @Bean(name = "rmhm")
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /**
     * 配置用户realm
     * @param credentialsMatcher
     * @return
     */
    @Bean("userRealm")
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        //设置密码加密器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 配置安全管理器SecurityManager
     *
     * @param userRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 rememberMeCookie
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
        // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        cookie.setSecure(true);  // 只在 https中有效 注释掉 正常
        // 设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }
    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie 加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        listeners.add(new ShiroSessionListener());
        // 设置session超时时间，单位为毫秒
        sessionManager.setGlobalSessionTimeout(1800000L);
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * 密码加密器
     *
     * @return
     */
    @Bean
    public CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(3);
        return credentialsMatcher;
    }

    /**
     * 定义ShiroFilter
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/index.html");
        shiroFilter.setUnauthorizedUrl("noPermission.html");

        Map<String, String> filterMap = new HashMap<>(16);
        filterMap.put("/logout", "logout");
        filterMap.put("/login", "anon");
        filterMap.put("/noPermission.html", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/vue/**", "anon");
        filterMap.put("/axios/**", "anon");
        filterMap.put("/element-ui/**", "anon");
        filterMap.put("/saveVerify", "anon");
        filterMap.put("/userList/**", "anon");
        filterMap.put("/**/*.css", "anon");
        filterMap.put("/**/*.js", "anon");
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean
    public SimpleMappingExceptionResolver exceptionResolver(){
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException","redirect:/nopermission.html");

        exceptionResolver.setExceptionMappings(properties);

        return exceptionResolver;
    }

    /**
     * 开启shiro注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 用于开启 Thymeleaf 中的 shiro 标签的使用
     *
     * @return ShiroDialect shiro 方言对象
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}