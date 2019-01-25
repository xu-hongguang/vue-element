package com.xhg.studyelement.shiro.service;

import com.xhg.studyelement.shiro.domain.Permission;

import java.util.List;

/**
 * @author Eddy.Xu
 */
public interface PermissionService {

    /**
     * 保存权限对象
     * @param permission
     */
    void save(Permission permission);

    /**
     * 获取员工的权限表达式
     * @param userId
     * @return
     */
    List<String> getPermissionResourceByUserId(Long userId);


    List<String> getAllResources();

    List<Permission> getAllPermissions();

    List<Permission> getAllPermissionsByUserId(Long userId, String type);
}
