package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Eddy.Xu
 */
@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAllMenuByUserId(@Param("userId") Long userId, @Param("type") String type);

    List<Permission> selectAllMenuByUserIdOfParientId(@Param("userId") Long userId, @Param("parentId") Long parentId, @Param("type") String type);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}