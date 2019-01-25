package com.xhg.studyelement.shiro.realm;

import com.xhg.studyelement.shiro.dao.IUserDAO;
import com.xhg.studyelement.shiro.domain.User;
import com.xhg.studyelement.shiro.service.PermissionService;
import com.xhg.studyelement.shiro.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author 16033
 */
//@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /** 认证操作 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从token中获取登录的用户名， 查询数据库返回用户信息
        String username = (String) token.getPrincipal();
        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            throw new UnknownAccountException("用户名错误！");
        }
        if (User.STATUS_LOCK.equals(user.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()),
                getName());
        return info;
    }


    @Override
    public String getName() {
        return "UserRealm";
    }

    /** 授权操作 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) principals.getPrimaryPrincipal();

        List<String> permissions = permissionService.getPermissionResourceByUserId(user.getId());
        List<String> roles = roleService.getRoleSnByUserId(user.getId());

       /* if ("admin".equals(user.getUsername())) {
            //拥有所有权限
//            permissions.add("*:*");
            permissions = permissionService.getPermissionResourceByUserId(user.getId());
            //查询所有角色
            roles = roleService.getAllRoleSn();
        } else {
            System.out.println(user.getId());
            //根据用户id查询该用户所具有的角色
            roles = roleService.getRoleSnByUserId(user.getId());
            //根据用户id查询该用户所具有的权限
            permissions = permissionService.getPermissionResourceByUserId(user.getId());
        }*/

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        info.addRoles(roles);
        return info;
    }

    /** 清除缓存 */
    public void clearCached() {
        //获取当前等的用户凭证，然后清除
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


}
