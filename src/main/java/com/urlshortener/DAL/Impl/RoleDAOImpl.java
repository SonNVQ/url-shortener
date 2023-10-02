package com.urlshortener.DAL.Impl;

import com.urlshortener.DAL.DBContext;
import com.urlshortener.DAL.RoleDAO;
import com.urlshortener.Models.Role;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
@Default
public class RoleDAOImpl implements RoleDAO {

    @Inject
    private DBContext dbContext;

    public RoleDAOImpl() {
    }

    @Override
    public Role addRole(Role role) {
        String sql = "insert into roles(role_name) values (?)";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, role.toString());
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return role;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer getRoleIdByRoleName(Role role) {
        String sql = "select id from roles where role_name = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, role.toString());
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Role getRoleById(int id) {
        String sql = "select role_name from roles where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Role.valueOf(rs.getString("role_name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
