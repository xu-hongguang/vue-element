package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.Permission;

import java.util.List;

public interface IPermissionDAO {

    /**
     * 保存权限对象
     * @param permission
     */
    int save(Permission permission);

    /**
     * 获取员工的权限表达式
     * @param userId
     * @return
     */
    List<String> getPermissionResourceByUserId(Long userId);


    List<String> getAllResources();

    List<Permission> getAllPermissions();
}
