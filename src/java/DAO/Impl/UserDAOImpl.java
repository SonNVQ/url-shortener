package DAO.Impl;

import DAO.DBContext;
import DAO.DBContextImpl;
import DAO.RoleDAO;
import DAO.UserDAO;
import Models.Role;
import Models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
public class UserDAOImpl implements UserDAO {

    DBContext dbContext;
    RoleDAO roleDAO;

    public UserDAOImpl() {
        this.dbContext = new DBContextImpl();
        this.roleDAO = new RoleDAOImpl();
    }

    @Override
    public User addUser(User user) {
        String sql = "insert into users(username, password, first_name, last_name, email, google_email) values (?, ?, ?, ?, ?, ?)";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setNString(1, user.getUsername());
            ps.setNString(2, user.getPassword());
            if (user.getFirstName() == null) {
                ps.setNull(3, Types.NVARCHAR);
            } else {
                ps.setNString(3, user.getFirstName());
            }
            if (user.getLastName() == null) {
                ps.setNull(4, Types.NVARCHAR);
            } else {
                ps.setNString(4, user.getLastName());
            }
            if (user.getEmail() == null) {
                ps.setNull(5, Types.NVARCHAR);
            } else {
                ps.setNString(5, user.getEmail());
            }
            if (user.getUsername() == null) {
                ps.setNull(6, Types.NVARCHAR);
            } else {
                ps.setNString(6, user.getGoogleEmail());
            }
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                try ( ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                        user.setPassword(null);
                        return user;
                    }
                }
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalError(ex);
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        String sql = "select username, password, first_name, last_name, email, google_email from users where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("username"))
                            .lastName(rs.getNString("last_name"))
                            .email(rs.getNString("email"))
                            .googleEmail(rs.getNString("google_email"))
                            .build();
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "select username, password, first_name, last_name, email, google_email from users where username = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, username);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("username"))
                            .lastName(rs.getNString("last_name"))
                            .email(rs.getNString("email"))
                            .googleEmail(rs.getNString("google_email"))
                            .build();
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "select username, password, first_name, last_name, email, google_email from users where email = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("username"))
                            .lastName(rs.getNString("last_name"))
                            .email(rs.getNString("email"))
                            .googleEmail(rs.getNString("google_email"))
                            .build();
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUserByGoogleEmail(String email) {
        String sql = "select username, password, first_name, last_name, email, google_email from users where google_email = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("username"))
                            .lastName(rs.getNString("last_name"))
                            .email(rs.getNString("email"))
                            .googleEmail(rs.getNString("google_email"))
                            .build();
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User login(String username, String password) {
        String sql = "select username, password, first_name, last_name, email, google_email from users where username = ?, password = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, username);
            ps.setNString(2, password);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("username"))
                            .lastName(rs.getNString("last_name"))
                            .email(rs.getNString("email"))
                            .googleEmail(rs.getNString("google_email"))
                            .build();
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        String sql = "update users set username = ?, password = ?, first_name = ?, last_name = ?, email = ?, google_email = ? from users where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, user.getUsername());
            ps.setNString(2, user.getPassword());
            ps.setNString(3, user.getFirstName());
            ps.setNString(4, user.getLastName());
            ps.setNString(5, user.getEmail());
            ps.setNString(6, user.getGoogleEmail());
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User deleteUser(User user) {
        String sql = "delete from users where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User addUserRole(User user, Role role) {
        Integer roleID = roleDAO.getRoleIdByRoleName(role);
        if (roleID == null) {
            return null;
        }
        String sql = "insert into user_role values (?, ?)";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.setInt(2, roleID.intValue());
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return user;
            }
        } catch (SQLException ex) {
//            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalError(ex);
        }
        return null;
    }

    @Override
    public List<Role> getRoles(User user) {
        String getRoleIDsSql = "select role_id from users where user_id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(getRoleIDsSql)) {
            ps.setInt(1, user.getId());
            try ( ResultSet rs = ps.executeQuery()) {
                List<Integer> roleList = new ArrayList<>();
                while (rs.next()) {
                    roleList.add(rs.getInt("role_id"));
                }
                List<Role> roles = new ArrayList<>();
                roleList.forEach((Integer roleId) -> {
                    roles.add(roleDAO.getRoleById(roleId));
                });
                return roles;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User deleteUserRole(User user, Role role) {
        Integer roleID = roleDAO.getRoleIdByRoleName(role);
        if (roleID == null) {
            return null;
        }
        String sql = "delete from user_role where user_id = ?, role_id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, user.getId());
            ps.setInt(2, roleID);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
