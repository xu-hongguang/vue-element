package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleDAO {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}