package DAO;

import Models.Role;
import Models.User;
import java.util.List;

/**
 *
 * @author nguyenson
 */
public interface UserDAO {

    User addUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    User login(String username, String password);

    User updateUser(User user);

    User deleteUser(User user);

    User addUserRole(User user, Role role);

    List<Role> getRoles(User user);

    User deleteUserRole(User user, Role role);

}
