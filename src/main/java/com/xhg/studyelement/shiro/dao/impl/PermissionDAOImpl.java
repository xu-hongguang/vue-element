package com.xhg.studyelement.shiro.dao.impl;

import com.xhg.studyelement.shiro.dao.IPermissionDAO;
import com.xhg.studyelement.shiro.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 16033
 */
@Repository
public class PermissionDAOImpl implements IPermissionDAO {

    private JdbcTemplate template;

    @Autowired
    private void setDataSource(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Permission permission) {
        template.update("insert into permission(`name`,resource,parientId,url,`type`) values(?,?)",
                    permission.getName(), permission.getResource());
    }

    //
    @Override
    public List<String> getPermissionResourceByUserId(Long userId) {
        String sql = "select resource from permission where id in(" +
                "       select permission_id from role_permission where role_id in(" +
                "           select role_id from user_role where user_id = ?)" +
                "      );";
        try {
            return template.query(sql, (rs, rowNum) -> rs.getString("resource"), userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();

    }

    @Override
    public List<String> getAllResources() {
        String  sql = "select resource from permission ";
        return template.query(sql, (rs,rowNum)-> rs.getString("resource"));
    }

    @Override
    public List<Permission> getAllPermissions() {
        String  sql = "select id,`name`,resource,parientId,url,`type` from permission ";
        return template.query(sql, new NewRowMapper());
    }

    private class NewRowMapper implements RowMapper<Permission>{
        @Override
        public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Permission permission = new Permission();
            permission.setId(rs.getLong("id"));
            permission.setName(rs.getString("name"));
            permission.setResource(rs.getString("resource"));
            permission.setParientId(rs.getLong("parientId"));
            permission.setUrl(rs.getString("url"));
            permission.setType(rs.getString("type"));
            return permission;
        }
    }
}
