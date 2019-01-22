package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAllMenuAndChildMenu(String type);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}