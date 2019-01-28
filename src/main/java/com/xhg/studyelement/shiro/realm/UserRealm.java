package com.xhg.studyelement.shiro.realm;

import com.xhg.studyelement.shiro.domain.User;
import com.xhg.studyelement.shiro.service.PermissionService;
import com.xhg.studyelement.shiro.service.RoleService;
import com.xhg.studyelement.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author 16033
 */
@Component("userRealm")
public class UserRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public String getName() {
        return "UserRealm";
    }

    /**
     * 认证操作
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取登录的用户名， 查询数据库返回用户信息
        String username = (String) token.getPrincipal();
        //获取token中的用户密码
        String password = new String((char[]) token.getCredentials());
        logger.info("password = " + password);

        User user = userService.getByUsername(username);

        return new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()),
                getName());
    }

    /**
     * 授权操作
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) principals.getPrimaryPrincipal();

        // 获取用户权限和用户角色
        List<String> permissions = permissionService.getPermissionResourceByUserId(user.getId());
        List<String> roles = roleService.getRoleSnByUserId(user.getId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 给用户赋予权限和角色
        info.addStringPermissions(permissions);
        info.addRoles(roles);

        return info;
    }

    /**
     * 清除缓存
     */
    public void clearCached() {
        //获取当前等的用户凭证，然后清除
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


}
