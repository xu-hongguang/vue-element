package com.xhg.studyelement.shiro.service.impl;

import com.xhg.studyelement.shiro.dao.IRoleDAO;
import com.xhg.studyelement.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eddy.Xu
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final IRoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(IRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<String> getAllRoleSn() {
        return roleDAO.getAllRoleSn();
    }

    @Override
    public List<String> getRoleSnByUserId(Long userId) {
        return roleDAO.getRoleSnByUserId(userId);
    }
}
