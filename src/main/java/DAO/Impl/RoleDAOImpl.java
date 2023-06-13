package DAO.Impl;

import DAO.DBContext;
import DAO.DBContextImpl;
import DAO.RoleDAO;
import Models.Role;
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
public class RoleDAOImpl implements RoleDAO {

    DBContext dbContext;

    public RoleDAOImpl() {
        dbContext = new DBContextImpl();
    }

    @Override
    public Role addRole(Role role) {
        String sql = "insert into roles(role_name) values (?)";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, role.toString());
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
            ps.setNString(1, role.toString());
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
                    return Role.valueOf(rs.getNString("role_name"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
