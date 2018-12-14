package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDAO {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}