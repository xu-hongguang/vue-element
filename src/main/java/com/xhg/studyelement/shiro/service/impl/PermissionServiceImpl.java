package com.xhg.studyelement.shiro.service.impl;

import com.xhg.studyelement.shiro.dao.IPermissionDAO;
import com.xhg.studyelement.shiro.domain.Permission;
import com.xhg.studyelement.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eddy.Xu
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final IPermissionDAO permissionDAO;

    @Autowired
    public PermissionServiceImpl(IPermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }

    @Override
    public void save(Permission permission) {
        permissionDAO.save(permission);
    }

    @Override
    public List<String> getPermissionResourceByUserId(Long userId) {
        return permissionDAO.getPermissionResourceByUserId(userId);
    }

    @Override
    public List<String> getAllResources() {
        return permissionDAO.getAllResources();
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionDAO.getAllPermissions();
    }
}
