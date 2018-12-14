package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.RolePermission;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionDAO {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);
}