package DAL.Impl;

import DAL.DBContext;
import DAL.RoleDAO;
import DAL.UserDAO;
import Models.Role;
import Models.User;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
@Default
public class UserDAOImpl implements UserDAO {

    @Inject
    private DBContext dbContext;

    @Inject
    private RoleDAO roleDAO;

    public UserDAOImpl() {
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
            if (user.getGoogleEmail() == null) {
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
        String sql = "select id, username, password, first_name, last_name, email, google_email from users where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("first_name"))
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
        String sql = "select id, username, password, first_name, last_name, email, google_email from users where username = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, username);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .password(rs.getNString("password"))
                            .firstName(rs.getNString("first_name"))
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
        String sql = "select id, username, password, first_name, last_name, email, google_email from users where email = ?";
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
        String sql = "select id, username, first_name, last_name, email, google_email from users where google_email = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = User.builder()
                            .id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("first_name"))
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
    public boolean isUsernameExisted(String username) {
        String sql = "select * from users where username = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, username);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isEmailExisted(String email) {
        String sql = "select * from users where email = ? or google_email = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, email);
            ps.setNString(2, email);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
            ps.setInt(7, user.getId());
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
            ps.setInt(2, roleID);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalError(ex);
        }
        return null;
    }

    @Override
    public HashSet<Role> getRoles(User user) {
        String getRoleIDsSql = "select role_id from user_role where user_id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(getRoleIDsSql)) {
            ps.setInt(1, user.getId());
            try ( ResultSet rs = ps.executeQuery()) {
                List<Integer> roleList = new ArrayList<>();
                while (rs.next()) {
                    roleList.add(rs.getInt("role_id"));
                }
                if (roleList.isEmpty()) {
                    return null;
                }
                HashSet<Role> roles = new HashSet<>();
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
        String sql = "delete from user_role where user_id = ? and role_id = ?";
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

    @Override
    public List<User> searchAllUsersPaging(String searchField, String searchText, int size, int page) {
        if (searchField.equals("name")) {
            searchField = "CONCAT(first_name, ' ', last_name)";
        }
        String sql = "SELECT id, username, password, first_name, last_name, email, google_email\n"
                + "FROM users\n"
                + "WHERE " + searchField + " LIKE CONCAT('%', ?, '%') "
                + "ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = size * (page - 1);
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, searchText);
            ps.setInt(2, offset);
            ps.setInt(3, size);
            try ( ResultSet rs = ps.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    User user = User.builder().id(rs.getInt("id"))
                            .username(rs.getNString("username"))
                            .firstName(rs.getNString("first_name"))
                            .lastName(rs.getNString("last_name"))
                            .email(rs.getNString("email"))
                            .googleEmail(rs.getNString("google_email"))
                            .build();
                    HashSet<Role> roles = getRoles(user);
                    user.setRoles(roles);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int countAllNumberOfSearchingRows(String searchField, String searchText) {
        if (searchField.equals("name")) {
            searchField = "CONCAT(first_name, ' ', last_name)";
        }
        String sql = "SELECT COUNT(*) AS total "
                + "FROM users "
                + "WHERE " + searchField + " LIKE CONCAT('%', ?, '%')";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setNString(1, searchText);
            try ( ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public boolean changePassword(int id, String newPassword) {
        String sql = "update users set password = ? from users where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, newPassword);
            ps.setInt(2, id);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
