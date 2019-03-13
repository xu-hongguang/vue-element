package com.xhg.studyelement.shiro.service.impl;

import com.google.common.collect.Lists;
import com.xhg.studyelement.common.domain.Tree;
import com.xhg.studyelement.common.utils.TreeUtils;
import com.xhg.studyelement.shiro.dao.IPermissionDAO;
import com.xhg.studyelement.shiro.dao.PermissionMapper;
import com.xhg.studyelement.shiro.domain.Permission;
import com.xhg.studyelement.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public int save(Permission permission) {
        return permissionDAO.save(permission);
    }

    /**
     * 根据用户id获取所有权限
     *
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
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public List<Permission> getAllPermissionsByUserId(Long userId, String type) {
        Permission rootMenu = getRootMenu(userId, type);
        //添加一级菜单
        final List<Permission> nodeList = Lists.newArrayList();
        nodeList.add(rootMenu);
        for (Permission permission : permissionMapper.selectAllMenuByUserId(userId, type)) {
            if (Objects.equals(permission.getParientId(), rootMenu.getId())) {
                //添加二级菜单  根据菜单权限id来设置子菜单集合
                permission.setChildList(permissionMapper.selectAllMenuByUserIdOfParientId(userId, permission.getId(), type));
                nodeList.add(permission);
            }
        }

        return nodeList;
    }

    /**
     * 获取主页父菜单
     *
     * @param userId
     * @param type
     * @return
     */
    public Permission getRootMenu(Long userId, String type) {
        List<Permission> permissions = permissionMapper.selectAllMenuByUserId(userId, type);
        Permission rootMenu = null;
        for (Permission permission : permissions) {
            if (permission.getParientId() == 0) {
                rootMenu = permission;
            }
        }

        return rootMenu;
    }

    @Override
    public List<Permission> findAllPermiss(Map<String, Object> params) {
        return permissionMapper.findAllPermiss(params);
    }


    @Override
    public Tree<Permission> getAllMenus(Long userId, String type) {

        List<Tree<Permission>> trees = new ArrayList<>();
        List<Permission> permissions = permissionMapper.selectAllMenuByUserId(userId, type);

        permissions.forEach(permission -> {
            Tree<Permission> tree = new Tree<>();
            tree.setId(permission.getId().toString());
            tree.setParentId(permission.getParientId().toString());
            tree.setText(permission.getName());
            tree.setIcon(permission.getIcon());
            tree.setUrl(permission.getUrl());
            trees.add(tree);
        });

        return TreeUtils.build(trees);
    }
}
