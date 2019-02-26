package com.xhg.studyelement.shiro.service.impl;

import com.google.common.collect.Lists;
import com.xhg.studyelement.shiro.dao.IPermissionDAO;
import com.xhg.studyelement.shiro.dao.PermissionMapper;
import com.xhg.studyelement.shiro.domain.Permission;
import com.xhg.studyelement.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Eddy.Xu
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final IPermissionDAO permissionDAO;

    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(IPermissionDAO permissionDAO, PermissionMapper permissionMapper) {
        this.permissionDAO = permissionDAO;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public void save(Permission permission) {
        permissionDAO.save(permission);
    }

    /**
     * 根据用户id获取所有权限
     * @param userId
     * @return
     */
    @Override
    public List<String> getPermissionResourceByUserId(Long userId) {
        List<String> resource = permissionDAO.getPermissionResourceByUserId(userId);
        resource.removeAll(Collections.singleton(null));
        resource.removeAll(Collections.singleton(""));
        return resource;
    }

    @Override
    public List<String> getAllResources() {
        return permissionDAO.getAllResources();
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionDAO.getAllPermissions();
    }

    /**
     * 根据用户id获取菜单栏（二级菜单）
     * @param userId
     * @param type
     * @return
     */
    @Override
    public List<Permission> getAllPermissionsByUserId(Long userId, String type) {
        List<Permission> permissions = permissionMapper.selectAllMenuByUserId(userId, type);
        Permission rootMenu = null;
        for (Permission permission : permissions){
            if (permission.getParientId() == 0){
                rootMenu = permission;
            }
        }

        //添加一级菜单
        final List<Permission> nodeList = Lists.newArrayList();
        nodeList.add(rootMenu);
        for (Permission permission : permissions) {
            if (Objects.equals(permission.getParientId(), rootMenu.getId())) {
                //添加二级菜单  根据菜单权限id来设置子菜单集合
                permission.setChildList(permissionMapper.selectAllMenuByUserIdOfParientId(userId, permission.getId(), type));
                nodeList.add(permission);
            }
        }

        return nodeList;
    }

    @Override
    public List<Permission> findAllPermiss(Map<String, Object> params) {
        return permissionMapper.findAllPermiss(params);
    }
}
